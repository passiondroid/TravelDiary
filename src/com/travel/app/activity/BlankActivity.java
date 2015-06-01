package com.travel.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.ParseUser;

public class BlankActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank);
		ParseUser currentUser = ParseUser.getCurrentUser();

		// Convert currentUser into String
		String struser = currentUser.getUsername().toString();

		// Locate TextView in blank.xml
		TextView txtuser = (TextView) findViewById(R.id.textView1);

		// Set the currentUser String into TextView
		txtuser.setText("You are logged in as " + struser);
	}
	

}
