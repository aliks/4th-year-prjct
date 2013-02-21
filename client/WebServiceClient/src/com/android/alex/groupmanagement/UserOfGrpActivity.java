package com.android.alex.groupmanagement;

import com.android.alex.services.SoapService;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

public class UserOfGrpActivity extends ListActivity 
{
	private SoapService ss;
	private PopupWindow pw;
	private Button location;
	private Button details;
	private Button cancel;
	private String responseString;
	private String selectedProperty;
	private String[] requestResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_in_grp);
		ss = new SoapService();
		Bundle b = getIntent().getExtras(); // Getting the Bundle object that pass from another activity
		selectedProperty = b.getString("SelectedProperty");

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		responseString = ss.listAllUsers(selectedProperty);
		parse(responseString);
		setList(requestResult);
	}
	
	private void setList(String[] arr) 
	{
		if(arr != null)
			setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr));
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) 
	{
		String selectedUser = requestResult[position];
		Bundle dataBundle = new Bundle();
		boolean rez = checkUserList(selectedUser, requestResult);
		
		dataBundle.putStringArray("SelectedProperty", 
				new String[]{rez ? "Multiple" : "Single", selectedProperty, selectedUser});
		Intent moreDetailsIntent = new Intent(UserOfGrpActivity.this, UserDetails.class);
		moreDetailsIntent.putExtras(dataBundle);
		startActivity(moreDetailsIntent);
	}
	
	private boolean checkUserList(String user, String[] users) 
	{
		int appears = 0;
		for (int i=0; i < users.length; i++)
			if (users[i].equals(user))
				appears++;
		return (appears > 1) ? true : false;
	}
	
	private void initiatePopupWindow(int position) 
	{
	    try {
	        //We need to get the instance of the LayoutInflater, use the context of this activity
	        LayoutInflater inflater = (LayoutInflater) UserOfGrpActivity.this
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.group_option_layout,
	                (ViewGroup) findViewById(R.id.popup_element));
	        // create a 300px width and 470px height PopupWindow
	        pw = new PopupWindow(layout, 300, 470, true);
	        // display the popup in the center
	        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
	 
			location = (Button) layout.findViewById(R.id.unsubscribe_b);
			details = (Button) layout.findViewById(R.id.view_users_b);
			cancel = (Button) layout.findViewById(R.id.cancel_b2);
			//viewGroup = (Button) layout.findViewById(R.id.view_grp_b);
			//final String selectedGroupName = searchResult[position];
	        
	        // set name of the selected group
	        //txt.setText("Group name: " + searchResult[position]);
			
	        cancel.setOnClickListener(cancel_button_click_listener);
	        location.setOnClickListener(new OnClickListener() {
	    	    public void onClick(View v) {
	    	    	 
	    	    	//onResume();
	    	        pw.dismiss();
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
	private void parse(String str) {
		if(str != null)
			requestResult = str.replace("[", "").replace("]", "").split("\\,\\s");
	}
	
}
