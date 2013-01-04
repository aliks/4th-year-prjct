package com.android.alex.groupmanagement;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.alex.groupmanagement.adapters.GroupsListAdapter;

public class MainActivity extends ListActivity 
{
    private Button createGrpButton;
    private Button search; 
    private GroupsListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setUpView();
		
		adapter = new GroupsListAdapter();
		
		//new SoapService().createGroupServ("home"); +
		//new SoapService().regUser("NewUser", 20, 20.0, 20.0);
		//new SoapService().getAllGroups(); +
		//new SoapService().putUserToGroup("NewUser", 20, "home");
		//new SoapService().removeUserFromGroup("NewUser", 20, "home");
		//new SoapService().deleteGroup("home");
		//new SoapService().findGroup("geeks"); -
		//new SoapService().listAllUsers("geeks"); -
		//new SoapService().finduser("Alex"); - 
		//new SoapService().update(10.0, 10.0, "NewUser", 20);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.reload();
		setList(adapter);
	}
	
	private void setList(GroupsListAdapter myAdaper) {
		String[] groupArr = myAdaper.getResponseList();
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupArr));
	}

	private void setUpView() 
	{
		createGrpButton = (Button)findViewById(R.id.create_gr_b);
		search = (Button)findViewById(R.id.search_gr_b);
		
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
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) 
	{
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
