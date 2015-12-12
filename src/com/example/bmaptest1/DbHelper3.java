package com.example.bmaptest1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper3 extends SQLiteOpenHelper {

	private static  final String CREATE_PHONE = "CREATE TABLE user(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," 
	+ "category text DEFAULT \"\"," + "name text DEFAULT \"\"," + "number text DEFAULT \"\")";
	
	
	public DbHelper3(Context context, String name, CursorFactory factory, int version) {
		
		super(context, name, factory, version);
		if (true) {
			Log.e("222", "222");
		}
	}

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_PHONE);
	}

}
