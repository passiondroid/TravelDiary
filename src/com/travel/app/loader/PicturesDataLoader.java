package com.travel.app.loader;

import java.util.List;

import android.content.Context;

import com.travel.app.datasource.DataSource;
import com.travel.app.datasource.PictureDBDataSource;
import com.travel.app.model.Picture;

public class PicturesDataLoader extends DataLoader<List<Picture>>{

	private DataSource<Picture> mDataSource;
	private String mSelection;
	private String[] mSelectionArgs;
	private String mGroupBy;
	private String mHaving;
	private String mOrderBy;
	
	public PicturesDataLoader(Context context, PictureDBDataSource mDataSource2, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		super(context);
		mDataSource = mDataSource2;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mGroupBy = groupBy;
		mHaving = having;
		mOrderBy = orderBy;
	}

	@Override
	protected List<Picture> buildList() {
		List<Picture> pictureList = mDataSource.read(mSelection, mSelectionArgs, mGroupBy, mHaving, mOrderBy);
		return pictureList;
	}
	
	public void insert(Picture entity) {
		new InsertTask(this).execute(entity);
	}

	public void update(Picture entity) {
		new UpdateTask(this).execute(entity);
	}

	public void delete(Picture entity) {
		new DeleteTask(this).execute(entity);
	}

	private class InsertTask extends ContentChangingTask<Picture, Void, Void> {
		InsertTask(PicturesDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(Picture... params) {
			mDataSource.insert(params[0]);
			return (null);
		}
	}

	private class UpdateTask extends ContentChangingTask<Picture, Void, Void> {
		UpdateTask(PicturesDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(Picture... params) {
			mDataSource.update(params[0]);
			return (null);
		}
	}

	private class DeleteTask extends ContentChangingTask<Picture, Void, Void> {
		DeleteTask(PicturesDataLoader loader) {
			super(loader);
		}

		@Override
		protected Void doInBackground(Picture... params) {
			mDataSource.delete(params[0]);
			return (null);
		}
	}


}
