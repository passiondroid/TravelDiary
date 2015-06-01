package com.travel.app.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;
import com.travel.app.db.DBHelper;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, create the db and send the user to LoginSignupActivity.class
        	DBTask dbTask = new DBTask(this);
        	dbTask.execute();
            Intent intent = new Intent(MainActivity.this,LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
            	Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SecondScreeen.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(MainActivity.this,LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
        
        
	private class DBTask extends AsyncTask<Void, Void, Void> {
		DBHelper dbHelper;
		public DBTask(Context c) {
			dbHelper = DBHelper.getInstance(c);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				boolean isDBCreated = dbHelper.createdatabase();
				Log.i("Travel","isDBCreated : "+isDBCreated);
			} catch (IOException e) {
				Log.e("Travel","Exception",e);
			} 
			return null;
		}
	}
}
