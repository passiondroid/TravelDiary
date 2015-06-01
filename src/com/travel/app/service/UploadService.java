package com.travel.app.service;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.travel.app.datasource.PictureDBDataSource;
import com.travel.app.datasource.PlaceDBDataSource;
import com.travel.app.db.DBHelper;
import com.travel.app.model.Picture;
import com.travel.app.model.Place;

public class UploadService extends IntentService{
	
	private PictureDBDataSource mPictureDBSource;
	private PlaceDBDataSource mPlaceDBSource;
	private Handler handler;
	
	public UploadService() {
		super("UploadService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String place = intent.getStringExtra("PLACE");
		mPictureDBSource = new PictureDBDataSource(DBHelper.getInstance(getApplicationContext()).getReadableDatabase());
		mPlaceDBSource = new PlaceDBDataSource(DBHelper.getInstance(getApplicationContext()).getReadableDatabase());
		System.out.println(":::::::::::::::::::"+place+":::::::::::");
		GetImagePathTask task = new GetImagePathTask();
		task.execute(place);
		
	}
	
	class GetImagePathTask extends AsyncTask<String, Void, List<Picture>>{

		@Override
		protected List<Picture> doInBackground(String... params) {
			try{
			List<Place> placeList = mPlaceDBSource.read("PLACE = ?", new String[]{params[0]}, null, null, null);
			List<Picture> pictureList = mPictureDBSource.read("PLACE = ? AND UPLOADED = 0 ", new String[]{params[0]}, null,  null, null);
			if(null != placeList && placeList.size()==1){
					final ParseObject object = new ParseObject("Places");
					object.put("city", placeList.get(0).getCityName());
					object.put("place", params[0]);
					object.put("description", placeList.get(0).getDescription());
					object.put("user", ParseUser.getCurrentUser());
					object.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e==null){
								System.out.println(":::::::::::::::Uploaded:::::::::::::::");
							}else{
								Log.e("Exception","Error",e);
								e.printStackTrace();
							}
						}
					});
					/*for(Picture pic : pictureList){
				
						final ParseObject image = new ParseObject("Pictures");
						//File file  = new File(FileUtils.getPath(getApplicationContext(), pic.getUri()));
						Bitmap bmp = ImageLoader.getInstance().loadImageSync(pic.getImagePath());
						
						byte[] byteArray = BitmapUtil.getScaledPhoto(bmp);
						final ParseFile parsefile = new ParseFile(byteArray);
						System.out.println(":::::::::::::::Ready to upload:::::::::::::::");
						parsefile.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException arg0) {
								image.put("picture", parsefile);
								object.put("photos",image);
								object.saveInBackground(new SaveCallback() {
									
									@Override
									public void done(ParseException arg0) {
										System.out.println(":::::::::::::::Uploaded:::::::::::::::");
									}
									
								});
							}
						});
						
					}*/
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return mPictureDBSource.read("PLACE = ? AND UPLOADED = 0 ", new String[]{params[0]}, null,  null, null);
		}
		
		@Override
		protected void onPostExecute(List<Picture> result) {
			super.onPostExecute(result);
			for(Picture pic : result){
				System.out.println("Image Path :: "+pic.getImagePath());
			}
			//ParseFile file = new 
		}
	}
	
	/*private void uploadImages(List<Picture> picList){
		Looper.prepare();
		handler = new Handler();
	}*/

}
