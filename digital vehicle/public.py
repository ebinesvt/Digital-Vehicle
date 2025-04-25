from flask import Blueprint,redirect,url_for,render_template,request,session
from database import *

public=Blueprint("public",__name__)


@public.route('/',methods=['get','post'])
def home():
	return render_template("index.html")
@public.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template("adminhome.html")
@public.route('/login',methods=['get','post'])
def login():
	if 'submit' in request.form:
		uname=request.form['username']
		passw=request.form['password']
		q="SELECT * FROM login WHERE username='%s' AND password='%s'" %(uname,passw)
		res=select(q)
		if len(res)>0:
			session['logid']=res[0]['login_id']
			if res[0]['usertype']=='admin':
				return redirect(url_for('admin.adminhome'))
			elif res[0]['usertype']=='user':
				return redirect(url_for('user.userhome'))
			elif res[0]['usertype']=='police':
				return redirect(url_for('police.policehome'))
	return render_template("login.html")

