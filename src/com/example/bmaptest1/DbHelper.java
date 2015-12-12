package com.example.bmaptest1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	
	private static final String CREATE_HISTORY = "create table history(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "companyName text DEFAULT \"\"," + "expressNo text DEFAULT \"\"," +  "companyCode text DEFAULT \"\")";
	
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_HISTORY);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
