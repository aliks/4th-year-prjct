package com.android.alex.services;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Log;

import com.android.alex.services.domain.Group;
import com.android.alex.services.domain.User;

public class SoapService 
{
	private static final String NAMESPACE = "http://service/";
	private static final String URL = "http://10.0.2.2:8080/GroupsManagement/GroupManagementWebServiceService?wsdl";

	private SoapObject request;
	private SoapSerializationEnvelope envelope;
	private HttpTransportSE androidHttpTransport;

	private String result;

	public void createGroupServ(String groupName) 
	{
		request = new SoapObject(NAMESPACE, "createGroup");
		request.addProperty("groupname", groupName);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		//envelope.addMapping(NAMESPACE, "Group", new Group().getClass());

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/createGroup",
					envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Object results = null;
		// SoapPrimitive results = null;
		// SoapObject results = null;
		try {
			results = (Object) envelope.getResponse();
			// results = (SoapPrimitive) envelope.getResponse();
			// results = (SoapObject)envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
	}

	public String getAllGroups() 
	{
		request = new SoapObject(NAMESPACE, "viewAllGroups");

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/viewAllGroups",
					envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Object results = null;
		try {
			results = (Object) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		Log.v("list", results.toString());
		return results.toString();
	}

	public void regUser(String userName, int userAge, double latitude,
			double longitude) {
		request = new SoapObject(NAMESPACE, "registerUser");
		User newUser = new User(userName, userAge, latitude, longitude);
		request.addProperty("arg0", newUser);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		envelope.addMapping(NAMESPACE, "user", new Group().getClass());

		// serialize double values
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/registerUser",
					envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Object results = null;
		// SoapPrimitive results = null;
		// SoapObject results = null;
		try {
			results = (Object) envelope.getResponse();
			// results = (SoapPrimitive) envelope.getResponse();
			// results = (SoapObject)envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
	}

	public void putUserToGroup(String name, int age, String groupName) 
	{
		request = new SoapObject(NAMESPACE, "subscribeToGroup");
		request.addProperty("arg0", name);
		request.addProperty("arg1", age);
		request.addProperty("arg2", groupName);
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport
					.call("http://service/groupManagementWebService/subscribeToGroup",
							envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		SoapObject results = null;
		try {
			results = (SoapObject) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
	}

	public void removeUserFromGroup(String name, int age, String groupName) 
	{
		request = new SoapObject(NAMESPACE, "unsubscribeUserFromGroup");
		request.addProperty("arg0", name);
		request.addProperty("arg1", age);
		request.addProperty("arg2", groupName);
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport
					.call("http://service/groupManagementWebService/unsubscribeUserFromGroup",
							envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		SoapObject results = null;
		try {
			results = (SoapObject) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
	}

	public void deleteGroup(String group)
	{
		request = new SoapObject(NAMESPACE, "removeGroup");
		request.addProperty("arg0", group);
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport
					.call("http://service/groupManagementWebService/removeGroup",
							envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		SoapObject results = null;
		try {
			results = (SoapObject) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
	}

	public String findGroup(String group)
	{
		request = new SoapObject(NAMESPACE, "searchGroup");
		request.addProperty("arg0", group);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/searchGroup",
					envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Object results = null;
		try {
			results = (Object) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		if(results == null) 
			return null;
		else
			return results.toString();
	}

	public String listAllUsers(String group)
	{
		request = new SoapObject(NAMESPACE, "getAllUsers");
		request.addProperty("arg0", group);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/getAllUsers",
					envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Object results = null;
		try {
			results = (Object) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		if(results == null) 
			return null;
		else
			return results.toString();
	}

	// NOT SURE if should be used
	public String finduser(String name)
	{
		request = new SoapObject(NAMESPACE, "searchUser");
		request.addProperty("arg0", name);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/searchUser",
					envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Object results = null;
		try {
			results = (Object) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		if(results == null) 
			return null;
		else
			return results.toString();
	}
	
	public void update(double l, double lo, String name, int i) 
	{
		request = new SoapObject(NAMESPACE, "updateLocation");
		request.addProperty("arg0", l);
		request.addProperty("arg1", lo);
		request.addProperty("arg2", name);
		request.addProperty("arg3", i);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		
		// serialize double values
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/updateLocation",
					envelope);
			Log.v(">>  ", androidHttpTransport.requestDump);
			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		SoapObject results = null;
		try {
			results = (SoapObject) envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		
	}

	//add getUser location
	
	//add getUser age
}
