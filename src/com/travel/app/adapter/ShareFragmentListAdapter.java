package com.travel.app.adapter;

import java.util.List;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.travel.app.activity.R;
import com.travel.app.model.Picture;

public class ShareFragmentListAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private List<Picture> picsList;
	private DisplayImageOptions options;

	public ShareFragmentListAdapter(LayoutInflater inflater, List<Picture> picsList, DisplayImageOptions options) {
		this.inflater = inflater;
		this.picsList = picsList;
		this.options = options;
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_share_fragment, parent, false);
			holder = new ViewHolder();
			assert view != null;
			holder.imageView = (ImageView) view.findViewById(R.id.imgView);
			holder.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		ImageLoader.getInstance().displayImage(picsList.get(position).getImagePath(), holder.imageView, options);

		return view;
	}
	
	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}

}
