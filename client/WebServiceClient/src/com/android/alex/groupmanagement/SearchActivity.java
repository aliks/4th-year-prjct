package com.android.alex.groupmanagement;

import com.android.alex.services.SoapService;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/** 
 * TO-DO
 * 
 * @author alex
 * 
 * get rid of user names in findGroup web-service method
 *
 */
public class SearchActivity extends ListActivity 
{
	private EditText groupNameField;
	private EditText userNameField;
	private EditText usersByGroupField;
	private Button submit;
	private SoapService ss;
	private String resultString = "";
	private String[] searchResult;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_form);
		ss = new SoapService();
		searchResult = null;
		
		setUpView();
	}

	private void setUpView() {
		groupNameField = (EditText) findViewById(R.id.srch_grp_name_ed);
		userNameField = (EditText) findViewById(R.id.srch_user_name_ed);
		usersByGroupField = (EditText) findViewById(R.id.srch_grp_by_user_name_ed);
		submit = (Button) findViewById(R.id.submit_search_form_b);
		
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!groupNameField.getText().toString().equals("")) {
					resultString= ss.findGroup(groupNameField.getText().toString());		// find group
					Log.v(">>>>>>>", resultString);

				} else if(!userNameField.getText().toString().equals("")) {
					resultString = ss.finduser(userNameField.getText().toString());			// find users by name
					
				} else if(!usersByGroupField.getText().toString().equals("")) {
					resultString = ss.listAllUsers(usersByGroupField.getText().toString());	// find users of the group
					
				} else if(groupNameField.getText().toString().equals("")) {
						Log.v(">> ", "NO INPUT");
					
				} else if(userNameField.getText().toString().equals("")) {
					Log.v(">> ", "NO INPUT");

				} else if(usersByGroupField.getText().toString().equals("")) {
					Log.v(">> ", "NO INPUT");
				}
				parse(resultString);
				setList(searchResult);
			}
		});
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) 
	{
		
	}
	
	private void setList(String[] arr) {
		if(arr != null)
			setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr));
	}
	
	private void parse(String str) {
		searchResult = resultString.replace("[", "").replace("]", "").split("\\,\\s");
	}
}





