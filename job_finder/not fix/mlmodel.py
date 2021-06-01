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

app = Flask(__name__)
api = Api(app)

app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024

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


PROJECT_NAME = 'jobfinder-demo'
CREDENTIALS = 'cred.json'
MODEL_PATH = 'gs://data-mlnya/glove_model.h5'
TOKEN_PATH = 'gs://data-mlnya/tokenizer_data_new.pkl'
FS = gcsfs.GCSFileSystem(project=PROJECT_NAME,token=CREDENTIALS)

with FS.open(TOKEN_PATH, 'rb') as f:   
    data = pickle.load(f)
    tokenizer = data['tokenizer']
    num_words = data['num_words']
    max_length = data['maxlen']
    padding_type = data['padding_type']
    trunc_type = data['trunc_type']
    pad_sequences = data['pad_sequences']
    label_index = data['label_index']

glove_model = load_model('job-finder.h5')

mysql = MySQL()


# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'Nanonano17'
app.config['MYSQL_DATABASE_DB'] = 'jobfinder'
app.config['MYSQL_DATABASE_HOST'] = '34.101.245.71'
mysql.init_app(app)


ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route('/file-upload', methods=['POST'])
def upload_file():
	# check if the post request has the file part
	if 'file' not in request.files:
		resp = jsonify({'message' : 'No file part in the request'})
		resp.status_code = 400
		return resp
	file = request.files['file']
	if file.filename == '':
		resp = jsonify({'message' : 'No file selected for uploading'})
		resp.status_code = 400
		return resp

	if file and allowed_file(file.filename):
		filename = secure_filename(file.filename)
        pdfReader = PyPDF2.PdfFileReader(filename)
        # creating a page object
        pageObj = pdfReader.getPage(0)

        # extracting text from page
        inputs = pageObj.extractText()
        # closing the pdf file object

        labs = [list(label_index.keys())]

        stopwords = ["a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought","our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "span"]

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
        padded = pad_sequences(seq, maxlen=max_length,padding=padding_type, truncating=trunc_type)
        # print(str(padded)+'\n')

        proba = [glove_model.predict(padded)[0, 1:]]

        max = np.where(proba == np.amax(proba))
        for [i] in max:
            idx = i
        predlabel = labs[0][idx]
        #predprob = proba[idx]*100
        #print(list(map("{}: {:.2f}%".format,[predlabel],[predprob])))

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

                
        conn = mysql.connect()
        cur = conn.cursor(pymysql.cursors.DictCursor)

        sql = "SELECT * FROM job JOIN company ON company.company_id = job.comp_id JOIN category ON category.category_id = job.cat_id where cat_id = %s"
        data = (predid)
        cur.execute(sql,data)
        rows = cur.fetchall()
        resp = jsonify(rows)
        resp.status_code = 200
        return resp

	else:
		resp = jsonify({'message' : 'Allowed file types are txt, pdf, png, jpg, jpeg, gif'})
		resp.status_code = 400
		return resp




if __name__ == '__main__':
    app.run(debug=True)
