package com.travel.app.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.app.model.Chapter;

public class ChapterDBDataSource extends DataSource<Chapter>{
	
	public static final String TABLE_CHAPTERS_TABLE = "CHAPTERS";
	public static final String COLUMN_CITY = "CITY";
	public static final String COLUMN_PLACE = "PLACE";
	public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

	public ChapterDBDataSource(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public boolean insert(Chapter entity) {
		if (entity == null) {
			return false;
		}
		long result = mDatabase.insert(TABLE_CHAPTERS_TABLE, null,
				generateContentValuesFromObject(entity));
		return result != -1;
	}

	@Override
	public boolean delete(Chapter entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.delete(TABLE_CHAPTERS_TABLE,
				COLUMN_PLACE + " = '" + entity.getPlaceName()+"'", null);
		return result != 0;
	}

	@Override
	public boolean update(Chapter entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_CHAPTERS_TABLE,
				generateContentValuesFromObject(entity), COLUMN_PLACE + " = "
						+ entity.getPlaceName(), null);
		return result != 0;
	}

	@Override
	public List<Chapter> read() {
		Cursor cursor = mDatabase.query(TABLE_CHAPTERS_TABLE, getAllColumns(), null,
				null, null, null, null);
		List chapterList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				chapterList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return chapterList;
	}

	@Override
	public List<Chapter> read(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_CHAPTERS_TABLE, getAllColumns(), selection,
				selectionArgs, groupBy, having, orderBy);
		List chapterList = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				chapterList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return chapterList;
	}

	public String[] getAllColumns() {
		return new String[] {COLUMN_CITY,COLUMN_PLACE,COLUMN_DESCRIPTION};
	}

	public Chapter generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		Chapter chapter = new Chapter();
		chapter.setCityName(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
		chapter.setPlaceName(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE)));
		chapter.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
		return chapter;
	}

	public ContentValues generateContentValuesFromObject(Chapter entity) {
		if (entity == null) {
			return null;
		}
		ContentValues values = new ContentValues();
		values.put(COLUMN_CITY, entity.getCityName());
		values.put(COLUMN_PLACE, entity.getPlaceName());
		values.put(COLUMN_DESCRIPTION, entity.getDescription());
		return values;
	}

	@Override
	public List<Chapter> readSpecificColumns(String tableName,
			String[] selectColums, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		return null;
	}
}
