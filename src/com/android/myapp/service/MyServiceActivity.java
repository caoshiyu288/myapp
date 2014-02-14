package com.android.myapp.service;

import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.myapp.R;
import com.android.myapp.utils.MyLog;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

public class MyServiceActivity extends Activity{
	private TextView mLable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service);
		ButterKnife.inject(this);
		mLable = (TextView)findViewById(R.id.textView1);
	}
	@OnClick(R.id.button1)public void startService(){
		Intent intent = new Intent();
		intent.setClass(this, MyService.class);
		startService(intent);
	}
	@OnClick(R.id.button2)public void stopService(){
		Intent intent = new Intent();
		intent.setClass(this, MyService.class);
		stopService(intent);
	}
	
	@OnClick(R.id.button3)public void bindService(){
		Intent intent = new Intent();
		intent.setClass(this, MyService.class);
		bindService(intent, connection, BIND_AUTO_CREATE); 
	}
	
	@OnClick(R.id.button4)public void unBindService(){
		unbindService(connection); 
	}
	
	
	final ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyLog.d("onServiceConnected,name:"+name);
			
			IService bind = (IService)service;
			mLable.setText(bind.getName());
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mLable.setText("");
		}  
		
    };  
}
