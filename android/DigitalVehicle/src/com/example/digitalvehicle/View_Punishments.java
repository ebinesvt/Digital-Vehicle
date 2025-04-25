package com.example.digitalvehicle;

import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class View_Punishments extends Activity implements JsonResponse,OnItemClickListener{
	ListView l1;
	SharedPreferences sh;
	String logid;
	String[] punishment_id, punishment_type_name,punish_type_desc,penalty_desc,penalty_amount,station_name,phone,all;
String pid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view__punishments);
		
		
		l1=(ListView)findViewById(R.id.punish);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		JsonReq JR=new JsonReq(getApplicationContext());
        JR.json_response=(JsonResponse) View_Punishments.this;
        String q = "View_Punishments?logid="+logid;
        q=q.replace(" ","%20");
        JR.execute(q);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view__punishments, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_Punishments")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					punishment_id=new String[ja1.length()];
					punishment_type_name=new String[ja1.length()];
					punish_type_desc=new String[ja1.length()];
					penalty_desc=new String[ja1.length()];
					penalty_amount=new String[ja1.length()];
					station_name=new String[ja1.length()];
					phone=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	
						punishment_id[i]=ja1.getJSONObject(i).getString("punishment_id");
						punishment_type_name[i]=ja1.getJSONObject(i).getString("punish_type_name");
						punish_type_desc[i]=ja1.getJSONObject(i).getString("punish_type_desc");
						penalty_desc[i]=ja1.getJSONObject(i).getString("penalty_desc");
						penalty_amount[i]=ja1.getJSONObject(i).getString("penalty_amount");
						station_name[i]=ja1.getJSONObject(i).getString("station_name");
						phone[i]=ja1.getJSONObject(i).getString("phone");
all[i]="Punishment :"+punishment_type_name[i]+"\n\nDescription:"+punish_type_desc[i]+"\n\nPenalty:"+penalty_desc[i]+"\n\nAmount:"+penalty_amount[i]+"\n\nPolice Station:"+station_name[i]+"\n\nPhone:"+phone[i];
					}
					ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,all);
					l1.setAdapter(ar);
					l1.setOnItemClickListener(this);
				}
				else
				{
					Toast.makeText(getApplicationContext(), " FAILED TO LOAD punishment details.....TRY AGAIN!!", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		pid=punishment_id[arg2];
		Editor e=sh.edit();
		e.putString("pid", pid);
		e.commit();
		selectapart();
	}
	private void selectapart() {
        final CharSequence[] items = { "Yes", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(View_Punishments.this);
        builder.setTitle("Pay Penalty");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Yes"))
                {
                	
                	startActivity(new Intent(getApplicationContext(),Payment.class));
                } 
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
