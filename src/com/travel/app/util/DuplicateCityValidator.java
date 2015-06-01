package com.travel.app.util;

import java.util.List;

import android.content.Context;
import android.widget.EditText;

import com.throrinstudio.android.common.libs.validator.AbstractValidator;
import com.throrinstudio.android.common.libs.validator.ValidatorException;
import com.travel.app.activity.R;
import com.travel.app.model.City;

public class DuplicateCityValidator extends AbstractValidator{
	
	private static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.validator_duplicate_city;
	private List<City> cityList;
	private EditText editText;
	
	public DuplicateCityValidator(Context c,EditText editText,List<City> cityList) {
		super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
		this.editText = editText;
		this.cityList = cityList;
	}

	@Override
	public boolean isValid(String value) throws ValidatorException {
		if(null != cityList && cityList.size() > 0){
			for(City city : cityList){
				if(city.getCityName().equalsIgnoreCase(editText.getText().toString()))
					return false;
			}
			return true;
		}else
			return true;
	}

}
