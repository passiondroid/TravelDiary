package com.travel.app.fragment;

import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.travel.app.activity.AddPhotoActivity;
import com.travel.app.activity.R;
import com.travel.app.datasource.PictureDBDataSource;
import com.travel.app.db.DBHelper;
import com.travel.app.loader.PicturesDataLoader;
import com.travel.app.model.Picture;

public class ImageGridFragment extends AbsListViewBaseFragment implements LoaderManager.LoaderCallbacks<List<Picture>>{

	public static final int INDEX = 1;
	public static PicturesDataLoader loader;
	private SQLiteDatabase mDatabase;
	private PictureDBDataSource mDataSource;
	private DBHelper mDbHelper;
	public static final int LOADER_ID = 5;
	private String place;
	public static List<Picture> picsList;

	DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				//.showImageForEmptyUri(R.drawable.ic_empty)
				//.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		
		mDbHelper = DBHelper.getInstance(this.getActivity());
		mDatabase = mDbHelper.getWritableDatabase();
		mDataSource = new PictureDBDataSource(mDatabase);
		Intent intent = this.getActivity().getIntent();
		place = intent.getStringExtra("Place");
		getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fr_image_grid, container, false);
		listView = (GridView) rootView.findViewById(R.id.grid);
		//((GridView) listView).setAdapter(new ImageAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		return rootView;
	}
	
	public class ImageAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private List<Picture> picsList;

		ImageAdapter(List<Picture> picsList) {
			inflater = LayoutInflater.from(getActivity());
			this.picsList = picsList;
		}

		@Override
		public int getCount() {
			return picsList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = inflater.inflate(R.layout.item_grid_image, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.image);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			ImageLoader.getInstance()
					.displayImage(picsList.get(position).getImagePath(), holder.imageView, options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							holder.progressBar.setProgress(0);
							holder.progressBar.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
							holder.progressBar.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							holder.progressBar.setVisibility(View.GONE);
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri, View view, int current, int total) {
							holder.progressBar.setProgress(Math.round(100.0f * current / total));
						}
					});

			return view;
		}
	}

	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}
	
	@Override
	public Loader<List<Picture>> onCreateLoader(int arg0, Bundle arg1) {
		loader = new PicturesDataLoader(this.getActivity(), mDataSource, "PLACE = ?", new String[]{place}, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<Picture>> arg0, List<Picture> pictureList) {
		picsList = pictureList;
		((GridView) listView).setAdapter(new ImageAdapter(pictureList));
	}

	@Override
	public void onLoaderReset(Loader<List<Picture>> arg0) {
		// TODO Auto-generated method stub
	}
}