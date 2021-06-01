# app.py
import csv
import tensorflow as tf
import numpy as np
import pandas as pd
import zipfile
import tensorflow as tf
import numpy as np
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
import re
from tensorflow.keras.models import load_model
import pickle
from flask_restful import reqparse, abort, Api, Resource
import PyPDF2
import os
from werkzeug.utils import secure_filename
import json
from flask import Flask, json, jsonify, request, session, make_response
from pymysql import connect, connections
from werkzeug.security import generate_password_hash, check_password_hash
from flaskext.mysql import MySQL
from datetime import timedelta
from flask import Flask
from flask_cors import CORS
from flask_expects_json import expects_json
import pymysql
import gcsfs
from google.cloud import storage
import logging
from pathlib import Path


app = Flask(__name__)
api = Api(app)

app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024
client = storage.Client()
bucket = client.get_bucket('user-cv')
jenis_file = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])
app.secret_key = "kepo amat lu padahal rahaisa"
app.config['PERMANENT_SESSION_LIFETIME'] = timedelta(minutes=10)
CORS(app)

mysql = MySQL()

# create schema
schema = {
    "type": "object",
    "properties": {
        "name": {"type": "string"},
        "email": {"type": "string"}
    },
    "required": ["email"],
    "required": ["username"]
}


PROJECT_NAME = 'jobfinder-demo'
cred = 'cred.json'
model_path = 'gs://data-mlnya/glove_model.h5'
prep_path = 'gs://data-mlnya/tokenizer_data_new.pkl'
FS = gcsfs.GCSFileSystem(project=PROJECT_NAME,token=cred)

with FS.open(prep_path, 'rb') as f:   
    data = pickle.load(f)
    tokenizer = data['tokenizer']
    num_words = data['num_words']
    max_length = data['maxlen']
    padding_type = data['padding_type']
    trunc_type = data['trunc_type']
    pad_sequences = data['pad_sequences']
    label_index = data['label_index']

glove_model = load_model('job-finder.h5')


print(Path.cwd())

UPLOAD_FOLDER = Path.cwd()

# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'Nanonano17'
app.config['MYSQL_DATABASE_DB'] = 'jobfinder'
app.config['MYSQL_DATABASE_HOST'] = '34.101.245.71'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

mysql.init_app(app)


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in jenis_file


def not_found(error=None):
    message = {
        'status': 404,
        'message': 'Not Found: ' + request.url,
    }
    res = jsonify(message)
    res.status_code = 404

    return res



@app.route('/joblist')
def userlist():
    conn = mysql.connect()
    cur = conn.cursor(pymysql.cursors.DictCursor)
    try:
        sql = "SELECT * FROM job JOIN company ON company.company_id = job.comp_id JOIN category ON category.category_id = job.cat_id"
        cur.execute(sql)
        rows = cur.fetchall()
        resp = jsonify(rows)
        resp.status_code = 200
        conn.close()
        cur.close()
        return resp
    except Exception as e:
        print(e)
    finally:
        cur.close()
        conn.close


@app.route('/profile', methods=['PUT', 'GET'])
def update_user():
    _json = request.json
    if 'id' in session:
        if request.method == 'PUT':
            _fullname = _json['fullname']
            _address = _json['address']
            _phone = _json['phone']
            if _address and _phone and request.method == 'PUT':

                # validate the received values
                # do not save password as a plain text
                # save edits
                sql = "UPDATE user SET adress=%s, phone=%s,full_name=%s WHERE id=%s"
                data = (_address, _phone, _fullname, session)
                conn = mysql.connect()
                cur = conn.cursor(pymysql.cursors.DictCursor)
                cur.execute(sql, data)
                conn.commit()
                resp = jsonify('User updated successfully!')
                resp.status_code = 200
                conn.close()
                cur.close()
                return resp
            else:
                conn.close()
                cur.close()
                return not_found()
        else:
            conn = mysql.connect()
            cur = conn.cursor(pymysql.cursors.DictCursor)
            try:
                sql = "SELECT id userid, full_name name, email user_email, address user_addres, phone user_phone FROM user WHERE id=%s"
                data = (session)
                cur.execute(sql,data)
                    
                rows = cur.fetchall()
                resp = jsonify(rows)
                resp.status_code = 200
                conn.close()
                cur.close()
                return resp
            except Exception as e:
                print(e)
            finally:
                cur.close()
                conn.close


@app.route('/register', methods=['POST'])
@expects_json(schema)
def register():
    conn = None
    cur = None
    conn = mysql.connect()
    cur = conn.cursor(pymysql.cursors.DictCursor)
    if 'id' in session:
        resp = jsonify('Already logged in!')
        resp.status_code = 200
        return resp
    else:
        _json = request.json
        _fullname = _json['fullname']
        _username = _json['username']
        _email = _json['email']
        _password = _json['pwd']
        # validate the received values

        # check user exists
        sql = "SELECT * FROM user WHERE username = %s"
        sql_where = (_username)

        cur.execute(sql, sql_where)
        row = cur.fetchone()
        if row:
            resp = jsonify('Username already exist!')
            resp.status_code = 400
            conn.close()
            cur.close()
            return resp
        else:
            if _username and _email and _password and request.method == 'POST':
                # do not save password as a plain text
                _hashed_password = generate_password_hash(_password)
                # save edits
                sql = "INSERT INTO user (full_name, email, username, password) VALUES(%s, %s, %s, %s)"
                data = (_fullname, _username, _email, _hashed_password,)
                cur.execute(sql, data)
                conn.commit()
                resp = jsonify('User added successfully!')
                resp.status_code = 200
                conn.close()
                cur.close()
                return resp
            else:
                conn.close()
                cur.close() 
                return not_found()


@app.route('/login', methods=['POST'])
def login():
    conn = None
    cur = None
    _json = request.json
    _username = _json['username']
    _password = _json['password']
    # validate the received values

    # check user exists
    conn = mysql.connect()
    cur = conn.cursor(pymysql.cursors.DictCursor)

    sql = "select * from user WHERE username = %s"
    sql_where = (_username)

    cur.execute(sql, sql_where)
    row = cur.fetchone()
    print(row)
    if row:
        if check_password_hash(row[4], _password):
            session['id'] = row[0]
            cur.close()
            conn.close()
            return jsonify({'message': 'You are logged in successfully'})
        else:
            resp = jsonify({'message': 'Bad Request - invalid password gagal line 258'})
            resp.status_code = 400
            conn.close()
            cur.close()
            return resp
    else:
        resp = jsonify({'message': 'Bad Request - invalid credendtials gagal line 264'})
        resp.status_code = 400
        conn.close()
        cur.close()
        return resp


@app.route('/logout')
def logout():
    if 'id' in session:
        session.pop('id', None)
    return jsonify({'message': 'You successfully logged out'})


@app.route('/upload', methods=['POST'])
def upload_file():
    # check if the post request has the file part
    if 'dataupload' not in request.files:
        res = jsonify({'message': 'No file part in the request'})
        res.status_code = 400
        return res

    file = request.files['dataupload']

    errors = {}
    success = False
                        
    conn = mysql.connect()
    cur = conn.cursor(pymysql.cursors.DictCursor)

    if 'id' in session : 
        if file and allowed_file(file.filename):                   
            with FS.open(prep_path, 'rb') as f:   
                data = pickle.load(f)
                tokenizer = data['tokenizer']
                num_words = data['num_words']
                max_length = data['maxlen']
                padding_type = data['padding_type']
                trunc_type = data['trunc_type']
                pad_sequences = data['pad_sequences']
                label_index = data['label_index']

            glove_model = load_model('job-finder.h5')
            filename = secure_filename(file.filename)
            success = True

            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            # Extract pdf to str
            isifile = open(filename, 'rb')        
            pdfread = PyPDF2.PdfFileReader(isifile)
            pageObj = pdfread.getPage(0)

            #Machine Learning things
            inputs = pageObj.extractText()
            blob = bucket.blob(session['id']+'.pdf')
            #Menentukan nama file ketika disave di GCS (i use default filename)
            blob.upload_from_filename(filename)
            namafile = session['id']+'.pdf'
            sql_pdf = "INSERT INTO Customers (file) VALUES ('%s')"
            cur.execute(sql_pdf,namafile)
            labs = [list(label_index.keys())]
            stopwords = ["a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought",
                            "our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "span"]

            def clean_text(sentence):
                for word in stopwords:
                    token = " " + word + " "
                    sentence = str(sentence).replace(token, " ")
                sentence = re.sub('[^a-zA-Z]', ' ', sentence)
                sentence = re.sub(r"\s+[a-zA-Z]\s+", ' ', sentence)
                sentence = re.sub(r'\s+', ' ', sentence)
                return sentence

            # print(str(inputs)+'\n')
            clean_inputs = [clean_text(inputs)]
            # print(str(clean_inputs)+'\n')
            seq = tokenizer.texts_to_sequences(clean_inputs)
            # print(str(seq)+'\n')
            padded = pad_sequences(seq, maxlen=max_length,
                                    padding=padding_type, truncating=trunc_type)
            # print(str(padded)+'\n')

            proba = [glove_model.predict(padded)[0, 1:]]

            max = np.where(proba == np.amax(proba))
            for [i] in max:
                idx = i
            predlabel = labs[0][idx]
            #predprob = proba[idx]*100
            if predlabel == 'software_developer':
                predlabel = 'Software Developer'
                predid= 1
            elif predlabel == 'systems_administrator':
                predlabel= 'System Administrator'
                predid= 2
            elif predlabel == 'project_manager':
                predlabel = 'Project Manager'
                predid= 3
            elif predlabel == 'web_developer':
                predlabel = 'Web Developer'
                predid= 4
            elif predlabel == 'database_administrator' :
                predlabel = 'Database Administrator'
                predid= 5
            elif predlabel == 'java_developer' :
                predlabel = 'Java Developer'
                predid= 6
            elif predlabel == 'python_developer' :
                predlabel = 'Python Developer'
                predid= 7
            elif predlabel == 'network_administrator' :
                predlabel = 'Network Administrator'
                predid= 8
            elif predlabel == 'security_analyst' :
                predlabel = 'Security Analyst'
                predid= 9


            sql = "SELECT * FROM job JOIN company ON company.company_id = job.comp_id JOIN category ON category.category_id = job.cat_id where cat_id = %s"
            data = (predid)
            cur.execute(sql,data)
            rows = cur.fetchall()
            res = jsonify(rows)
            res.status_code = 200
            os.remove(filename)
            conn.close()
            cur.close()            
            return res

        else:
            errors[file.filename] = 'File type is not allowed'

        if success and errors:
            errors['message'] = 'File(s) successfully uploaded'
            res = jsonify(errors)
            res.status_code = 500
            conn.close()
            cur.close()
            return res
        if success:
            res = jsonify({'message': 'Files successfully uploaded'})
            res.status_code = 201
            conn.close()
            cur.close()
            return res
        else:
            res = jsonify(errors)
            res.status_code = 500
            conn.close()
            cur.close()
            return res
    else:
        err = jsonify(errors)
        err.status_code = 500
        conn.close()
        cur.close()
        return err


if __name__ == '__main__':
    app.run(debug=True)
