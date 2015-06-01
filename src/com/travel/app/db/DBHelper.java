package com.travel.app.db;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * A helper class to manage database creation and version management using 
 * an application's raw asset files.
 */
public final class DBHelper extends SQLiteOpenHelper {

	public Context mContext;
    private String dbPath;
    private static final String SYS_DEFAULT_DB_PATH = "/data/data/";
    private static final String DB_FOLDER_INTERNAL ="/database/";
	private static final String DB_FILE_NAME = "TravelDiary.sqlite";// the extension;	// may be// .sqlite or// .db
	private SQLiteDatabase database;
	//private boolean isDBExternal=false;
	private static DBHelper dbHelper = null;
	public static String DB_PATH =null; 
	private static String DB_NAME = "TravelDiary.db";
	
	/**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
	private DBHelper(){
		super(null,null,null,1);
	}
	
	public static synchronized DBHelper getInstance(Context context){
		if(dbHelper == null) {
			dbHelper = new DBHelper(context);
		}
		return dbHelper;
	}
	
	private DBHelper(Context context)  {
		super(context, DB_NAME, null, 1);
		this.mContext = context;
		//dbPath = SYS_DEFAULT_DB_PATH + mContext.getApplicationContext().getPackageName() + DB_FOLDER_INTERNAL;
		dbPath = Environment.getExternalStorageDirectory() + DB_FOLDER_INTERNAL;
		DB_PATH = this.dbPath + DB_NAME;
	}

	public boolean createdatabase() throws IOException {
		boolean dbexist = checkdatabase();
		if (!dbexist) {
			Log.i("DBHelper","creating db at location : " + this.dbPath + DB_NAME);
			File dir = new File(this.dbPath);
			dir.mkdirs();
			SQLiteDatabase triDatabase = SQLiteDatabase.openOrCreateDatabase(this.dbPath + DB_NAME, null);
			triDatabase.close();
			try {
				copydatabase(this.dbPath + DB_NAME);
			} catch (IOException e) {
				Log.e("DB Exception", e.getMessage(),e);
				throw new Error("Error copying database");
			}
		}
			return dbexist;
		}

	/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
	private boolean checkdatabase() {
		boolean checkdb = false;
		File dbfile;
		try {
			dbfile = new File(this.dbPath + DB_NAME);
			checkdb = dbfile.exists();
			Log.i("DBHelper","Checking the database at location : "+this.dbPath + DB_NAME +", is DB Present "+checkdb);
		} catch (Exception e) {
			Log.e("Exception","Exception occured while checking database",e);
		}
		return checkdb;
	}

	
	/**
     * Copies your database from  assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
	private void copydatabase(String path) throws IOException {
		// Open your local db as the input stream
		Log.i("DBHelper","Copying db at path : "+path);
		
		InputStream inputStream = mContext.getAssets().open(DB_FILE_NAME);

		// Path to the just created empty db

		// Open the empty db as the output stream
		OutputStream outputStream = new FileOutputStream(path);

		// transfer byte to inputfile to outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}

		// Close the streams
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}
	
	
		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
		
		public SQLiteDatabase getReadableDatabase() {
			/*if(isDBExternal){
				String filePath =  Constant.DB_FOLDER_EXTERNAL + Constant.DB_NAME;
				database = SQLiteDatabase.openDatabase(filePath, null,SQLiteDatabase.OPEN_READONLY);
			}else{*/
				//database = super.getReadableDatabase();
			database = SQLiteDatabase.openDatabase(DB_PATH, null,SQLiteDatabase.OPEN_READONLY);
			//}
			return database;
		}

		public SQLiteDatabase getWritableDatabase() {
			/*if(isDBExternal){
				String filePath =  Constant.DB_FOLDER_EXTERNAL+ Constant.DB_NAME;
				database = SQLiteDatabase.openDatabase(filePath, null,SQLiteDatabase.OPEN_READWRITE);
			}else{*/
				//database = super.getWritableDatabase();
				database = SQLiteDatabase.openDatabase(DB_PATH, null,SQLiteDatabase.OPEN_READWRITE);
			//}
			return database;
		}
		
				
}
