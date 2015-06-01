package com.travel.app.activity;

import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.travel.app.datasource.PictureDBDataSource;
import com.travel.app.db.DBHelper;
import com.travel.app.fragment.ImageGridFragment;
import com.travel.app.fragment.ImagePagerFragment;
import com.travel.app.loader.PicturesDataLoader;
import com.travel.app.model.Picture;
import com.travel.app.util.BitmapUtil;
import com.u1aryz.android.lib.newpopupmenu.MenuItem;
import com.u1aryz.android.lib.newpopupmenu.PopupMenu;
import com.u1aryz.android.lib.newpopupmenu.PopupMenu.OnItemSelectedListener;

public class AddPhotoActivity extends FragmentActivity implements OnItemSelectedListener{

	//private static final int LOADER_ID = 4;
	private static final int SELECT_PICTURE = 1;
	private String place;
	public static List<Picture> picsList;
	private SQLiteDatabase mDatabase;
	private PictureDBDataSource mDataSource;
	private DBHelper mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = DBHelper.getInstance(this);
		mDatabase = mDbHelper.getWritableDatabase();
		mDataSource = new PictureDBDataSource(mDatabase);
		
		//int frIndex = getIntent().getIntExtra(Constants.Extra.FRAGMENT_INDEX, 0);
		Fragment fr;

		Intent intent = getIntent();
		place = intent.getStringExtra("Place");
		
		String tag  = ImagePagerFragment.class.getSimpleName();
		fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImageGridFragment();
		}
		
		getSupportFragmentManager().beginTransaction().add(R.id.container, fr,tag).commit();
		setContentView(R.layout.add_photos_layout);
		
	}
	
	public void onAttachmentClick(View view){
		PopupMenu menu = new PopupMenu(this);
		menu.setOnItemSelectedListener(this);
		// Add Menu (Android menu like style)
		menu.add(0, R.string.camera).setIcon(getResources().getDrawable(android.R.drawable.ic_menu_camera));
		menu.add(1, R.string.gallery).setIcon(getResources().getDrawable(android.R.drawable.ic_menu_gallery));
		menu.show(view);

	}

	@Override
	public void onItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == RESULT_OK) {
				if (requestCode == SELECT_PICTURE) {
					Uri selectedImageUri = data.getData();
					/*String path = FileUtils.getPath(this, selectedImageUri);
					File file = new File(path);*/
					Picture pic = new Picture();
					pic.setUri(selectedImageUri);
					pic.setImagePath(selectedImageUri.toString());
					pic.setTitle("");
					pic.setPlace(place);
					new InsertTask().execute(pic);
					
				}
			}
		} catch (IllegalArgumentException e){
			Log.e("Travel","Exception",e);
		}catch(Exception ex){
			Log.e("Travel","Exception",ex);
		}
	}
	
	class InsertTask extends AsyncTask<Picture, Void, Void>{

		@Override
		protected Void doInBackground(Picture... params) {
			mDataSource.insert(params[0]);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			ImageGridFragment.loader.startLoading();
		}
	}
	
	public void onShareClick(View view){
		startActivity(new Intent(this, ShareActivity.class).putExtra("PLACE", place));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(null!=mDatabase)
			mDatabase.close();
		mDataSource = null;
	}
	
}
