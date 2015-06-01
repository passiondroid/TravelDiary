package com.travel.app.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.travel.app.activity.R;
import com.travel.app.adapter.ShareFragmentListAdapter;
import com.travel.app.model.Picture;

public class ShareActivityFragment extends ListFragment{
	
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	LayoutInflater inflater = LayoutInflater.from(getActivity());
	List<Picture> picsList = new ArrayList<Picture>();
	Picture pic1 = new Picture();
	pic1.setImagePath("");
	Picture pic2 = new Picture();
	pic2.setImagePath("");
	picsList.add(pic1);
	picsList.add(pic2);
	
	DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.ic_stub)
	.showImageForEmptyUri(R.drawable.ic_empty)
	.showImageOnFail(R.drawable.ic_error)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.build();
	
	ShareFragmentListAdapter adapter = new ShareFragmentListAdapter(inflater,picsList,options);
	this.setListAdapter(adapter);
}

}
