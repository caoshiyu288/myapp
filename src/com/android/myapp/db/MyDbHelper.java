package com.android.myapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
	static final String DB_NAME = "myapp_db";
	static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "myapp";
	static MyDbHelper mInstance;
	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	public static MyDbHelper getInstance(Context context){
		if(mInstance == null){
			mInstance = new MyDbHelper(context);
		}
		return mInstance;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE myapp ("+
				"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+"user TEXT,"
				+"password TEXT"+
				");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		final int version = oldVersion;
		if (version != DB_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
	}
	
    public synchronized long insert(String table, String nullColumnHack, ContentValues values) {
    	SQLiteDatabase db = getWritableDatabase();
        return db.insert(table, nullColumnHack, values);
    }
    public synchronized Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String orderBy) {
    	SQLiteDatabase db = getWritableDatabase();
        return db.query(table, columns, selection, selectionArgs, null, null, orderBy);
    }
    public synchronized int delete(String table, String whereClause, String[] whereArgs){
    	SQLiteDatabase db = getWritableDatabase();
    	return db.delete(table, whereClause, whereArgs);
    }
    public synchronized int update(String table, ContentValues values, String whereClause, String[] whereArgs){
    	SQLiteDatabase db = getWritableDatabase();
    	return db.update(table, values, whereClause, whereArgs);
    }
    //sql 
    /*
     * insert into table(user, password) values('admin','abc');
     * 
     * delete from table where user = 'admin';
     * 
     * select * from table where user = 'admin';
     * 
     * update table set password='abc' where user = 'admin';
     * */
    public synchronized void rawSql(String sql) {
    	SQLiteDatabase db = getWritableDatabase();
    	db.execSQL(sql);
    }
    
}
