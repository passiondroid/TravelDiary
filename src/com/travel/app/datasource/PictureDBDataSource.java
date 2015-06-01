package com.travel.app.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.travel.app.model.Picture;

public class PictureDBDataSource extends DataSource<Picture>{

	public static final String TABLE_PICTURES_TABLE = "PICTURES";

	public static final String COLUMN_TITLE = "TITLE";
	public static final String COLUMN_IMAGE_PATH = "IMAGE_PATH";
	public static final String COLUMN_DATE_ADDED = "DATE_ADDED";
	public static final String COLUMN_PLACE = "PLACE";


	public PictureDBDataSource(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public boolean insert(Picture entity) {
		if (entity == null) {
			return false;
		}
		long result = mDatabase.insert(TABLE_PICTURES_TABLE, null,
				generateContentValuesFromObject(entity));
		return result != -1;
	}

	@Override
	public boolean delete(Picture entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.delete(TABLE_PICTURES_TABLE,COLUMN_IMAGE_PATH + " = '" + entity.getImagePath()+"'", null);
		return result != 0;
	}

	@Override
	public boolean update(Picture entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_PICTURES_TABLE,
				generateContentValuesFromObject(entity), COLUMN_IMAGE_PATH + " = "
						+ entity.getImagePath(), null);
		return result != 0;
	}

	@Override
	public List<Picture> read() {
		Cursor cursor = mDatabase.query(TABLE_PICTURES_TABLE, getAllColumns(), null,
				null, null, null, null);
		List picturesList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				picturesList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return picturesList;
	}

	@Override
	public List<Picture> read(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_PICTURES_TABLE, getAllColumns(), selection,
				selectionArgs, groupBy, having, orderBy);
		List picturesList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				picturesList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return picturesList;
	}

	public String[] getAllColumns() {
		return new String[] {COLUMN_TITLE,COLUMN_PLACE,COLUMN_IMAGE_PATH,COLUMN_DATE_ADDED};
	}

	public Picture generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		Picture picture = new Picture();
		picture.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
		picture.setImagePath(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH)));
		picture.setPlace(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE)));
		picture.setDateAdded(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_ADDED)));
		return picture;
	}

	public ContentValues generateContentValuesFromObject(Picture entity) {
		if (entity == null) {
			return null;
		}
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, entity.getTitle());
		values.put(COLUMN_IMAGE_PATH, entity.getImagePath());
		values.put(COLUMN_PLACE, entity.getPlace());
		//values.put(COLUMN_DATE_ADDED, entity.getDateAdded());
		return values;
	}

	@Override
	public List<Picture> readSpecificColumns(String tableName,
			String[] selectColums, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		return null;
	}
}
