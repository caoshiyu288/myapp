package com.android.myapp;

import android.content.ContentValues;
import android.database.Cursor;

public class MyApp {
	public String user;
	public String password;
	public String iconUrl;
	public String name;
	public ContentValues toContentValues(){
		ContentValues values = new ContentValues();
		values.put(MyAppSettings.DbParameter.USER, user);
		values.put(MyAppSettings.DbParameter.PASSWORD, password);
		return values;
	}
	public void outDb(Cursor c){
		user=c.getString(c.getColumnIndexOrThrow(MyAppSettings.DbParameter.USER));
		password=c.getString(c.getColumnIndexOrThrow(MyAppSettings.DbParameter.PASSWORD));
	}
}
