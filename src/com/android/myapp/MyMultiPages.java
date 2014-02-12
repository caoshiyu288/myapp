package com.android.myapp;

import com.android.myapp.widget.MyTabHost;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ShareActionProvider;

public class MyMultiPages extends FragmentActivity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.tabhost);
		
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
//		bar.setDisplayShowTitleEnabled(true);
		
		MyTabHost tabHost = (MyTabHost) findViewById(R.id.tabhost);
		tabHost.setActivity(this);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.actionbar_menu, menu);
		MenuItem shareMenu_1 =  menu.findItem(R.id.share_1);
		ShareActionProvider acitonProvider_1 = (ShareActionProvider)shareMenu_1.getActionProvider();
		acitonProvider_1.setShareIntent(createShareIntent());
		
		MenuItem shareMenu_2 =  menu.findItem(R.id.share_2);
		ShareActionProvider acitonProvider_2 = (ShareActionProvider)shareMenu_1.getActionProvider();
		acitonProvider_2.setShareIntent(createShareIntent());
		
		return true;
	}
	private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return shareIntent;
    }
}
