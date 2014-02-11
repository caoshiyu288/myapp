package com.android.myapp;

import java.util.ArrayList;
import java.util.List;

import com.android.myapp.db.MyDbHelper;
import com.android.myapp.db.SQLiteCursorLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class MyUsers extends Activity{
	private TextView mText;
	private LoaderManager mLoaderManager;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users);
		mText = (TextView) findViewById(R.id.textView1);
		mLoaderManager = getLoaderManager();
		mLoaderManager.initLoader(1000, null, callbacks);
	}
	@SuppressLint("NewApi")
	private LoaderManager.LoaderCallbacks<Cursor> callbacks = new LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        	
        	SQLiteCursorLoader loader=new SQLiteCursorLoader(MyUsers.this, MyDbHelper.getInstance(getApplicationContext())
                   ,"SELECT *  FROM myapp", null);         
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        	MyApp app = null;
            final ArrayList<MyApp> mMyAppList = new ArrayList<MyApp>();
            if (cursor==null){
            	return;
            }
            while (cursor.moveToNext()) {
            	app = new MyApp();
				app.outDb(cursor);
				mMyAppList.add(app);
            }
            //bind data
            bindData(mMyAppList);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
        
    };
    private void bindData(List<MyApp> apps){
    	final int size = apps.size();
    	StringBuffer sb = new StringBuffer();
    	sb.setLength(0);
    	for (int i = 0; i < size; i++) {
			sb.append(apps.get(i).user+" ");
		}
    	mText.setText(sb.toString());
    }
}
