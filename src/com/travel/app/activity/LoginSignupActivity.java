package com.travel.app.activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.travel.app.util.ValidationUtil;

public class LoginSignupActivity extends Activity {
	// Declare Variables
	Button loginbutton;
	Button okBtn;
	String nametxt;
	String emailtxt;
	String passwordtxt;
	String telephonetxt;
	EditText password;
	EditText name;
	EditText email;
	EditText telephone;
	TextView error_text;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from main.xml
		setContentView(R.layout.activity_main);

		 // Locate EditTexts in main.xml
        name = (EditText) findViewById(R.id.name_editText);
        password = (EditText) findViewById(R.id.password_editText);
        email = (EditText) findViewById(R.id.email_editText);
        telephone = (EditText) findViewById(R.id.telephone_editText);
        error_text = (TextView)findViewById(R.id.error_text);

		// Locate Buttons in main.xml
        okBtn = (Button) findViewById(R.id.okBtn);

		// Login Button Click Listener
		/*loginbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if(checkAllFieldsAreValid()){
				// Retrieve the text entered from the EditText
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();

				// Send data to Parse.com for verification
				ParseUser.logInInBackground(usernametxt, passwordtxt,new LogInCallback() {
					public void done(ParseUser user, ParseException e) {
						if (user != null) {
							// If user exist and authenticated, send user to Welcome.class
							Intent intent = new Intent(LoginSignupActivity.this, SecondScreeen.class);
							startActivity(intent);
							Toast.makeText(getApplicationContext(),"Successfully Logged in",Toast.LENGTH_LONG).show();
							finish();
						} else {
							Log.e("Travel","Exception",e);
							Toast.makeText(getApplicationContext(),"No such user exist, please signup",Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		}});*/
		// Sign up Button Click Listener
		okBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if(checkAllFieldsAreValid()){
				// Retrieve the text entered from the EditText
				emailtxt = email.getText().toString();
				passwordtxt = password.getText().toString();
				nametxt  = name.getText().toString();
				telephonetxt = telephone.getText().toString();

					// Save new user data into Parse.com Data Storage
					ParseUser user = new ParseUser();
					user.put("name", nametxt);
					user.put("telephone", telephonetxt);
					user.setUsername(emailtxt);
					user.setPassword(passwordtxt);
					user.setEmail(emailtxt);
					
					// Set up a progress dialog
			        final ProgressDialog dlg = new ProgressDialog(LoginSignupActivity.this);
			        dlg.setTitle("Please wait.");
			        dlg.setMessage("Signing up...");
			        dlg.show();
			        
					user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
							if (e == null) {
								// Show a simple Toast message upon successful registration
								dlg.dismiss();
								Toast.makeText(getApplicationContext(),"Successfully Signed up.",Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(LoginSignupActivity.this, SecondScreeen.class);
				                startActivity(intent);
				                finish();
							} else {
								switch(e.getCode()){
								case ParseException.USERNAME_TAKEN:
									error_text.setText("Sorry, this email id already exists.");
								  break;
								case ParseException.USERNAME_MISSING:
									error_text.setText("Sorry, you must supply an email id to register.");
									
								  break;
								case ParseException.PASSWORD_MISSING:
									error_text.setText("Sorry, you must supply a password to register.");
								  break;
								case ParseException.EMAIL_TAKEN:
									error_text.setText("Sorry, this email id already exists.");
								  break;
								case ParseException.EMAIL_MISSING:
									error_text.setText("Sorry, you must supply an email id to register.");
								  break;
								default:
									error_text.setText("Something went wrong while registering you. Please try again.");
								}
								dlg.dismiss();
								error_text.setVisibility(View.VISIBLE);
								Log.e("Travel","Exception",e);
								//Toast.makeText(getApplicationContext(),"Sign up Error", Toast.LENGTH_SHORT).show();
							}
						}
					});
			}
		}});

	}

	private boolean checkAllFieldsAreValid() {
		if(isOnline()){
			EditText nameText=(EditText) findViewById(R.id.name_editText);
			EditText emailIdText=(EditText) findViewById(R.id.email_editText);
			EditText passwordText=(EditText) findViewById(R.id.password_editText);
			EditText telephoneNoText=(EditText) findViewById(R.id.telephone_editText);

			boolean isNameValid = ValidationUtil.checkValidName(this, nameText);
			boolean isEmailValid = ValidationUtil.checkValidEmailId(this, emailIdText);
			boolean isPasswordValid = ValidationUtil.checkValidPasword(this, passwordText);
			boolean isPhoneValid = ValidationUtil.checkValidPhoneNumber(this, telephoneNoText);

			if(isNameValid && isEmailValid && isPasswordValid && isPhoneValid){
				return true;
			}else{
				return false;
			}
		}else{
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setMessage(R.string.no_internet)
		               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   dialog.dismiss();
		                   }
		               });
		        builder.create().show();
			return false;
		}
	}
	
	public boolean isOnline() {
		ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
}