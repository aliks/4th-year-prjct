package com.android.alex.groupmanagement.ui;

import android.os.Bundle;
import android.util.Log;

import com.android.alex.groupmanagement.R;
import com.android.alex.services.SoapService;
import com.android.alex.services.domain.DemandSpace;
import com.google.android.maps.MapActivity;

public class NearestFriendActivity extends MapActivity {

	private DemandSpace ds;
	private SoapService ss;
	private String selectedGroupName;
	private String responseString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearest_f_layout);
		ss = new SoapService();
		
		Bundle b = getIntent().getExtras();
		selectedGroupName = b.getString("SelectedProperty");

		// init variables
		
		// make request
		responseString = findNN(selectedGroupName);
	}
	
	// receive a string of the nearest neighbor locations
	private String findNN(String selectedGroupName) {
		// find user location
		Double latid = 55.875259;
		Double longt = -4.291577;
		// get number of members in a group
		int numberOfMembers = Integer.parseInt(ss.numberOfMembersInGroup(selectedGroupName));
		Log.v("Number of member in a group: ", String.valueOf(numberOfMembers));
		// create a demand space
		Double x1 = latid + (1/(numberOfMembers*100));
		Double y1 = longt + (1/(numberOfMembers*100));
		
		Double x2 = latid - (1/(numberOfMembers*100));
		Double y2 = longt - (1/(numberOfMembers*100));
		ds = new DemandSpace(x1,y1, x2,y2);
		// send demand space
		//ss.findNearestFriend(selectedGroupName, ds);
		return null;
	}
	
	// analyzes locations received from the server (LBS) 
	// returns closest neighbor location
	private Double[] analyzeLocations() {
		return null;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
