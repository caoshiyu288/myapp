package com.android.myapp;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.android.myapp.db.MyDbHelper;
import com.android.myapp.db.SQLiteCursorLoader;
import com.android.myapp.network.MyWebViewActivity;
import com.android.myapp.service.MyReceiverActivity;
import com.android.myapp.service.MyServiceActivity;

import de.greenrobot.event.EventBus;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MyMain extends Activity{
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bd = intent.getExtras();
		String user = bd.getString("user"); 
		setContentView(R.layout.main);
		ButterKnife.inject(this);
		TextView v = (TextView)findViewById(R.id.textView1);
		v.setText(user+" login success!");
		
		EventBus.getDefault().register(this);//post
	}
	private void store(){
//		System.putInt(getContentResolver(), "user", 0);
//		System.getInt(getContentResolver(),"user",1);
		SharedPreferences pf = getSharedPreferences("MYAPP_PF", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pf.edit();
		editor.putString("user", "admin");
		editor.commit();
	}
	private void get(){
		SharedPreferences pf = getSharedPreferences("MYAPP_PF", Context.MODE_PRIVATE);
		pf.getString("user", "");
	}
	public void onEvent(final MyApp app) {
		
	}
	
    @OnClick(R.id.button1) public void showAllUsers(){
    	Intent intent = new Intent();
    	intent.setClass(this, MyUsers.class);
    	startActivity(intent);
    }	
    @OnClick(R.id.button2) public void receiver(){
    	Intent intent = new Intent();
    	intent.setClass(this, MyReceiverActivity.class);
    	startActivity(intent);
    }	
    @OnClick(R.id.button3) public void service(){
    	Intent intent = new Intent();
    	intent.setClass(this, MyServiceActivity.class);
    	startActivity(intent);
    }	
    @OnClick(R.id.button4) public void showMultiPages(){
    	Intent intent = new Intent();
    	intent.setClass(this, MyMultiPages.class);
    	startActivity(intent);
    }
    @OnClick(R.id.button5) public void showWebView(){
    	Intent intent = new Intent();
    	intent.setClass(this, MyWebViewActivity.class);
    	startActivity(intent);
    }
}
