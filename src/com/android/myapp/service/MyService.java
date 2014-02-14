package com.android.myapp.service;

import com.android.myapp.R;
import com.android.myapp.utils.MyLog;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service{
	private MediaPlayer player; 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		player = MediaPlayer.create(this, R.raw.test);
		player.setLooping(true);
		MyLog.d("MyService--onCreate");
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		MyLog.d("MyService--onStartCommand");
		player.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		MyLog.d("MyService--onBind");
		// TODO Auto-generated method stub
		player.start();
		return new MyBind();
	}
	@Override
	public void onDestroy() {
		MyLog.d("MyService--onDestroy");
		player.stop();
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		MyLog.d("MyService--onUnbind");
		return super.onUnbind(intent);
	}
	class MyBind extends Binder implements IService{
        public String getName() {
         // TODO Auto-generated method stub
         return "hello!!!"; 
         } 
      }  
}
interface IService {
    String getName();
}   
