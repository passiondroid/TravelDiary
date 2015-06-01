package com.travel.app.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.app.model.Place;

public class PlaceDBDataSource extends DataSource<Place>{
	
public static final String TABLE_PLACES_TABLE = "PLACES";

	public static final String COLUMN_PLACE = "PLACE";
	public static final String COLUMN_CITY = "CITY";
	public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

	public PlaceDBDataSource(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public boolean insert(Place entity) {
		if (entity == null) {
			return false;
		}
		long result = mDatabase.insert(TABLE_PLACES_TABLE, null,generateContentValuesFromObject(entity));
		return result != -1;
	}

	@Override
	public boolean delete(Place entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.delete(TABLE_PLACES_TABLE,COLUMN_PLACE + " = '" + entity.getPlaceName()+"'", null);
		return result != 0;
	}

	@Override
	public boolean update(Place entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_PLACES_TABLE,generateContentValuesFromObject(entity), COLUMN_PLACE + " = '"
						+ entity.getCityName()+"'", null);
		return result != 0;
	}
	
	public boolean updatePlace(Place entity,String oldPlace) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_PLACES_TABLE,generateContentValuesFromObject(entity), COLUMN_PLACE + " = '"
						+ oldPlace+"'", null);
		return result != 0;
	}

	@Override
	public List<Place> read() {
		Cursor cursor = mDatabase.query(TABLE_PLACES_TABLE, getAllColumns(), null,
				null, null, null, null);
		List placeList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				placeList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return placeList;
	}

	@Override
	public List<Place> read(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_PLACES_TABLE, getAllColumns(), selection,
				selectionArgs, groupBy, having, orderBy);
		List placeList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				placeList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return placeList;
	}
	
	public List<String> readAllPlaceNames() {
		Cursor cursor = mDatabase.query(TABLE_PLACES_TABLE,new String[] {COLUMN_PLACE}, null,null, null, null, null);
		ArrayList<String> placeList = new ArrayList<String>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				placeList.add(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE)).trim());
				cursor.moveToNext();
			}
			cursor.close();
		}
		return placeList;
	}

	public String[] getAllColumns() {
		return new String[] {COLUMN_PLACE,COLUMN_CITY,COLUMN_DESCRIPTION};
	}

	public Place generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		Place place = new Place();
		place.setPlaceName(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE)));
		place.setCityName(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
		place.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
		return place;
	}

	public ContentValues generateContentValuesFromObject(Place entity) {
		if (entity == null) {
			return null;
		}
		ContentValues values = new ContentValues();
		values.put(COLUMN_PLACE, entity.getPlaceName());
		values.put(COLUMN_CITY, entity.getCityName());
		values.put(COLUMN_DESCRIPTION, entity.getDescription());
		return values;
	}

	@Override
	public List<Place> readSpecificColumns(String tableName,
			String[] selectColums, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}
}
