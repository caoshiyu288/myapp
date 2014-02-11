package com.android.myapp;

import com.android.myapp.db.MyDbHelper;
import com.android.myapp.jni.MyJni;
import com.android.myapp.network.MyWebViewActivity;
import com.android.myapp.utils.MyLog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MyLogin extends Activity {
	private TextView mTitle;
	private EditText mUser;
	private EditText mPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setupViews();
	}
	private void setupViews(){
		mTitle = (TextView)findViewById(R.id.textView1);
		MyJni jni = new MyJni();
		mTitle.setText(jni.plus(1, 2)+"");
		mUser = (EditText) findViewById(R.id.user);
		mPassword = (EditText) findViewById(R.id.password);
		
		Button register = (Button)findViewById(R.id.button2);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MyLogin.this, MyRegister.class);
				startActivity(intent);
			}
		});
		
		Button login = (Button)findViewById(R.id.button1);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkLogin();
			}
		});
		
	}
	private void checkLogin(){
		//query from db
		new Thread(new Runnable() {
			@Override
			public void run() {
				String user = mUser.getText().toString();
				String password = mPassword.getText().toString();
				Cursor c = null;
				boolean exit = false;
				try {
					MyDbHelper dbHelper = MyDbHelper.getInstance(getApplicationContext());
					c = dbHelper.query(MyDbHelper.TABLE_NAME, new String[]{MyAppSettings.DbParameter.PASSWORD},
							MyAppSettings.DbParameter.USER+"=?", new String[]{user}, null);
					while (c.moveToNext()) {
						String passwordInDb = c.getString(c.getColumnIndexOrThrow(MyAppSettings.DbParameter.PASSWORD));
						if(passwordInDb != null && passwordInDb.equals(password)){
							exit = true;
							//find the data
							Message msg = mHandler.obtainMessage();
							msg.what = 0;
							Bundle bd = new Bundle();
							bd.putString("user", user);
							msg.setData(bd);
							mHandler.sendMessage(msg);
							break;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					exit = false;
				} finally{
					if(c != null)
						c.close();
					if(!exit){
						Message msg = mHandler.obtainMessage();
						msg.what = 1;
						mHandler.sendMessage(msg);
					}
				}
			}
		}).start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0://success
				MyLog.d("start main !!!!!!!");
				Intent intent = new Intent();
				intent.putExtra("user", msg.getData().getString("user"));
				intent.setClass(MyLogin.this, MyMain.class);
				startActivity(intent);
				break;
			case 1://fail
				Toast.makeText(MyLogin.this, "user or password error!", Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
	};
}
