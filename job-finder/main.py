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
import sqlalchemy

app = Flask(__name__)
api = Api(app)


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
FS = gcsfs.GCSFileSystem(project=PROJECT_NAME, token=CREDENTIALS)

print(" error line 70")

print(" error line 86")

mysql = MySQL()


# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'Nanonano17'
app.config['MYSQL_DATABASE_DB'] = 'jobfinder'
app.config['MYSQL_DATABASE_HOST'] = '34.101.245.71'
mysql.init_app(app)

PROJECT_NAME = 'jobfinder-demo'
cred = 'cred.json'
client = storage.Client()
bucket = client.get_bucket('user-cv')
app.secret_key = "kepo amat lu padahal rahaisa"
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024
prep_path = 'gs://data-mlnya/tokenizer_data_new.pkl'
FS = gcsfs.GCSFileSystem(project=PROJECT_NAME, token=cred)
jenis_file = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])
app.config['PERMANENT_SESSION_LIFETIME'] = timedelta(minutes=10)
UPLOAD_FOLDER = Path.cwd()
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

CORS(app)


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in jenis_file


def not_found(error=None):
    message = {
        'status': 404,
        'message': 'Not Found: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404

    return resp


@app.route('/joblist')
def joblist():
    conn = mysql.connect()
    cur = conn.cursor(pymysql.cursors.DictCursor)
    try:
        sql = "SELECT * FROM job JOIN company ON company.company_id = job.comp_id JOIN category ON category.category_id = job.cat_id"
        cur.execute(sql)
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
                sql = "UPDATE user SET address=%s, phone=%s,full_name=%s WHERE id=%s"
                data = (_address, _phone, _fullname, session['id'])
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
            cursor = conn.cursor()
            try:
                sql = "SELECT * from user WHERE id=%s"
                data = (session['id'])
                cursor.execute(sql, data)
                rows = cursor.fetchall()
                resp = jsonify(rows)
                resp.status_code = 200
                return resp
            except Exception as e:
                print(e)
            finally:
                cursor.close()
                conn.close


@app.route('/register', methods=['POST'])
@expects_json(schema)
def register():
    conn = None
    cursor = None
    if 'id' in session:
        resp = jsonify('Already logged in!')
        resp.status_code = 200
        return resp
    else:
        conn = mysql.connect()
        cursor = conn.cursor()
        _json = request.json
        _fullname = _json['fullname']
        _username = _json['username']
        _email = _json['email']
        _password = _json['pwd']
        # validate the received values
        sql = "SELECT * FROM user WHERE username = %s"
        sql_where = (_username)

        cursor.execute(sql, sql_where)
        row = cursor.fetchone()
        # check user exists

        if row:
            resp = jsonify({'message': 'Bad Request - username already exists'})
            resp.status_code = 400
            return resp
        else:
            conn = mysql.connect()
            cursor = conn.cursor()
            sql1 = "SELECT * FROM user WHERE email = %s"
            sql_where1 = (_email)

            cursor.execute(sql1, sql_where1)
            row1 = cursor.fetchone()
            if row1:
                resp = jsonify(
                    {'message': 'Bad Request - email already exists'})
                resp.status_code = 400
                return resp
            else:
                if _username and _email and _password and request.method == 'POST':
                    # do not save password as a plain text
                    _hashed_password = generate_password_hash(_password)
                    # save edits
                    sql = "INSERT INTO user (full_name, username, email, password) VALUES(%s, %s, %s, %s)"
                    data = (_fullname, _username, _email, _hashed_password,)
                    conn = mysql.connect()
                    cursor = conn.cursor()
                    cursor.execute(sql, data)
                    conn.commit()
                    resp = jsonify('User added successfully!')
                    resp.status_code = 200
                    return resp


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
            session['username'] =  row[2]
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


@app.route('/deletepdf', methods=['GET'])
def delete():
    conn = None
    cursor = None
    conn = mysql.connect()
    cursor = conn.cursor()
    if 'id' in session:
        sql_pdf = "UPDATE user SET file ='NULL' WHERE id=%s"
        seshid = session['id']
        sqlplace = (seshid)
        cursor.execute(sql_pdf, sqlplace)
        namafile = session['username'] + '.pdf'
        blob = bucket.blob(namafile)
        blob.delete()
        resp = jsonify('Data deleted successfully!')
        resp.status_code = 200
        return resp
    else:
        resp = jsonify('You need login')
        resp.status_code = 400
        return resp

