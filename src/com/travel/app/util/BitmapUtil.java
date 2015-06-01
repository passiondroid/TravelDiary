package com.travel.app.util;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

@SuppressLint("NewApi")
public class BitmapUtil {

	public static int calculateInSampleSize( 
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image 
		final int height = options.outHeight; 
		final int width = options.outWidth; 
		int inSampleSize = 1; 

		if (height > reqHeight || width > reqWidth) { 

			final int halfHeight = height / 2; 
			final int halfWidth = width / 2; 

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both 
			// height and width larger than the requested height and width. 
			while ((halfHeight / inSampleSize) > reqHeight 
					&& (halfWidth / inSampleSize) > reqWidth) { 
				inSampleSize *= 2; 
			} 
		} 

		return inSampleSize; 
	} 

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, Context context) { 
		
		int Measuredwidth = 0;
		int Measuredheight = 0;
		Point size = new Point();
		WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		 
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{ 
		    w.getDefaultDisplay().getSize(size);
		 
		    Measuredwidth = size.x;
		    Measuredheight = size.y;
		} 
		else 
		{ 
		    Display d = w.getDefaultDisplay();
		    Measuredwidth = d.getWidth();
		    Measuredheight = d.getHeight();
		} 

		// First decode with inJustDecodeBounds=true to check dimensions 
		final BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inJustDecodeBounds = true; 
		//BitmapFactory.decodeFile(path, options);
		BitmapFactory.decodeResource(res, resId, options); 

		// Calculate inSampleSize 
		options.inSampleSize = calculateInSampleSize(options, Measuredwidth, Measuredheight); 

		// Decode bitmap with inSampleSize set 
		options.inJustDecodeBounds = false; 
		return BitmapFactory.decodeResource(res, resId, options); 
	} 
	
	public static byte[] getScaledPhoto(Bitmap bmp) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 80, stream);
		
		byte[] data = stream.toByteArray();
		
		// Resize photo from byte array
		Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
		Bitmap mealImageScaled = Bitmap.createScaledBitmap(image, 200, 200
				* image.getHeight() / image.getWidth(), false);

		// Override Android default landscape orientation and save portrait
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
				0, mealImageScaled.getWidth(), mealImageScaled.getHeight(),
				matrix, true);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		rotatedScaledMealImage.compress(Bitmap.CompressFormat.PNG, 80, bos);

		return bos.toByteArray();

	}

}
