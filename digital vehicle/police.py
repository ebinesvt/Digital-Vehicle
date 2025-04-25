from flask import Blueprint,redirect,url_for,render_template,request,session,flash
from database import *
import uuid

police=Blueprint("police",__name__)

@police.route('/policehome',methods=['get','post'])
def policehome():
	return render_template("policehome.html")

@police.route('/policevusers',methods=['get','post'])
def policevusers():
	data={}
	q="select * from users"
	res=select(q)
	print(res)
	data['pu']=res
	return render_template("policevusers.html",data=data)

@police.route('/policesearch',methods=['get','post'])
def policesearch():
	data={}
	if 'searchs' in request.form:
		search=request.form['search']
		s=search+"%"
		q="SELECT *,concat(first_name,' ',last_name)AS name FROM users INNER JOIN docs USING(user_id) INNER JOIN vehicles USING(user_id) WHERE `number` LIKE '%s'" %(s)
		res=select(q)
		data['search_details']=res
	if 'submit' in request.form:
		search=request.form['search']
		s=search+"%"
		q="SELECT *,concat(first_name,' ',last_name)AS name FROM users INNER JOIN docs USING(user_id) INNER JOIN vehicles USING(user_id) WHERE `number` LIKE '%s'" %(s)
		res=select(q)
		data['de']=res
	return render_template("policesearch.html",data=data)

@police.route('/police_manage_punishment',methods=['get','post'])
def police_manage_punishment():
	data={}
	q="SELECT * FROM punishment_type"
	data['punish']=select(q)
	q="SELECT *,concat(first_name,' ',last_name)as name FROM users"
	data['users']=select(q)
	if 'add' in request.form:
		user=request.form['user']
		punishment=request.form['punishment']
		q="INSERT INTO punishments VALUES(NULL,'%s','%s',(SELECT police_id FROM police WHERE login_id='%s'),CURDATE(),'pending')" %(punishment,user,session['logid'])
		insert(q)
		return redirect(url_for("police.police_manage_punishment"))
	return render_template("police_manage_punishment.html",data=data)

@police.route('/police_view_punishment',methods=['get','post'])
def police_view_punishment():
	data={}
	q="SELECT *,CONCAT(first_name,' ',last_name)AS name FROM punishments INNER JOIN punishment_type USING(punish_type_id) INNER JOIN users USING(user_id)"
	data['punish_det']=select(q)
	return render_template("police_view_punishment.html",data=data)