package com.android.myapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("com.android.my.receiver")){
			Toast.makeText(context, "接受广播成功！", Toast.LENGTH_SHORT).show();
		}
	}

}
