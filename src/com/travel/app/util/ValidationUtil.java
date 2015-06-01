package com.travel.app.util;

import java.util.List;

import android.content.Context;
import android.widget.EditText;

import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;
import com.travel.app.model.City;
import com.travel.app.model.Place;

public class ValidationUtil {

	
	public static boolean  checkValidName(Context c,EditText editText) {
		//AlnumValidator alnumValidator=new AlnumValidator(c);
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		//validate.addValidator(alnumValidator);
		return validate.isValid();
	}
	
	public static boolean checkValidEmailId(Context c,EditText editText) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		EmailValidator emailValidator=new EmailValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		validate.addValidator(emailValidator);
		return validate.isValid();
	}
	
	public static boolean checkValidPhoneNumber(Context c,EditText editText) {
		if(editText.getText().toString()!=null && !editText.getText().toString().equals("")){
			PhoneValidator phoneValidator=new PhoneValidator(c);
			Validate validate =new Validate(editText);
			validate.addValidator(phoneValidator);
			return validate.isValid();
		}else{
			return true;
		}
	}
	
	public static boolean  checkValidPasword(Context c,EditText editText) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		StringSizeValidater stringSizeValidater=new StringSizeValidater(c, 6);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		validate.addValidator(stringSizeValidater);
		return validate.isValid();
	}
	
	public static boolean  checkValidCity(Context c,EditText editText,List<City> cityList) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		DuplicateCityValidator dupCityValidator = new DuplicateCityValidator(c, editText, cityList);
		validate.addValidator(dupCityValidator);
		return validate.isValid();
	}
	
	public static boolean  checkValidPlace(Context c,EditText editText,List<String> placeList,String mode) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		if(mode.equals("Add")){
			DuplicatePlaceValidator dupPlaceValidator = new DuplicatePlaceValidator(c, editText, placeList);
			validate.addValidator(dupPlaceValidator);
		}
		return validate.isValid();
	}
	
	public static boolean  checkValidPlaceDescription(Context c,EditText editText) {
		NotEmptyValidator notEmptyValidator=new NotEmptyValidator(c);
		Validate validate =new Validate(editText);
		validate.addValidator(notEmptyValidator);
		return validate.isValid();
	}
}
