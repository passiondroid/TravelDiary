package com.travel.app.adapter;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.travel.app.activity.R;
import com.travel.app.model.City;

public class CityAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private boolean isFromSecondScreen;
	private List<City> cityList;
	//private int count=1;

	public CityAdapter(Context context,boolean isFromSecondScreen,List<City> cityList) {
		this.isFromSecondScreen=isFromSecondScreen;
		inflater = ((Activity) context).getLayoutInflater();
		this.cityList = cityList;
	}

	@Override
	public int getCount() {
		return cityList.size();
	}

	@Override
	public Object getItem(int position) {
		return cityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		//  if (view == null) 
     //   {
              if (isFromSecondScreen) {
            	  view = inflater.inflate(R.layout.item_layout_second_screen,parent, false);
                  TextView item_name=(TextView) view.findViewById(R.id.city_name_textView);
                  item_name.setText(cityList.get(position).getCityName());
			}
              else {
            	  view = inflater.inflate(R.layout.item_layout_third_screen,parent, false);
                  TextView item_no=(TextView) view.findViewById(R.id.item_no_textView);
                  TextView item_name=(TextView) view.findViewById(R.id.city_name_textView);
                  Button deleteBtn= (Button) view.findViewById(R.id.delete_btn);
                  item_no.setText(String.valueOf(position+1));
                  item_name.setText(cityList.get(position).getCityName());
                  deleteBtn.setTag(cityList.get(position).getCityName());
			}
        //} 
		  return view;
	}

}
