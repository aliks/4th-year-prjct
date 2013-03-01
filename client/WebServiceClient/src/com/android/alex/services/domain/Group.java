package com.android.alex.services.domain;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Group implements KvmSerializable {
	private long groupId;
	private String groupName;
	private List<User> users = null;

	public Group() {
	}

	public Group(String groupName) {
		super();
		this.groupName = groupName;
		this.users = new ArrayList<User>();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String toString() {
		return "Group name: " + groupName;
	}

	@Override
	public Object getProperty(int arg0) {
        switch(arg0)
        {
        case 0:
            return groupId;
        case 1:
            return groupName;
        case 2:
            return users;
        }
        
        return null;
	}

	@Override
	public int getPropertyCount() {
		return 3;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)
        {
        case 0:
            info.type = PropertyInfo.LONG_CLASS;
            info.name = "groupId";
            break;
        case 1:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "groupName";
            break;
        case 2:
            info.type = PropertyInfo.VECTOR_CLASS;
            info.name = "users";
            break;
        default:break;
        }
	}

	@Override
	public void setProperty(int index, Object value) {
        switch(index)
        {
        case 0:
        	groupId = Integer.parseInt(value.toString());
            break;
        case 1:
        	groupName = value.toString();
            break;
        case 2:
            users = null;
            break;
        default:
            break;
        }
	}



}
