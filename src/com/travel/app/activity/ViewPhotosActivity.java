package com.travel.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.travel.app.fragment.ImagePagerFragment;

public class ViewPhotosActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//setContentView(R.layout.view_photos_layout);
		
		String tag = ImagePagerFragment.class.getSimpleName();
		Fragment fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImagePagerFragment();
			fr.setArguments(getIntent().getExtras());
		}
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
		
	}

}
