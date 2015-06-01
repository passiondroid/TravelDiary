package com.travel.app.activity;

import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.travel.app.adapter.CityAdapter;
import com.travel.app.datasource.CityDBDataSource;
import com.travel.app.db.DBHelper;
import com.travel.app.loader.CityDataLoader;
import com.travel.app.model.City;

public class SecondScreeen extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<City>>{
	
	private static final int LOADER_ID = 1;
	private SQLiteDatabase mDatabase;
	private CityDBDataSource mDataSource;
	private DBHelper mDbHelper;
	//private static final int REQUEST_CODE = 55;
	private ListView listView;
	private CityAdapter adapter;
	private TextView welcomeText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_screen_layout);
		ParseUser currentUser = ParseUser.getCurrentUser();
		// Convert currentUser into String
		String struser = currentUser.getString("name");
		welcomeText=(TextView) findViewById(R.id.welcom_textView);
		welcomeText.setText("Welcome \n"+struser+"!");
		
		mDbHelper = DBHelper.getInstance(this);
		mDatabase = mDbHelper.getWritableDatabase();
		mDataSource = new CityDBDataSource(mDatabase);
		
		listView=(ListView) findViewById(R.id.city_listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,long arg3) {
				Intent intent = new Intent(SecondScreeen.this,FourthScreen.class);
				City city = (City) adapter.getItemAtPosition(position);
				intent.putExtra("City", city.getCityName());
				Toast.makeText(SecondScreeen.this,city.getCityName(), Toast.LENGTH_SHORT).show();
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
		Intent intent=new Intent(SecondScreeen.this, ThirdScreen.class);
		startActivity(intent);
	}

	@Override
	public Loader<List<City>> onCreateLoader(int arg0, Bundle arg1) {
		CityDataLoader  loader = new CityDataLoader(getApplicationContext(), mDataSource, null, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<City>> arg0, List<City> cityList) {
		//if(null!=cityList && cityList.size()>0){
			listView=(ListView) findViewById(R.id.city_listView);
			listView.setEmptyView(findViewById(R.id.empty_textView));
			adapter=new CityAdapter(this,true,cityList);
			listView.setAdapter(adapter);
		/*}else{
			listView=(ListView) findViewById(R.id.city_listView);
			listView.setEmptyView(findViewById(R.id.empty_textView));
			Toast.makeText(this, "List Empty", Toast.LENGTH_SHORT).show();
		}*/
	}

	@Override
	public void onLoaderReset(Loader<List<City>> arg0) {
		// TODO Auto-generated method stub
		
	}

}
