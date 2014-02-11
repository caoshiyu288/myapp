package com.android.myapp.db;

import com.android.myapp.MyApplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider{
	
	private static MyDbHelper mDbHelper;
	public static final String AUTHORITY = "com.android.myapp.db";
	public static final String PARAMETER_NOTIFY = "notify";
	@Override
	public boolean onCreate() {
		mDbHelper = MyDbHelper.getInstance(getContext());
		MyApplication app = (MyApplication)getContext();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
/*        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(args.table);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor result = qb.query(db, projection, args.where, args.args, null, null, sortOrder);*/
		Cursor result = mDbHelper.query(args.table, projection, args.where, args.args, sortOrder);
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
	}

	@Override
	public String getType(Uri uri) {
        SqlArguments args = new SqlArguments(uri, null, null);
        if (TextUtils.isEmpty(args.where)) {
            return "vnd.android.cursor.dir/" + args.table;
        } else {
            return "vnd.android.cursor.item/" + args.table;
        }
    }

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SqlArguments args = new SqlArguments(uri);
		final long rowId = mDbHelper.insert(args.table, null, values);
		if(rowId > 0){
			uri = ContentUris.withAppendedId(uri, rowId);//append id to the end of uri
			sendNotify(uri);
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri);
		final int count = mDbHelper.delete(args.table, args.where, args.args);
		if(count > 0){
			sendNotify(uri);
		}
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri);
		final int count = mDbHelper.update(args.table, values, args.where, args.args);
		if(count > 0){
			sendNotify(uri);
		}
		return count;
	}
	
    private void sendNotify(Uri uri) {
        String notify = uri.getQueryParameter(PARAMETER_NOTIFY);
        if (notify == null || "true".equals(notify)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
    static class SqlArguments {
        public final String table;
        public final String where;
        public final String[] args;

        SqlArguments(Uri url, String where, String[] args) {
            if (url.getPathSegments().size() == 1) {
                this.table = url.getPathSegments().get(0);
                this.where = where;
                this.args = args;
            } else if (url.getPathSegments().size() != 2) {
                throw new IllegalArgumentException("Invalid URI: " + url);
            } else if (!TextUtils.isEmpty(where)) {
                throw new UnsupportedOperationException("WHERE clause not supported: " + url);
            } else {
                this.table = url.getPathSegments().get(0);
                this.where = "_id=" + ContentUris.parseId(url);
                this.args = null;
            }
        }

        SqlArguments(Uri url) {
            if (url.getPathSegments().size() == 1) {
                table = url.getPathSegments().get(0);
                where = null;
                args = null;
            } else {
                throw new IllegalArgumentException("Invalid URI: " + url);
            }
        }
    }
    
    /*
     *  db.execSQL("ALTER TABLE table " +
                        "ADD/DROP COLUMN email TEXT;");
                        
     * */
}
