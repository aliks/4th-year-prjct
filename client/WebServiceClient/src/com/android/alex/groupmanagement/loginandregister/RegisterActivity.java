package com.android.alex.groupmanagement.loginandregister;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.alex.groupmanagement.MainActivity;
import com.android.alex.groupmanagement.R;
import com.android.alex.services.SoapService;

public class RegisterActivity extends Activity 
{
	private Button registerUser;
	private EditText nameTxt;
	private EditText ageTxt;
	private EditText passTxt;
	private SoapService ss;
	
	private String name;
	private int age;
	private Double lat;
	private Double longt;
	private String pass;
	// GPS variables
	private Double[] gps;
	private LocationManager lm = null;
	private List<String> providers = null;
	private Location l = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_form);
 
        ss = new SoapService();
        registerUser = (Button) findViewById(R.id.btnRegister);
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        nameTxt = (EditText) findViewById(R.id.reg_fullname);
        ageTxt = (EditText) findViewById(R.id.reg_age);
        passTxt = (EditText) findViewById(R.id.reg_password);
 
        // listen to register Button
        registerUser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// validate inputs
				collectData();
				// call register new user web service method
				ss.regUser(name, pass, age, lat, longt);
				// Switching to Main screen
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
			}
		});
        
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
    }
    
    private void collectData() {
    	name = nameTxt.getText().toString();
    	age = Integer.parseInt(ageTxt.getText().toString());
    	pass = passTxt.getText().toString();
    	// CALL METHOD TO FIND LOCATION
    	Double[] current_location = getLocation();
		if (current_location[0] != null) {
			lat = current_location[0];
			longt = current_location[1];
		} else {
			// set default location to Glasgow Uni SAW & Boyd Orr location
			lat = 55.8738;
    		longt = -4.292178;
		}
    }
	private Double[] getLocation() {
		gps = new Double[2];
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		providers = lm.getProviders(true);
		/*
		 * Loop over the array backwards, and if you get an accurate location,
		 * then break out the loop
		 */
		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		if (l != null) {
			gps[0] = l.getLatitude();
			gps[1] = l.getLongitude();
		}
		return gps;
	}
}
