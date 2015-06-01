package com.travel.app.util;

import java.util.List;

import android.content.Context;
import android.widget.EditText;

import com.throrinstudio.android.common.libs.validator.AbstractValidator;
import com.throrinstudio.android.common.libs.validator.ValidatorException;
import com.travel.app.activity.R;
import com.travel.app.model.City;
import com.travel.app.model.Place;

public class DuplicatePlaceValidator extends AbstractValidator{
	
	private static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.validator_duplicate_place;
	private List<String> placeList;
	private EditText editText;
	
	public DuplicatePlaceValidator(Context c,EditText editText,List<String> placeList) {
		super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
		this.editText = editText;
		this.placeList = placeList;
	}

	@Override
	public boolean isValid(String value) throws ValidatorException {
		if(null != placeList && placeList.size() > 0){
			for(String place : placeList){
				if(place.equalsIgnoreCase(editText.getText().toString().trim()))
					return false;
			}
			return true;
		}else
			return true;
	}

}
