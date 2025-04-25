package com.example.digitalvehicle;

import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Complaint extends Activity implements JsonResponse{

	ListView l1;
	String logid,description;
	Button b1;
	EditText e1;
	SharedPreferences sh;
	int id;
	String[] message_desc,date,all,reply;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		Toast.makeText(getApplicationContext(), logid, Toast.LENGTH_LONG).show();
		e1=(EditText)findViewById(R.id.etcomplaintdescription);
	
		b1=(Button)findViewById(R.id.btaddcomplaints);
		l1=(ListView)findViewById(R.id.lvcomplaintstable);
		
		JsonReq JR=new JsonReq(getApplicationContext());
        JR.json_response=(JsonResponse) Complaint.this;
        String q = "View_message?logid="+logid;
        q=q.replace(" ","%20");
        JR.execute(q);
        
        
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				description=e1.getText().toString();
				
				JsonReq JR=new JsonReq(getApplicationContext());
		        JR.json_response=(JsonResponse) Complaint.this;
		        String q = "addmessage?desc="+description+"&logid="+logid;
		        q=q.replace(" ","%20");
		        JR.execute(q);
		        }
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.complaint, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_message")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
//					complaint_id=new String[ja1.length()];
					message_desc=new String[ja1.length()];
					date=new String[ja1.length()];
					reply=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	
//						complaint_id[i]=ja1.getJSONObject(i).getString("complaint_id");
						message_desc[i]=ja1.getJSONObject(i).getString("msg_desc");
						reply[i]=ja1.getJSONObject(i).getString("reply");
						date[i]=ja1.getJSONObject(i).getString("date");
						all[i]="Message :"+message_desc[i]+"\n\nReply :"+reply[i]+"\n\nDate:"+date[i];
					}
					ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,all);
					l1.setAdapter(ar);
				}
				else
				{
					Toast.makeText(getApplicationContext(), " FAILED TO LOAD MESSAGES.....TRY AGAIN!!", Toast.LENGTH_SHORT).show();
				}
			
			}
		 if(method.equalsIgnoreCase("addmessage")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					Toast.makeText(getApplicationContext(), "Message Send", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),Complaint.class));
				}
				else{
					Toast.makeText(getApplicationContext(), "Failed. Try again after sometime..", Toast.LENGTH_LONG).show();
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
			  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),Home.class);			
			startActivity(b);
		}

}
