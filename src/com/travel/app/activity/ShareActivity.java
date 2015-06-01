package com.travel.app.activity;

import com.travel.app.service.UploadService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ShareActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_share_layout);
		Intent i = new Intent(this,UploadService.class);
		i.putExtra("PLACE",getIntent().getStringExtra("PLACE"));
		startService(i);
	}
}
