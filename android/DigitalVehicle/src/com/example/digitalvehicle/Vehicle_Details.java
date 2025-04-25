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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Vehicle_Details extends Activity implements JsonResponse{
ListView l1;
SharedPreferences sh;
String logid;
String[] register_no,manfacture_year,brand,model,all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehicle__details);
		
		l1=(ListView)findViewById(R.id.vehicle);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		JsonReq JR=new JsonReq(getApplicationContext());
        JR.json_response=(JsonResponse) Vehicle_Details.this;
        String q = "View_vehicle?logid="+logid;
        q=q.replace(" ","%20");
        JR.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vehicle__details, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_vehicle")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					register_no=new String[ja1.length()];
					manfacture_year=new String[ja1.length()];
					brand=new String[ja1.length()];
					model=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	
						register_no[i]=ja1.getJSONObject(i).getString("register_no");
						manfacture_year[i]=ja1.getJSONObject(i).getString("manfacture_year");
						brand[i]=ja1.getJSONObject(i).getString("brand");
						model[i]=ja1.getJSONObject(i).getString("model");
						all[i]="Registration no :"+register_no[i]+"\n\nMFG Year:"+manfacture_year[i]+"\n\nBrand:"+brand[i]+"\n\nModel:"+model[i];
					}
					ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,all);
					l1.setAdapter(ar);
				}
				else
				{
					Toast.makeText(getApplicationContext(), " FAILED TO LOAD Vehicle details.....TRY AGAIN!!", Toast.LENGTH_SHORT).show();
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
