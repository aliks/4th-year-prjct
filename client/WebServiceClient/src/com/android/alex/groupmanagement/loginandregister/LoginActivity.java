package com.android.alex.groupmanagement.loginandregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.alex.groupmanagement.MainActivity;
import com.android.alex.groupmanagement.R;
import com.android.alex.services.SoapService;

public class LoginActivity extends Activity 
{
	private Button loginButton;
	private EditText username;
	private EditText pass;
	private SoapService ss;
	private Long id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);
        ss = new SoapService();
 
        loginButton = (Button) findViewById(R.id.btnLogin);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        username = (EditText) findViewById(R.id.usrname_ed);
        pass = (EditText) findViewById(R.id.passwrd_ed);
        
        // listener to login button
        loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// validate input
				// send data
				verify();
				if (id != 0) {
					// Switching to Main screen
					Bundle dataBundle = new Bundle();
					dataBundle.putLong("userID", id);
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					i.putExtras(dataBundle);
					startActivity(i);
				}
			}
		});
 
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    private void verify() {	
    	String usr = username.getText().toString();
    	String passwrd = pass.getText().toString();
    	id = ss.verifyUser(usr, passwrd);
    }
}
