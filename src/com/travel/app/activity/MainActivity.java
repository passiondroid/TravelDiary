package com.travel.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.travel.app.util.ValidationUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        System.out.println("MainActivity.onCreate()"+ValidationUtil.isValidName(this, "hsfbhj"));
    }


    private void checkAllFieldsAreValid() {
    	EditText nameText=(EditText) findViewById(R.id.name_editText);
    	EditText emailIdText=(EditText) findViewById(R.id.email_editText);
    	EditText passwordText=(EditText) findViewById(R.id.password_editText);
    	EditText telephoneNoText=(EditText) findViewById(R.id.telephone_editText);
    	
    	ValidationUtil.checkValidName(this, nameText);
    	ValidationUtil.checkValidEmailId(this, emailIdText);
    	ValidationUtil.checkValidPasword(this, passwordText);
    	ValidationUtil.checkValidPhoneNumber(this, telephoneNoText);
    	
	}
    
    public void onOkBtnClick(View view) {
		checkAllFieldsAreValid();
	}
   
    
}
