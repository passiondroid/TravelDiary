package com.travel.app.activity;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.travel.app.adapter.CityAdapter;
import com.travel.app.datasource.CityDBDataSource;
import com.travel.app.db.DBHelper;
import com.travel.app.loader.CityDataLoader;
import com.travel.app.model.City;
import com.travel.app.util.ValidationUtil;

public class ThirdScreen extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<City>>{
	
	private ListView listView;
	private CityAdapter adapter;
	private static final int LOADER_ID = 2;
	private SQLiteDatabase mDatabase;
	private CityDBDataSource mDataSource;
	private DBHelper mDbHelper;
	private TextView welcomeText;
	private List<City> cities;
	private EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third_screen_layout);
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		// Convert currentUser into String
		String struser = currentUser.getString("name");
		welcomeText=(TextView) findViewById(R.id.welcome_textView);
		welcomeText.setText("Welcome \n"+struser+"!");
		
		mDbHelper = DBHelper.getInstance(this);
		mDatabase = mDbHelper.getWritableDatabase();
		mDataSource = new CityDBDataSource(mDatabase);
		
		editText = (EditText)findViewById(R.id.editText1);
		
		//cities = new ArrayList<City>();
		//adapter=new CityAdapter(this,false,cities);
		//listView.setEmptyView(findViewById(R.id.empty_textView));
		//listView.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getSupportLoaderManager().initLoader(LOADER_ID, null, this);
	}
	
	public void onClick(View view) {
		if(view.getId()==R.id.okBtn){
			if(isCityValid()){
				editText.setEnabled(false);
				City city = new City();
				city.setCityName(editText.getText().toString());
				Log.i("Travel","City Name from edittext : "+city.getCityName());
				mDataSource.insert(city);
				getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
			}
		}else if(view.getId()==R.id.back_button){
			this.finish();
		}
	}
	
	
	@Override
	public Loader<List<City>> onCreateLoader(int arg0, Bundle arg1) {
		CityDataLoader  loader = new CityDataLoader(getApplicationContext(), mDataSource, null, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<City>> arg0, List<City> cityList) {
		//if(null!=cityList && cityList.size()>0){
			cities = cityList;
			listView=(ListView)findViewById(R.id.city_listView);
			//listView.setEmptyView(findViewById(R.id.empty_textView));
			adapter=new CityAdapter(this,false,cityList);
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			editText.setEnabled(true);
			editText.setText("");
		/*}else{
			listView=(ListView) findViewById(R.id.city_listView);
			listView.setEmptyView(findViewById(R.id.empty_textView));
		}*/
	}

	@Override
	public void onLoaderReset(Loader<List<City>> arg0) {
		// TODO Auto-generated method stub
	}
	
	private boolean isCityValid() {
		boolean isNameValid = ValidationUtil.checkValidCity(this, editText,cities);
		return isNameValid;
	}
	
	public void onDeleteClick(View view){
		String cityName = (String)view.getTag();
		City city = new City();
		city.setCityName(cityName);
		mDataSource.delete(city);
		getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
	}
	
}
