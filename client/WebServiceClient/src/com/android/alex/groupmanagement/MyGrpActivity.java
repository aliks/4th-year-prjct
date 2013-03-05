package com.android.alex.groupmanagement;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.alex.groupmanagement.ui.NearestFriendActivity;
import com.android.alex.services.SoapService;
import com.android.alex.services.utilities.UtilityClass;

public class MyGrpActivity extends ListActivity 
{
	private Button unsubscribe;
	private Button viewUser;
	private Button cancel;
	private Button nf; // nearest friend
	private Button update_location; // update location
    private PopupWindow pw;
	private String[] searchResult = null;
	private SoapService ss;
	private long userId;
	// GPS variables
	private Double[] gps;
	private LocationManager lm = null;
	private List<String> providers = null;
	private Location l = null;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_grp_view);
		ss = new SoapService();
		Bundle b = getIntent().getExtras(); // Getting the Bundle object that pass from another activity
		userId = b.getLong("userID");
		
		update_location = (Button) findViewById(R.id.update_location_b);
		update_location.setOnClickListener(location_listener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// HERE PUT USERS NAME AND AGE
		String responseString = ss.getMyGroups(userId);
		parse(responseString);
		setList(searchResult);
	}

	private void setList(String[] arr) 
	{
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr));
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) 
	{
		Log.v("position >> ", Integer.toString(position));
		initiatePopupWindow(position);
	}
	
	private void initiatePopupWindow(int position) 
	{
	    try {
	        //We need to get the instance of the LayoutInflater, use the context of this activity
	        LayoutInflater inflater = (LayoutInflater) MyGrpActivity.this
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.group_option_layout,
	                (ViewGroup) findViewById(R.id.popup_element));
	        // create a 300px width and 470px height PopupWindow
	        pw = new PopupWindow(layout, 300, 470, true);
	        // display the popup in the center
	        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
	 
			unsubscribe = (Button) layout.findViewById(R.id.unsubscribe_b);
			viewUser = (Button) layout.findViewById(R.id.view_users_b);
			cancel = (Button) layout.findViewById(R.id.cancel_b2);
			nf = (Button) layout.findViewById(R.id.find_mate);
			//viewGroup = (Button) layout.findViewById(R.id.view_grp_b);
			final String selectedGroupName = searchResult[position];
	        
	        // set name of the selected group
	        //txt.setText("Group name: " + searchResult[position]);
			
			nf.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//
					Bundle dataBundle = new Bundle();
					dataBundle.putString("SelectedProperty", selectedGroupName);
					Intent nearFriendAct = new Intent(MyGrpActivity.this, NearestFriendActivity.class);
					nearFriendAct.putExtras(dataBundle);
					pw.dismiss();
					startActivity(nearFriendAct);
				}
			});
			
	        cancel.setOnClickListener(cancel_button_click_listener);
	        unsubscribe.setOnClickListener(new OnClickListener() {
	    	    public void onClick(View v) {
	    	    	// 
	    	    	ss.removeUserFromGroup(selectedGroupName, userId);
	    	    	onResume();
	    	        pw.dismiss();
	    	    }
	    	});
	        viewUser.setOnClickListener(new OnClickListener() {
	    	    public void onClick(View v) {
	    	    	//
	    	    	Bundle dataBundle = new Bundle();
	    	    	dataBundle.putString("SelectedProperty", selectedGroupName);
	    	    	Intent userDetails = new Intent(MyGrpActivity.this, UserOfGrpActivity.class);
	    	    	userDetails.putExtras(dataBundle);
	    	    	pw.dismiss();
	    	    	startActivity(userDetails);
	    	    }
	    	});
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
	    public void onClick(View v) {
	        pw.dismiss();
	    }
	};
	private OnClickListener location_listener = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			Double[] current_location = getLocation();
			if (current_location[0] != null) {
				ss.update(current_location[0], current_location[1], userId);
				Toast.makeText(MyGrpActivity.this,"Location updated!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MyGrpActivity.this,"Something went wrong, can't update location", 
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	
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
	
	private void parse(String str) {
		if(str != null)
			searchResult = UtilityClass.parseString(str);
	}
}
