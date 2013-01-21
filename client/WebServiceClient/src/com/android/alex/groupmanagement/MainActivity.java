package com.android.alex.groupmanagement;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.alex.groupmanagement.adapters.GroupsListAdapter;

public class MainActivity extends ListActivity 
{
    private Button createGrpButton;
    private Button search; 
    private Button viewMyGrp;
    private GroupsListAdapter adapter;
    private PopupWindow pw;
    private Button subscribe; 
    private Button cancel;
    private TextView txt;
    private Long userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle b = getIntent().getExtras(); // Getting the Bundle object that pass from another activity
		userId = b.getLong("userID");
		setUpView();
		
		adapter = new GroupsListAdapter();
		
		//new SoapService().createGroupServ("home"); +
		//new SoapService().regUser("NewUser", 20, 20.0, 20.0); +
		//new SoapService().getAllGroups(); +
		//new SoapService().putUserToGroup("NewUser", 20, "home"); +
		//new SoapService().removeUserFromGroup("NewUser", 20, "home"); +
		//new SoapService().deleteGroup("home"); -
		//new SoapService().findGroup("geeks"); +
		//new SoapService().listAllUsers("geeks"); +
		//new SoapService().finduser("Alex"); + 
		//new SoapService().update(10.0, 10.0, "NewUser", 20); -
		// get location +
		// get age -
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.reload();
		setList(adapter);
	}
	
	private void setList(GroupsListAdapter myAdaper) {
		String[] groupArr = myAdaper.getResponseList();
		setListAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1,
				android.R.id.text1, 
				groupArr));
	}

	private void setUpView() 
	{
		createGrpButton = (Button)findViewById(R.id.create_gr_b);
		search = (Button) findViewById(R.id.search_gr_b);
		viewMyGrp = (Button) findViewById(R.id.view_mygrs_b);
		
		createGrpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, CreateGrpActivity.class));
			} 
		});
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, SearchActivity.class));
			} 
		});
		viewMyGrp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle dataBundle = new Bundle();
				dataBundle.putLong("userID", userId);
				Intent i = new Intent(MainActivity.this, MyGrpActivity.class);
				i.putExtras(dataBundle);
				startActivity(i);
			} 
		});
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
	        LayoutInflater inflater = (LayoutInflater) MainActivity.this
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.group_details,
	                (ViewGroup) findViewById(R.id.popup_element));
	        // create a 300px width and 470px height PopupWindow
	        pw = new PopupWindow(layout, 300, 470, true);
	        // display the popup in the center
	        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
	 
	        txt = (TextView) layout.findViewById(R.id.group_details_txt);
	        subscribe = (Button) layout.findViewById(R.id.subscribe_b);
	        cancel = (Button) layout.findViewById(R.id.cancel_b);
	        final String selectedGroupName = adapter.getResponseList()[position];
	        
	        // set name of the selected group
	        txt.setText("Group name: " + selectedGroupName);

	        subscribe.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// PUT USERS NAME AND AGE HERE
					adapter.subScribeUser(selectedGroupName, userId);
					onResume();
					pw.dismiss();
				}
	        });
	        cancel.setOnClickListener(cancel_button_click_listener);
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
	    public void onClick(View v) {
	        pw.dismiss();
	    }
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
