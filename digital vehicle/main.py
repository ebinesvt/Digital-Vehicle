from flask import Flask
from public import public
# from user import user
from admin import admin
from police import police
from api import api

app=Flask(__name__)

app.secret_key="secret_key"

app.register_blueprint(public)
# app.register_blueprint(user,url_prefix='/user')
app.register_blueprint(api,url_prefix='/api')
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(police,url_prefix='/police')

app.run(debug=True,port=5000,host='192.168.43.244')  