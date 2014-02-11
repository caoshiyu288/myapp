package com.android.myapp;

import com.android.myapp.db.MyDbHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyRegister extends Activity{
	private EditText mUser;
	private EditText mPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		mUser = (EditText) findViewById(R.id.editText1);
		mPassword = (EditText) findViewById(R.id.editText2);
		Button register = (Button)findViewById(R.id.button1);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});
	}
	private void submit(){
		//insert date to db
		new Thread(new Runnable() {
			@Override
			public void run() {
				MyApp app = new MyApp();
				app.user = mUser.getText().toString();
				app.password = mPassword.getText().toString();
				MyDbHelper dbHelper = MyDbHelper.getInstance(getApplicationContext());
				try {
					dbHelper.insert(MyDbHelper.TABLE_NAME, null, app.toContentValues());
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					dbHelper.close();
					dbHelper = null;
				}
			}
		}).start();
		//finish self
		finish();
	}
}
