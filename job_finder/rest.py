from typing import final
from flask import json, jsonify, request, session, make_response
from pymysql import connect, connections
from werkzeug.security import generate_password_hash, check_password_hash
from flaskext.mysql import MySQL
from datetime import timedelta
from flask import Flask
from flask_cors import CORS
from flask_expects_json import expects_json
import pymysql

app = Flask(__name__)
app.secret_key = "secret key"
app.config['PERMANENT_SESSION_LIFETIME'] =  timedelta(minutes=10)
CORS(app)

mysql = MySQL()

# create schema
schema = {
  "type": "object",
  "properties": {
    "name": { "type": "string" },
    "email": { "type": "string" }
  },
  "required": ["email"],
  "required": ["username"]
}

# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'Nanonano17.'
app.config['MYSQL_DATABASE_DB'] = 'jobfinder'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'
mysql.init_app(app)


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
    conn=mysql.connect()
    cur=conn.cursor(pymysql.cursors.DictCursor)
    try:

        cur.execute("Select * from job")
        rows = cur.fetchall()
        resp = jsonify(rows)
        resp.status_code=200
        return resp
    except Exception as e:
        print (e)
    finally:
        cur.close()
        conn.close


@app.route('/profile', methods=['PUT','GET'])
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
                    #do not save password as a plain text
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
                conn=mysql.connect()
                cur=conn.cursor(pymysql.cursors.DictCursor)
                try:
                    cur.execute("SELECT id userid, full_name name, email user_email, address user_addres, phone user_phone FROM user WHERE id=2")
                    rows = cur.fetchall()
                    resp = jsonify(rows)
                    resp.status_code=200
                    return resp
                except Exception as e:
                    print (e)
                finally:
                    cur.close()
                    conn.close

@app.route('/register', methods=['POST'])
@expects_json(schema)
def register():
    conn = None;
    cursor = None;
    if 'id' in session:
        return not_found()

    else:
        _json = request.json
        _fullname = _json['fullname']
        _username = _json['username']
        _email = _json['email']
        _password = _json['pwd']
        # validate the received values

        #check user exists
        if _username and _email and _password and request.method == 'POST':
            #do not save password as a plain text
            _hashed_password = generate_password_hash(_password)
            # save edits
            sql = "INSERT INTO user (full_name, email, username, password) VALUES(%s, %s, %s, %s)"
            data = (_fullname,_username, _email, _hashed_password,)
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
    conn = None;
    cursor = None;

    _username = request.json['username']
    _password = request.json['password']

    # validate the received values

    #check user exists
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
            return jsonify({'message' : 'You are logged in successfully'})
        else:
            resp = jsonify({'message' : 'Bad Request - invalid password'})
            resp.status_code = 400
            return resp
    else:
        resp = jsonify({'message' : 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp



@app.route('/logout')
def logout():
    if 'id' in session:
        session.pop('id', None)
    return jsonify({'message' : 'You successfully logged out'})





if __name__ == "__main__":
    app.run()