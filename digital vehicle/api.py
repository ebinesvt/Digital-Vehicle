from flask import Blueprint,request
from database import *
import demjson
api=Blueprint("api",__name__)

@api.route('/login')
def login():
	data={}
	data['method']='login'
	username=request.args['username']
	password=request.args['password']
	q="SELECT * FROM login WHERE username='%s' AND password='%s'" %(username,password)
	res=select(q)
	print(res)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return demjson.encode(data)

@api.route('/View_message')
def View_message():
	data={}
	data['method']='View_message'
	logid=request.args['logid']
	q="SELECT * FROM msgs WHERE user_id=(SELECT user_id FROM users WHERE login_id='%s')" %(logid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return demjson.encode(data)

@api.route('/addmessage')
def addmessage():
	data={}
	data['method']='addmessage'
	logid=request.args['logid']
	desc=request.args['desc']
	q="INSERT INTO msgs VALUES(NULL,(SELECT user_id FROM users WHERE login_id='%s'),'%s','pending',CURDATE())" %(logid,desc)
	insert(q)
	data['status']='success'
	return demjson.encode(data)

@api.route('/View_vehicle')
def View_vehicle():
	data={}
	data['method']='View_vehicle'
	logid=request.args['logid']
	q="SELECT * FROM vehicles WHERE user_id =(SELECT user_id FROM users WHERE login_id='%s')" %(logid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return demjson.encode(data)

@api.route('/View_Punishments')
def View_Punishments():
	data={}
	data['method']='View_Punishments'
	logid=request.args['logid']
	q="SELECT * FROM punishments INNER JOIN punishment_type USING(punish_type_id) INNER JOIN police USING(police_id) WHERE user_id =(SELECT user_id FROM users WHERE login_id='%s')" %(logid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return demjson.encode(data)

@api.route('/View_amount')
def View_amount():
	data={}
	data['method']='View_amount'
	pid=request.args['pid']
	q="SELECT * FROM punishments INNER JOIN punishment_type USING(punish_type_id) INNER JOIN police USING(police_id) WHERE punishment_id='%s'" %(pid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return demjson.encode(data)


@api.route('/pay_amount')
def pay_amount():
	data={}
	data['method']='pay_amount'
	logid=request.args['logid']
	amount=request.args['amount']
	q="SELECT * FROM payments WHERE user_id=(SELECT user_id FROM users WHERE login_id='%s') AND payment_date=CURDATE()" %(logid)
	res=select(q)
	if res:
		data['status']='duplicate'
	else:
		q="INSERT INTO payments VALUES(NULL,(SELECT user_id FROM users WHERE login_id='%s'),'%s',CURDATE())" %(logid,amount)
		insert(q)
		data['status']='success'
	return demjson.encode(data)


@api.route('/View_documents')
def View_documents():
	data={}
	logid=request.args['logid']
	data['method']='View_documents'
	q="SELECT * FROM docs INNER JOIN doc_type USING(doc_type_id) WHERE user_id=(SELECT user_id FROM users WHERE login_id='%s')" %(logid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return demjson.encode(data)