package com.android.myapp;

import com.android.myapp.db.MyContentProvider;
import com.android.myapp.db.MyDbHelper;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyAppSettings {
	public static final class DbParameter implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" +
        		MyContentProvider.AUTHORITY + "/" + MyDbHelper.TABLE_NAME +
                "?" + MyContentProvider.PARAMETER_NOTIFY + "=true");
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" +
        		MyContentProvider.AUTHORITY + "/" + MyDbHelper.TABLE_NAME +
                "?" + MyContentProvider.PARAMETER_NOTIFY + "=false");
        static Uri getContentUri(long id, boolean notify) {
            return Uri.parse("content://" + MyContentProvider.AUTHORITY +
                    "/" + MyDbHelper.TABLE_NAME + "/" + id + "?" +
                    MyContentProvider.PARAMETER_NOTIFY + "=" + notify);
        }
        
        public static final String USER = "user";
        public static final String PASSWORD = "password";
	}
}
