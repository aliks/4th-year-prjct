package com.android.alex.services.domain;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class User implements KvmSerializable
{

	private String userName;
	private int userAge;
	private double latitude;
	private double longitude;

	public User(String userName, int userAge, double latitude, double longitude) {
		super();
		this.userName = userName;
		this.userAge = userAge;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String toString() 
	{
		return "Name: " + userName + "; Age: " + userAge + "; Last location: " + longitude + ", " + latitude;
	}
	
	@Override
	public Object getProperty(int arg0) {
        switch(arg0)
        {
        case 0:
            return userName;
        case 1:
            return userAge;
        case 2:
            return longitude;
        case 3:
        	return latitude;
        }
        
        return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)
        {
        case 0:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "userName";
            break;
        case 1:
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "userAge";
            break;
        case 2:
            info.type = Double.class;
            info.name = "longitude";
            break;
        case 3:
            info.type = Double.class;
            info.name = "latitude";
            break;  
        default:break;
        }
	}

	@Override
	public void setProperty(int index, Object value) {
        switch(index)
        {
        case 0:
        	userName = value.toString();
            break;
        case 1:
        	userAge = Integer.parseInt(value.toString());
            break;
        case 2:
        	longitude = (Double) value;
            break;
        case 3:
        	latitude = (Double) value;
        	break;
        default:
            break;
        }
	}


}
