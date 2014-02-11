package com.android.myapp.service;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.android.myapp.R;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MyReceiverActivity extends FragmentActivity {
	private MyBroadcastReceiver mReceiver;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.receiver);
		ButterKnife.inject(this);
	}
	
	@OnClick(R.id.button1)public void register(){
		mReceiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.android.my.receiver");
		registerReceiver(mReceiver, filter);
	}
	@OnClick(R.id.button2)public void unRegister(){
		unregisterReceiver(mReceiver);
	}
	@OnClick(R.id.button3)public void sendBroadcast(){
		Intent intent = new Intent("com.android.my.receiver");
		sendBroadcast(intent);
	}
	
}

