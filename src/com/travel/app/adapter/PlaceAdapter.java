package com.travel.app.adapter;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.travel.app.activity.R;
import com.travel.app.model.Place;

public class PlaceAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private List<Place> placeList;

	public PlaceAdapter(Context context,List<Place> placeList) {
		inflater = ((Activity) context).getLayoutInflater();
		this.placeList = placeList;
	}

	@Override
	public int getCount() {
		return placeList.size();
	}

	@Override
	public Object getItem(int position) {
		return placeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		view = inflater.inflate(R.layout.item_layout_second_screen,parent, false);
		TextView item_name=(TextView) view.findViewById(R.id.city_name_textView);
		item_name.setText(placeList.get(position).getPlaceName());
		return view;
	}

}
