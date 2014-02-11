package com.android.myapp.utils;

import android.util.Log;

public class MyLog {
	private static final String TAG = "MyApp";
	public static final void v(String msg){
		Log.v(TAG, msg);
	}
	public static final void d(String msg){
		Log.d(TAG, msg);
	}
	public static final void e(String msg){
		Log.e(TAG, msg);
	}
	public static final void w(String msg){
		Log.w(TAG, msg);
	}
	public static final void i(String msg){
		Log.i(TAG, msg);
	}
	
}
