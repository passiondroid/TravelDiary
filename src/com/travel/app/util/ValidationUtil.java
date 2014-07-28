package com.travel.app.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.ValidatorException;
import com.throrinstudio.android.common.libs.validator.validator.AlnumValidator;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.NumericValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;
import com.throrinstudio.android.common.libs.validator.validator.RangeValidator;

public class ValidationUtil {

	
	public static void  checkValidName(Context c,EditText editText) {
		AlnumValidator alnumValidator=new AlnumValidator(c);
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		validate.addValidator(alnumValidator);
		validate.isValid();
	}
	
	public static void checkValidEmailId(Context c,EditText editText) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		EmailValidator emailValidator=new EmailValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		validate.addValidator(emailValidator);
		validate.isValid();
	}
	
	public static void checkValidPhoneNumber(Context c,EditText editText) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		PhoneValidator phoneValidator=new PhoneValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		validate.addValidator(phoneValidator);
		validate.isValid();
	}
	
	public static void checkValidPasword(Context c,EditText editText) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		StringSizeValidater stringSzieValidater=new StringSizeValidater(c, 6);
		Validate validate =new Validate(editText);
//		validate.addValidator(notEmptyValidator);
		validate.addValidator(stringSzieValidater);
		validate.isValid();
		
		
	}
}
