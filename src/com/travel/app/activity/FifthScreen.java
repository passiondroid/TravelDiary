package com.travel.app.activity;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;
import com.travel.app.datasource.PlaceDBDataSource;
import com.travel.app.db.DBHelper;
import com.travel.app.model.Place;
import com.travel.app.util.ValidationUtil;

public class FifthScreen extends FragmentActivity{
	private String city;
	private String place;
	private String description;
	private String mode;
	private TextView welcomeText;
	//private static final int LOADER_ID = 4;
	private SQLiteDatabase mDatabase;
	private PlaceDBDataSource mDataSource;
	private DBHelper mDbHelper;
	private EditText placeET,descET;
	private TextView dateTV;
	//private PlaceDataLoader placeDataLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fifth_screen_layout);
		Intent intent = getIntent();
		city = intent.getStringExtra("City");
		place = intent.getStringExtra("Place");
		description = intent.getStringExtra("Desc");
		mode = intent.getStringExtra("Mode");
		Log.i("Travel","City & Place : "+city+" "+place);
		TextView textview = (TextView)findViewById(R.id.textView3);
		textview.setText("Chapter\n"+city);
		ParseUser currentUser = ParseUser.getCurrentUser();
		// Convert currentUser into String
		String struser = currentUser.getString("name");
		welcomeText=(TextView) findViewById(R.id.welcom_textView);
		welcomeText.setText("Welcome \n"+struser);
		placeET = (EditText)findViewById(R.id.editText1);
		descET = (EditText)findViewById(R.id.editText2);
		dateTV = (TextView)findViewById(R.id.dateTV);
		if(null != place){
			placeET.setText(place);
		}

		if(null != description){
			descET.setText(description);
		}
		mDbHelper = DBHelper.getInstance(this);
		mDatabase = mDbHelper.getWritableDatabase();
		mDataSource = new PlaceDBDataSource(mDatabase);
		//placeDataLoader = (PlaceDataLoader)getLoaderManager().getLoader(LOADER_ID);
		//placeDataLoader = new PlaceDataLoader(this, mDataSource, null, null, null, null, null);
	}

	public void onClick(View view){
		if(view.getId()==R.id.save_Btn){
			if(mode.equals("Edit")){
				if(isEntryValid(mode)){
					Place placeObj = new Place();
					placeObj.setCityName(city);
					placeObj.setPlaceName(placeET.getText().toString());
					placeObj.setDescription(descET.getText().toString());
					//Update values
					mDataSource.updatePlace(placeObj,place);
				}
			}else if(mode.equals("Add")){
				if(isEntryValid(mode)){
					Place placeObj = new Place();
					placeObj.setCityName(city);
					placeObj.setPlaceName(placeET.getText().toString());
					placeObj.setDescription(descET.getText().toString());
					//Insert values
					mDataSource.insert(placeObj);
				}
			}
			this.finish();
		}else if(view.getId()==R.id.delete_Btn){
			if(isEntryValid("delete")){
				Place placeObj = new Place();
				placeObj.setCityName(city);
				placeObj.setPlaceName(placeET.getText().toString());
				placeObj.setDescription(descET.getText().toString());
				mDataSource.delete(placeObj);
				this.finish();
			}
		}else if(view.getId()==R.id.cancel_Btn){
			this.finish();
		}else if(view.getId()==R.id.photo_Btn){
			startActivity(new Intent(this,AddPhotoActivity.class).putExtra("Place", place));
		}else if(view.getId()==R.id.calendar_Btn){
			DialogFragment newFragment = new DatePickerFragment();
		    newFragment.show(getSupportFragmentManager(), "datePicker");
		}
	}

	public boolean isEntryValid(String mode){
		boolean validPlace = ValidationUtil.checkValidPlace(this, placeET, mDataSource.readAllPlaceNames(), mode);
		boolean validPlaceDesc = ValidationUtil.checkValidPlaceDescription(this, descET);
		if(validPlace && validPlaceDesc){
			return true;
		}else{
			return false;
		}
	}

	public static class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
		}
	}


}
