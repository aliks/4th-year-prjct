package com.android.alex.groupmanagement;

import com.android.alex.services.SoapService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateGrpActivity extends Activity 
{
	private Button submit;
	private EditText ed;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_grp_form);
		setUpView();
	}

	private void setUpView() 
	{
		submit = (Button) findViewById(R.id.submit_b);
		ed = (EditText) findViewById(R.id.grp_name_ed);
		
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String newgroup = ed.getText().toString();
				new SoapService().createGroupServ(newgroup);
				finish();
			}
		});
	}
}
