package com.android.alex.services;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Log;

import com.android.alex.services.domain.DemandSpace;
import com.android.alex.services.domain.Group;
import com.android.alex.services.domain.User;

public class SoapService {
	private static final String NAMESPACE = "http://service/";
	private static final String URL = "http://ec2-54-228-218-128.eu-west-1.compute.amazonaws.com:8080/webapp/GroupManagementWebServiceService?wsdl";
	//private static final String URL = "http://10.0.2.2:8080/webapp/GroupManagementWebServiceService?wsdl";
	private SoapObject request;
	private SoapSerializationEnvelope envelope;
	private HttpTransportSE androidHttpTransport;

	private String result;

	public void loginService(String username, String password) 
	{
		request = new SoapObject(NAMESPACE, "loginUser");

		request.addProperty("username", username);
		request.addProperty("password", password);
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		
		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		androidHttpTransport = new HttpTransportSE(URL);
		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/loginUser",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		//Object results = null;
		SoapPrimitive results = null;
		// SoapObject results = null;
		try {
			//results = (Object) envelope.getResponse();
			results = (SoapPrimitive) envelope.getResponse();
			// results = (SoapObject)envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}
	}

	public void createGroupServ(String groupName) 
	{
		request = new SoapObject(NAMESPACE, "createGroup");
		request.addProperty("groupname", groupName);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		// envelope.addMapping(NAMESPACE, "Group", new Group().getClass());

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/createGroup",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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

	public String getAllGroups() {
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
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results == null)
			return null;
		else
			return results.toString();
	}

	public void regUser(String userName, String pass, int userAge, double latitude,
			double longitude) {
		request = new SoapObject(NAMESPACE, "registerUser");
		User newUser = new User(userName, pass, userAge, latitude, longitude);
		request.addProperty("arg0", newUser);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		//envelope.addMapping(NAMESPACE, "user", new User().getClass());

		// serialize double values
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/registerUser",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		/*
		Object results = null;
		// SoapPrimitive results = null;
		//SoapObject results = null;
		try {
			results = (Object) envelope.getResponse();
			// results = (SoapPrimitive) envelope.getResponse();
			//results = (SoapObject)envelope.getResponse();
		} catch (SoapFault e) {
			e.printStackTrace();
		}*/
	}

	public void putUserToGroup(String groupName, Long id) {
		request = new SoapObject(NAMESPACE, "subscribeToGroup");
		request.addProperty("arg0", groupName);
		request.addProperty("arg1", id);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport
					.call("http://service/groupManagementWebService/subscribeToGroup",
							envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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

	public void removeUserFromGroup(String groupName, Long id) {
		request = new SoapObject(NAMESPACE, "unsubscribeUserFromGroup");
		request.addProperty("arg0", groupName);
		request.addProperty("arg1", id);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport
					.call("http://service/groupManagementWebService/unsubscribeUserFromGroup",
							envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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

	public void deleteGroup(String group) {
		request = new SoapObject(NAMESPACE, "removeGroup");
		request.addProperty("arg0", group);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/removeGroup",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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

	public String findGroup(String group) {
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
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results == null)
			return null;
		else
			return results.toString();
	}

	public String listAllUsers(String group) {
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
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results == null)
			return null;
		else
			return results.toString();
	}

	// NOT SURE if should be used
	public String finduser(String name) {
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
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results == null)
			return null;
		else
			return results.toString();
	}

	public void update(double l, double lo, Long id) {
		request = new SoapObject(NAMESPACE, "updateLocation");
		request.addProperty("arg0", l);
		request.addProperty("arg1", lo);
		request.addProperty("arg2", id);

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
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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

	public String getMyGroups(Long id) {
		request = new SoapObject(NAMESPACE, "findUsersGrp");
		request.addProperty("arg0", id);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/findUsersGrp",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results == null)
			return null;
		else
			return results.toString();
	}

	public String findUsersLocation(String name, String grpName) {
		request = new SoapObject(NAMESPACE, "getUserLocation");
		request.addProperty("arg0", name);
		request.addProperty("arg1", grpName);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/getUserLocation",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results != null)
			return results.toString();
		else
			return result;
	}

	public Long verifyUser(String usr, String pass) {
		request = new SoapObject(NAMESPACE, "verifyUser");
		request.addProperty("arg0", usr);
		request.addProperty("arg1", pass);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);

		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/verifyUser",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		Log.v("response >> ", results.toString());
		return Long.parseLong(results.toString());
	}

	public String numberOfMembersInGroup(String groupName) {
		request = new SoapObject(NAMESPACE, "numberOfMembers");
		request.addProperty("arg0", groupName);

		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		
		androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(
					"http://service/groupManagementWebService/numberOfMembers",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results != null)
			return results.toString();
		else
			return "0";
	}
	
	public String findNearestFriend(String groupName, DemandSpace ds) {
		request = new SoapObject(NAMESPACE, "findNN");
		request.addProperty("arg0", groupName);
		request.addProperty("arg1", ds);
		//request.addProperty("arg2", ds.getY1());
		//request.addProperty("arg3", ds.getX2());
		//request.addProperty("arg4", ds.getY2());

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
					"http://service/groupManagementWebService/findNN",
					envelope);
//			Log.v(">>  ", androidHttpTransport.requestDump);
//			Log.v(">> ", androidHttpTransport.responseDump);

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
		if (results != null)
			return results.toString();
		else
			return result;
	}

	// add getUser age
}
