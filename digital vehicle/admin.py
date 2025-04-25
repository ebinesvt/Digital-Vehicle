from flask import Blueprint,redirect,url_for,render_template,request
from database import *
import uuid

admin=Blueprint("admin",__name__)

@admin.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template("adminhome.html")

@admin.route('/adminpregistration',methods=['get','post'])
def adminpregistration():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=='delete':
		q="DELETE FROM login WHERE login_id=(SELECT login_id FROM police WHERE police_id='%s')" %(id)
		delete(q)
		q="DELETE FROM police WHERE police_id='%s'" %(id)
		delete(q)
		return redirect(url_for("admin.adminpregistration"))
	if action=='update':
		q="SELECT * FROM police WHERE police_id='%s'" %(id)
		res=select(q)
		data['police']=res
	if 'update' in request.form:
		station_name=request.form['station_names']
		phone=request.form['phones']
		email=request.form['emails']
		place=request.form['places']
		pincode=request.form['pincodes']
		q="UPDATE police SET station_name='%s',phone='%s',email='%s',place='%s',pincode='%s' WHERE police_id='%s'" %(station_name,phone,email,place,pincode,id)
		update(q)
		return redirect(url_for("admin.adminpregistration")) 
	if 'submit' in request.form:
		station_name=request.form['station_name']
		phone=request.form['phone']
		email=request.form['email']
		place=request.form['place']
		pincode=request.form['pincode']
		username=request.form['username']
		password=request.form['password']
		q="INSERT INTO login VALUES(NULL,'%s','%s','police')"%(username,password)
		id=insert(q)
		q="INSERT INTO police VALUES(NULL,'%s','%s','%s','%s','%s','%s')" %(id,station_name,phone,email,place,pincode)
		insert(q)
	q="select * from police"
	res=select(q)
	data['adminpregistration']=res
	return render_template("adminpregistration.html",data=data)


@admin.route('/admindocumentmanage',methods=['get','post'])
def admindocumentmanage():
	data={}
	if 'submit' in request.form:
		doc_type_name=request.form['doc_type_name']
		doc_type_desc=request.form['doc_type_desc']
		q="INSERT INTO doc_type VALUES(NULL,'%s','%s')" %(doc_type_name,doc_type_desc)
		insert(q)
	q="select * from doc_type"
	res=select(q)
	data['admindocumentmanage']=res
	return render_template("admindocumentmanage.html",data=data)

@admin.route('/adminpunishmanage',methods=['get','post'])
def adminpunishmanage():
	data={}
	if 'submit' in request.form:
		punishment_name=request.form['punishment_name']
		punishment_desc=request.form['punishment_desc']
		penalty_desc=request.form['penalty_desc']
		amount=request.form['amount']
		q="INSERT INTO punishment_type VALUES(NULL,'%s','%s','%s','%s')" %(punishment_name,punishment_desc,penalty_desc,amount)
		insert(q)
	q="select * from punishment_type"
	res=select(q)
	data['adminpunishmanage']=res
	return render_template("adminpunishmanage.html",data=data)

@admin.route('/adminmuseracc',methods=['get','post'])
def adminmuseracc():
	data={}
	if 'submit' in request.form:
		first_name=request.form['first_name']
		last_name=request.form['last_name']
		latitude=request.form['latitude']
		longitude=request.form['longitude']
		place=request.form['place']
		pincode=request.form['pincode']
		house_name=request.form['house_name']
		gender=request.form['gender']
		dob=request.form['dob']
		photo=request.files['photo']
		path='static/uploads/'+str(uuid.uuid4())+photo.filename
		photo.save(path)
		phone=request.form['phone']
		email=request.form['email']
		username=request.form['username']
		password=request.form['password']
		q="insert into login values(null,'%s','%s','user')"%(username,password)
		id=insert(q)
		q="INSERT INTO users VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')" %(id,first_name,last_name,latitude,longitude,place,pincode,house_name,gender,dob,path,phone,email)
		insert(q)
	q="select * from users"
	res=select(q)
	data['adminmuseracc']=res
	return render_template("adminmuseracc.html",data=data)

@admin.route('/adminudoc',methods=['get','post'])
def adminudoc():
	data={}
	if 'submit' in request.form:
		username=request.form['username']
		doc_type_id=request.form['doc_type_id']
		# doc=request.files['doc']
		# path='static/uploads/'+str(uuid.uuid4())+doc.filename
		# print(path)
		# doc.save(path)
		# image=request.files['doc']
		# path="static/uploads/"+str(uuid.uuid4())+image.filename
		# image.save(path)
		image=request.files['image']
		path='static/uploads/'+str(uuid.uuid4())+image.filename
		print("path=",path)
		image.save(path)
		no=request.form['no']
		q="INSERT INTO docs VALUES(NULL,'%s','%s','/%s','%s')"%(doc_type_id,username,path,no)
		insert(q)
	q="select *,concat(first_name,' ',last_name)as username from users"
	res=select(q)
	data['docs']=res
	q="select * from doc_type"
	res=select(q)
	data['doc']=res
	q="SELECT *,CONCAT(first_name,' ',last_name)AS NAME FROM docs INNER JOIN doc_type USING(doc_type_id)INNER JOIN users USING(user_id)"
	res=select(q)
	# print(res)
	data['docc']=res
	return render_template('adminudoc.html',data=data)



@admin.route("/downloadlogfile/<path>")
def DownloadLogFile (path = None):
    if path is None:
        self.Error(400)
    try:
        return send_file(path, as_attachment=True)
    except Exception as e:
        self.log.exception(e)
        self.Error(400)

# @admin.route("/file")
# def file():
# 	id=request.args['id']
# 	print(id)
# 	return send_file(id,as_attachment=True)





@admin.route('/adminvmsgs',methods=['get','post'])
def adminvmsgs():
	data={}
	q="select *,concat(first_name,' ',last_name)as name from msgs inner join users using(user_id)"
	res=select(q)
	data['msgs']=res
	j=0
	for i in range(1,len(res)+1):
		print('submit'+str(i))
		if 'submit' + str(i) in request.form:
			reply=request.form['reply'+str(i)]
			q="UPDATE msgs SET reply='%s' WHERE msg_id='%s'" %(reply,res[j]['msg_id'])
			update(q)
			return redirect(url_for('admin.adminvmsgs'))
		j=j+1
	return render_template("adminvmsgs.html",data=data)

@admin.route('/adminvpunish',methods=['get','post'])
def adminvpunish():
	data={}
	q="SELECT *,concat(first_name,' ',last_name)AS NAME FROM punishments INNER JOIN punishment_type USING(punish_type_id)INNER JOIN users USING(user_id)INNER JOIN police USING(police_id)"
	res=select(q)
	data['vpunish']=res
	return render_template("adminvpunish.html",data=data)

@admin.route('/adminvpenalty',methods=['get','post'])
def adminvpenalty():
	data={}
	id=request.args['id']
	q="SELECT * from punishment_type WHERE punish_type_id='%s'" %(id)
	res=select(q)
	data['pen']=res
	return render_template("adminvpenalty.html",data=data)

@admin.route('/adminvehicleinfo',methods=['get','post'])
def adminvehicleinfo():
	data={}
	if 'submit' in request.form:
		username=request.form['username']
		reg_no=request.form['reg_no']
		man_year=request.form['man_year']
		brand=request.form['brand']
		model=request.form['model']
		q="INSERT INTO vehicles VALUES(NULL,'%s','%s','%s','%s','%s')"%(username,reg_no,man_year,brand,model)
		insert(q)
	q="SELECT *,concat(first_name,' ',last_name)as name from users"
	res=select(q)
	data['vehicles']=res
	q="SELECT *,concat(first_name,' ',last_name)as name from vehicles inner join users using(user_id)"
	res=select(q)
	data['ve']=res
	return render_template("adminvehicleinfo.html",data=data)