# app.py
import csv
import numpy as np
import pandas as pd
import zipfile
import numpy as np
import re
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
import sqlalchemy

app = Flask(__name__)


#bucket_name = os.environ.get('CLOUD_STORAGE_BUCKET')
#UPLOAD_FOLDER = os.environ['CLOUD_STORAGE_UPLOAD']
#app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024

ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])
app.secret_key = "secret key"
app.config['PERMANENT_SESSION_LIFETIME'] = timedelta(minutes=10)
CORS(app)


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

conn = sqlalchemy.create_engine('mysql+pymysql://myjobfinder:Nanonano17@34.101.245.71/jobfinder')                      
cursor = conn.connect()
# MySQL configurations


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
    try:
        cursor.execute("Select * from job")
        rows = cursor.fetchall()
        resp = jsonify(rows)
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
    finally:
        print("succes")


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
                cursor.execute(sql, data)
                resp = jsonify('User updated successfully!')
                resp.status_code = 200
                return resp
            else:
                return not_found()
        else:
            
            try:
                cursor.execute(
                    "SELECT id userid, full_name name, email user_email, address user_addres, phone user_phone FROM user WHERE id=2")
                rows = cursor.fetchall()
                resp = jsonify(rows)
                resp.status_code = 200
                return resp
            except Exception as e:
                print(e)
            finally:
                print("succes")


@app.route('/register', methods=['POST'])
@expects_json(schema)
def register():
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
            cursor.execute(sql, data)
            resp = jsonify('User added successfully!')
            resp.status_code = 200
            return resp
        else:
            return not_found()


@app.route('/login', methods=['POST'])
def login():

    _username = request.json['username']
    _password = request.json['password']

    # validate the received values

    # check user exists

    sql = "SELECT * FROM user WHERE username = %s"
    sql_where = (_username)

    _dbuser = cursor.execute(sql, sql_where)

    if _dbuser:
        if check_password_hash(_dbuser[4], _password):
            session['id'] = _dbuser[0]
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

if __name__ == '__main__':
    app.run(debug=True)
