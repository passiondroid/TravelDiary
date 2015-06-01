package com.travel.app.activity;

import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.travel.app.adapter.PlaceAdapter;
import com.travel.app.datasource.PlaceDBDataSource;
import com.travel.app.db.DBHelper;
import com.travel.app.loader.PlaceDataLoader;
import com.travel.app.model.Place;

public class FourthScreen extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Place>>{
	
	private static final int LOADER_ID = 3;
	private SQLiteDatabase mDatabase;
	private PlaceDBDataSource mDataSource;
	private DBHelper mDbHelper;
	private ListView listView;
	private PlaceAdapter adapter;
	private TextView welcomeText;
	private String city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_screen_layout);
		Intent intent = getIntent();
		city = intent.getStringExtra("City");
		Log.i("Travel","City : "+city);
		TextView textview = (TextView)findViewById(R.id.textView3);
		textview.setText(city);
		ParseUser currentUser = ParseUser.getCurrentUser();
		// Convert currentUser into String
		String struser = currentUser.getString("name");
		welcomeText=(TextView) findViewById(R.id.welcom_textView);
		welcomeText.setText("Welcome \n"+struser);
		
		mDbHelper = DBHelper.getInstance(this);
		mDatabase = mDbHelper.getWritableDatabase();
		mDataSource = new PlaceDBDataSource(mDatabase);
		
		listView=(ListView) findViewById(R.id.city_listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,long arg3) {
				Intent intent = new Intent(FourthScreen.this,FifthScreen.class);
				Place place = (Place) adapter.getItemAtPosition(position);
				intent.putExtra("Place", place.getPlaceName());
				intent.putExtra("City", city);
				intent.putExtra("Desc", place.getDescription());
				intent.putExtra("Mode", "Edit");
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getSupportLoaderManager().initLoader(LOADER_ID, null, this);
	}
	
	public void onAddBtnClick(View view) {
		Intent intent=new Intent(FourthScreen.this, FifthScreen.class);
		intent.putExtra("City", city);
		intent.putExtra("Mode", "Add");
		startActivity(intent);
	}

	@Override
	public Loader<List<Place>> onCreateLoader(int arg0, Bundle arg1) {
		PlaceDataLoader  loader = new PlaceDataLoader(getApplicationContext(), mDataSource, "CITY = ?", new String[]{city}, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<Place>> arg0, List<Place> placeList) {
			listView=(ListView) findViewById(R.id.city_listView);
			listView.setEmptyView(findViewById(R.id.empty_textView));
			adapter=new PlaceAdapter(this,placeList);
			listView.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<List<Place>> arg0) {
		// TODO Auto-generated method stub
		
	}
}
