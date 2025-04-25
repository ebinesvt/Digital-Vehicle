package com.example.digitalvehicle;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements JsonResponse{
	EditText e1,e2;
	Button b1;
	String uname,pass;
	SharedPreferences sh;
	String logid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		e1=(EditText)findViewById(R.id.etusername);
		e2=(EditText)findViewById(R.id.etpassword);
		b1=(Button)findViewById(R.id.btlogin);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(e1.getText().toString().equals(""))
				{
					e1.setError("Enter a Username");
					e1.setFocusable(true);
				}
				else if(e2.getText().toString().equals(""))
				{
					e2.setError("Enter a Password");
					e2.setFocusable(true);
				}
				else
				{
					uname=e1.getText().toString();
					pass=e2.getText().toString();
					
					JsonReq JR= new JsonReq(getApplicationContext());
					JR.json_response=(JsonResponse)Login.this;
					String q="login?username="+uname+"&password="+pass;
					q=q.replace(" ","%20");
					JR.execute(q);
				}
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try
		{
			
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("login"))
			{
				String status=jo.getString("status");
				Log.d("result", status);
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					String logid=ja.getJSONObject(0).getString("login_id");
					
					Editor e=sh.edit();
					e.putString("logid", logid);
					e.commit();
					
					String type=ja.getJSONObject(0).getString("usertype");
					if(type.equalsIgnoreCase("user"))
					{
						startActivity(new Intent(getApplicationContext(),Home.class));
					}
					
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Login Failed.\nTry Again.", Toast.LENGTH_LONG).show();
				}
						
			}
			
					
			
		}catch(Exception e)
		{
			
			Toast.makeText(getApplicationContext(), "Exception "+e+" Occured.", Toast.LENGTH_SHORT).show();
			
		}
		
	}
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),IPsettings.class);			
			startActivity(b);
		}


}
