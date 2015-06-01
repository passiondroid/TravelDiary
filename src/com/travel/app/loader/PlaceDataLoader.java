package com.travel.app.loader;

import java.util.List;

import android.content.Context;

import com.travel.app.datasource.DataSource;
import com.travel.app.datasource.PlaceDBDataSource;
import com.travel.app.model.Place;

public class PlaceDataLoader extends DataLoader<List<Place>>{

	private DataSource<Place> mDataSource;
	private String mSelection;
	private String[] mSelectionArgs;
	private String mGroupBy;
	private String mHaving;
	private String mOrderBy;
	
	public PlaceDataLoader(Context context, PlaceDBDataSource mDataSource2, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		super(context);
		mDataSource = mDataSource2;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mGroupBy = groupBy;
		mHaving = having;
		mOrderBy = orderBy;
	}

	@Override
	protected List<Place> buildList() {
		List<Place> placesList = mDataSource.read(mSelection, mSelectionArgs, mGroupBy, mHaving, mOrderBy);
		return placesList;
	}
	
	public void insert(Place entity) {
		new InsertTask(this).execute(entity);
	}

	public void update(Place entity) {
		new UpdateTask(this).execute(entity);
	}

	public void delete(Place entity) {
		new DeleteTask(this).execute(entity);
	}

	private class InsertTask extends ContentChangingTask<Place, Void, Void> {
		InsertTask(PlaceDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(Place... params) {
			mDataSource.insert(params[0]);
			return (null);
		}
	}

	private class UpdateTask extends ContentChangingTask<Place, Void, Void> {
		UpdateTask(PlaceDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(Place... params) {
			mDataSource.update(params[0]);
			return (null);
		}
	}

	private class DeleteTask extends ContentChangingTask<Place, Void, Void> {
		DeleteTask(PlaceDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(Place... params) {
			mDataSource.delete(params[0]);
			return (null);
		}
	}


}
