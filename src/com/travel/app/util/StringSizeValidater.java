package com.travel.app.util;

import android.content.Context;

import com.throrinstudio.android.common.libs.validator.AbstractValidator;
import com.throrinstudio.android.common.libs.validator.ValidatorException;
import com.travel.app.activity.R;

public class StringSizeValidater extends AbstractValidator {
	
	private static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.validator_srting_size;
	private int minLength;
	
	public StringSizeValidater(Context c,int minLength) {
		super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
		this.minLength=minLength;
	}

	@Override
	public boolean isValid(String value) throws ValidatorException {
		return  value.length()>=minLength;
	}

}
