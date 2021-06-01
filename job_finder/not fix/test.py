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
from sklearn.model_selection import train_test_split
from keras.models import load_model
import pickle
from flask import Flask
from flask_restful import reqparse, abort, Api, Resource
import PyPDF2

# Ekstrak pdf ke text
# creating a pdf file object
#pdfFileObj = open('example.pdf', 'rb')
# creating a pdf reader object
#pdfReader = PyPDF2.PdfFileReader(pdfFileObj)
# creating a page object
#pageObj = pdfReader.getPage(0)

# extracting text from page
#inputs = pageObj.extractText()
# closing the pdf file object
# pdfFileObj.close()


app = Flask(__name__)
api = Api(app)

glove_model = load_model(
    'D://Kuliah//Bangkit//Capstone//job_finder//api_ML//glove_model.h5')
with open("D://Kuliah//Bangkit//Capstone//job_finder//api_ML//tokenizer_data_new.pkl", 'rb') as f:
    data = pickle.load(f)
    tokenizer = data['tokenizer']
    num_words = data['num_words']
    max_length = data['maxlen']
    padding_type = data['padding_type']
    trunc_type = data['trunc_type']
    pad_sequences = data['pad_sequences']
    label_index = data['label_index']

# argument parsing
parser = reqparse.RequestParser()
parser.add_argument('query')


# Machine Learning model

class PredictSentiment(Resource):
    def get(self):

        args = parser.parse_args()
        user_query = args['query']
        inputs = np.array([user_query])
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

        return output


# Setup the Api resource routing here
# Route the URL to the resource
api.add_resource(PredictSentiment, '/predict')


if __name__ == '__main__':
    app.run(debug=True)
