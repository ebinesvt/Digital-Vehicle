from flask import Blueprint,redirect,url_for,render_template,request
from database import *

user=Blueprint("user",__name__)

@user.route('/userhome',methods=['get','post'])
def userhome():
	return render_template("userhome.html")


