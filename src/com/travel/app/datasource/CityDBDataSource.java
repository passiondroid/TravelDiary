package com.travel.app.datasource;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.app.model.City;

public class CityDBDataSource extends DataSource<City>{
	
public static final String TABLE_CITIES_TABLE = "CITIES";
	
	public static final String COLUMN_CITY = "CITY";

	public CityDBDataSource(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public boolean insert(City entity) {
		if (entity == null) {
			return false;
		}
		long result = mDatabase.insert(TABLE_CITIES_TABLE, null,
				generateContentValuesFromObject(entity));
		return result != -1;
	}

	@Override
	public boolean delete(City entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.delete(TABLE_CITIES_TABLE,
				COLUMN_CITY + " = '" + entity.getCityName()+"'", null);
		return result != 0;
	}

	@Override
	public boolean update(City entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_CITIES_TABLE,
				generateContentValuesFromObject(entity), COLUMN_CITY + " = "
						+ entity.getCityName(), null);
		return result != 0;
	}

	@Override
	public List<City> read() {
		Cursor cursor = mDatabase.query(TABLE_CITIES_TABLE, getAllColumns(), null,
				null, null, null, null);
		List cityList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				cityList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return cityList;
	}

	@Override
	public List<City> read(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_CITIES_TABLE, getAllColumns(), selection,
				selectionArgs, groupBy, having, orderBy);
		List cityList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				cityList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return cityList;
	}

	public String[] getAllColumns() {
		return new String[] {COLUMN_CITY};
	}

	public City generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		City city = new City();
		city.setCityName(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
		return city;
	}

	public ContentValues generateContentValuesFromObject(City entity) {
		if (entity == null) {
			return null;
		}
		ContentValues values = new ContentValues();
		values.put(COLUMN_CITY, entity.getCityName());
		return values;
	}

	@Override
	public List<City> readSpecificColumns(String tableName,
			String[] selectColums, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}
}
