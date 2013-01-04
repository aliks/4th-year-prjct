package com.android.alex.groupmanagement.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.alex.services.SoapService;

public class GroupsListAdapter
{
	private String responseString = null;
	private String[] responseList = null;

	public GroupsListAdapter() 
	{
		responseString = new SoapService().getAllGroups();
		parse(responseString);
	}
	
	public String[] getResponseList() {
		return responseList;
	}

	public void setResponseList(String[] responseList) {
		this.responseList = responseList;
	}

	public void reload() {
		responseString = new SoapService().getAllGroups();
		parse(responseString);
	}
	
	private void parse(String str) {
		responseList = str.replace("[", "").replace("]", "").split("\\,\\s");
	}

}
