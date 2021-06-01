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
import matplotlib.pyplot as plt
import re
from keras.models import load_model
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
from google.cloud import storage
import gcsfs
import h5py

#fs = gcsfs.GCSFileSystem(project='jobfinder-demo')
app = Flask(__name__)
api = Api(app)


#bucket_name = os.environ.get('CLOUD_STORAGE_BUCKET')
#UPLOAD_FOLDER = os.environ['CLOUD_STORAGE_UPLOAD']
#app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024

ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])
app.secret_key = "secret key"
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
CREDENTIALS = 'cred.json'
MODEL_PATH = 'gs://data-mlnya/glove_model.h5'
TOKEN_PATH = 'gs://data-mlnya/tokenizer_data_new.pkl'

FS = gcsfs.GCSFileSystem(project=PROJECT_NAME,
                         token=CREDENTIALS)
with FS.open(MODEL_PATH, 'rb') as model_file:
    glove_odel = load_model(model_file)


with FS.open(TOKEN_PATH, 'rb') as f:
    data = pickle.load(f)
    tokenizer = data['tokenizer']
    num_words = data['num_words']
    max_length = data['maxlen']
    padding_type = data['padding_type']
    trunc_type = data['trunc_type']
    pad_sequences = data['pad_sequences']
    label_index = data['label_index']


# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'Nanonano17'
app.config['MYSQL_DATABASE_DB'] = 'jobfinder'
app.config['MYSQL_DATABASE_HOST'] = '34.101.245.71'
mysql.init_app(app)


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


def not_found(error=None):
    message = {
        'status': 404,
        'message': 'Not Found: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404

    return resp


@app.route('/')
def userlist():
    conn = mysql.connect()
    cur = conn.cursor(pymysql.cursors.DictCursor)
    try:

        cur.execute("Select * from job")
        rows = cur.fetchall()
        resp = jsonify(rows)
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
    finally:
        cur.close()
        conn.close


@app.route('/profile', methods=['PUT', 'GET'])
def update_user():
    conn = None
    cursor = None
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
                cursor = conn.cursor()
                cursor.execute(sql, data)
                conn.commit()
                resp = jsonify('User updated successfully!')
                resp.status_code = 200
                return resp
            else:
                return not_found()
        else:
            conn = mysql.connect()
            cur = conn.cursor(pymysql.cursors.DictCursor)
            try:
                cur.execute(
                    "SELECT id userid, full_name name, email user_email, address user_addres, phone user_phone FROM user WHERE id=2")
                rows = cur.fetchall()
                resp = jsonify(rows)
                resp.status_code = 200
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
    cursor = None
    if 'id' in session:
        return not_found()

    else:
        _json = request.json
        _fullname = _json['fullname']
        _username = _json['username']
        _email = _json['email']
        _password = _json['pwd']
        # validate the received values

        # check user exists
        if _username and _email and _password and request.method == 'POST':
            # do not save password as a plain text
            _hashed_password = generate_password_hash(_password)
            # save edits
            sql = "INSERT INTO user (full_name, email, username, password) VALUES(%s, %s, %s, %s)"
            data = (_fullname, _username, _email, _hashed_password,)
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(sql, data)
            conn.commit()
            resp = jsonify('User added successfully!')
            resp.status_code = 200
            return resp
        else:
            return not_found()


@app.route('/login', methods=['POST'])
def login():
    conn = None
    cursor = None

    _username = request.json['username']
    _password = request.json['password']

    # validate the received values

    # check user exists
    conn = mysql.connect()
    cursor = conn.cursor()

    sql = "SELECT * FROM user WHERE username = %s"
    sql_where = (_username)

    cursor.execute(sql, sql_where)
    row = cursor.fetchone()

    if row:
        if check_password_hash(row[4], _password):
            session['id'] = row[0]
            cursor.close()
            conn.close()
            return jsonify({'message': 'You are logged in successfully'})
        else:
            resp = jsonify({'message': 'Bad Request - invalid password'})
            resp.status_code = 400
            return resp
    else:
        resp = jsonify({'message': 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp


@app.route('/logout')
def logout():
    if 'id' in session:
        session.pop('id', None)
    return jsonify({'message': 'You successfully logged out'})


@app.route('/upload', methods=['POST'])
def upload_file():
    # check if the post request has the file part
    if 'files' not in request.files:
        resp = jsonify({'message': 'No file part in the request'})
        resp.status_code = 400
        return resp

    files = request.files.getlist('files')

    errors = {}
    success = False

    for file in files:
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save('gcs://data-mlnya/uploads/', filename)
            success = True
            # Ekstrak pdf ke text
            # creating a pdf file object
            p_file = open('gcs://data-mlnya/uploads/' + filename, 'rb')
            # creating a pdf reader object
            p_read = PyPDF2.PdfFileReader(p_file)
            # creating a page object
            p_obj = p_read.getPage(0)

            # extracting text from page
            inputs = p_obj.extractText()
            # closing the pdf file object

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

            for (label, p) in zip(labs, proba):
                output = (list(map("{}: {:.2f}%".format, label, p*100)))

            # create JSON object
            #output = {'prediction': pred_text, 'confidence': confidence}
            p_file.close()

            json_file = json.dumps(output)
            print(json_file)
            return json_file

        else:
            errors[file.filename] = 'File type is not allowed'

    if success and errors:
        errors['message'] = 'File(s) successfully uploaded'
        resp = jsonify(errors)
        resp.status_code = 500
        return resp
    if success:
        resp = jsonify({'message': 'Files successfully uploaded'})
        resp.status_code = 201
        return resp
    else:
        resp = jsonify(errors)
        resp.status_code = 500
        return resp


if __name__ == '__main__':
    app.run(debug=True)
