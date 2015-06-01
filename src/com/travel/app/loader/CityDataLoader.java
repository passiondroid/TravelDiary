package com.travel.app.loader;

import java.util.List;

import android.content.Context;

import com.travel.app.datasource.CityDBDataSource;
import com.travel.app.datasource.DataSource;
import com.travel.app.model.City;

public class CityDataLoader extends DataLoader<List<City>>{

	private DataSource<City> mDataSource;
	private String mSelection;
	private String[] mSelectionArgs;
	private String mGroupBy;
	private String mHaving;
	private String mOrderBy;
	
	public CityDataLoader(Context context, CityDBDataSource mDataSource2, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		super(context);
		mDataSource = mDataSource2;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mGroupBy = groupBy;
		mHaving = having;
		mOrderBy = orderBy;
	}

	@Override
	protected List<City> buildList() {
		List<City> cityList = mDataSource.read(mSelection, mSelectionArgs, mGroupBy, mHaving, mOrderBy);
		return cityList;
	}
	
	public void insert(City entity) {
		new InsertTask(this).execute(entity);
	}

	public void update(City entity) {
		new UpdateTask(this).execute(entity);
	}

	public void delete(City entity) {
		new DeleteTask(this).execute(entity);
	}

	private class InsertTask extends ContentChangingTask<City, Void, Void> {
		InsertTask(CityDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(City... params) {
			mDataSource.insert(params[0]);
			return (null);
		}
	}

	private class UpdateTask extends ContentChangingTask<City, Void, Void> {
		UpdateTask(CityDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(City... params) {
			mDataSource.update(params[0]);
			return (null);
		}
	}

	private class DeleteTask extends ContentChangingTask<City, Void, Void> {
		DeleteTask(CityDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(City... params) {
			mDataSource.delete(params[0]);
			return (null);
		}
	}


}
