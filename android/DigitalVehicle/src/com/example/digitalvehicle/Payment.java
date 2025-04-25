package com.example.digitalvehicle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Payment extends Activity implements JsonResponse{
	Button b1;
	EditText e1,e2,e3,e4;
	SharedPreferences sh;
	String logid;
	String pid;
	private DatePickerDialog fromDatePickerDialog;

	private SimpleDateFormat dateFormatter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		
		b1=(Button)findViewById(R.id.btpay);
		e1=(EditText)findViewById(R.id.card);
		e2=(EditText)findViewById(R.id.cvv);
		e3=(EditText)findViewById(R.id.edate);
		e4=(EditText)findViewById(R.id.etamount);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		pid=sh.getString("pid", "");
		
		JsonReq JR= new JsonReq(getApplicationContext());
		JR.json_response=(JsonResponse)Payment.this;
		String q="View_amount?logid="+logid+"&pid="+pid;
		q=q.replace(" ","%20");
		JR.execute(q);
		
		e3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				e3.setInputType(InputType.TYPE_NULL);
				e3.requestFocus();
				setDateTimeField();
				dateFormatter = new SimpleDateFormat("MM-yyyy", Locale.US);
				fromDatePickerDialog.show();
			}
		});
		
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(e1.getText().toString().equals("") || e1.getText().toString().length()<16)
				{
					e1.setError("Enter a card number");
					e1.setFocusable(true);
				}else if(e2.getText().toString().equals("") || e2.getText().toString().length()<3)
				{
					e2.setError("Enter a CVV number");
					e2.setFocusable(true);
				}else if(e3.getText().toString().equals("") )
				{
					e3.setError("Enter a valid date");
					e3.setFocusable(true);
				}else
				{
					selectapart();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment, menu);
		return true;
	}
private void setDateTimeField() {
		
//		e1.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				fromDatePickerDialog.show();
//			}
//		});
		Toast.makeText(getApplicationContext(), "Date", Toast.LENGTH_LONG).show();
		Calendar newCalendar = Calendar.getInstance();
		fromDatePickerDialog =new DatePickerDialog(this,new OnDateSetListener() {
			
			public void onDateSet(DatePicker view,int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            e3.setText(dateFormatter.format(newDate.getTime()));	
//	            bdate=e1.getText().toString();
			}
		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
		String method=jo.getString("method");
		
		if(method.equalsIgnoreCase("View_amount")){
			
			String status=jo.getString("status");
			Log.d("pearl",status);
			
			if(status.equalsIgnoreCase("success")){
				
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					e4.setText(ja1.getJSONObject(0).getString("penalty_amount"));	
					startActivity(new Intent(getApplicationContext(),View_Punishments.class));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No pending payments", Toast.LENGTH_LONG).show();
			}
		
		}
		if(method.equalsIgnoreCase("pay_amount")){
			
			String status=jo.getString("status");
			Log.d("pearl",status);
			
			if(status.equalsIgnoreCase("success")){
				
				Toast.makeText(getApplicationContext(), "Paid successfully.", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplicationContext(),View_Punishments.class));
			}
			else if(status.equalsIgnoreCase("duplicate"))
			{
				Toast.makeText(getApplicationContext(), "No pending payments", Toast.LENGTH_LONG).show();
			}
			else 
					{
						Toast.makeText(getApplicationContext(), "Failed try again", Toast.LENGTH_LONG).show();
					}
		
		}
		} catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
			  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	private void selectapart() {
        final CharSequence[] items = { "Yes", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
        builder.setTitle("Are you sure to continue");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Yes"))
                {
                	
                	JsonReq JR= new JsonReq(getApplicationContext());
            		JR.json_response=(JsonResponse)Payment.this;
            		String q="pay_amount?logid="+logid+"&amount="+e4.getText().toString();
            		q=q.replace(" ","%20");
            		JR.execute(q);
            		
                } 
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



}
