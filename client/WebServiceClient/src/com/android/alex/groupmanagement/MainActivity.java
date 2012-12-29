package com.android.alex.groupmanagement;

import java.io.IOException;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.android.alex.services.SoapService;
import com.android.alex.services.domain.Group;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity 
{
    private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//new SoapService().createGroupServ("home");
		//new SoapService().regUser("NewUser", 20, 20.0, 20.0);
		//new SoapService().getAllGroups();
		new SoapService().putUserToGroup("NewUser", 20, "home");
		//new SoapService().removeUserFromGroup("NewUser", 20, "home");
		//new SoapService().deleteGroup("home");
		//new SoapService().findGroup("geeks");
		//new SoapService().listAllUsers("geeks");
		//new SoapService().finduser("Alex");
		//new SoapService().update(10.0, 10.0, "NewUser", 20);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
