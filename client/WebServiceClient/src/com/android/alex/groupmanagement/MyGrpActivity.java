package com.android.alex.groupmanagement;

import com.android.alex.services.SoapService;

import android.app.ListActivity;
import android.content.Context;
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
import android.widget.TextView;

public class MyGrpActivity extends ListActivity 
{
	private Button unsubscribe;
	private Button viewUser;
	private Button viewGroup;
	private Button cancel;
    private PopupWindow pw;
	private String[] searchResult = null;
	private SoapService ss;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_grp_view);
		ss = new SoapService();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// HERE PUT USERS NAME AND AGE
		String responseString = ss.getMyGroups("NewUser", 20);
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
			//viewGroup = (Button) layout.findViewById(R.id.view_grp_b);
			final String selectedGroupName = searchResult[position];
	        
	        // set name of the selected group
	        //txt.setText("Group name: " + searchResult[position]);
			
	        cancel.setOnClickListener(cancel_button_click_listener);
	        unsubscribe.setOnClickListener(new OnClickListener() {
	    	    public void onClick(View v) {
	    	    	// 
	    	    	ss.removeUserFromGroup("NewUser", 20, selectedGroupName);
	    	    	onResume();
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
			searchResult = str.replace("[", "").replace("]", "").split("\\,\\s");
	}
}
