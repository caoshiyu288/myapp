package com.android.myapp.widget;

import com.android.myapp.R;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

public class MyTabHost extends TabHost{
	private LayoutInflater mInflater;
	private static final int TAB_COUNT = 3;
	public MyTabHost(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setup();
		
		for (int i = 0; i < TAB_COUNT; i++) {
			final ViewPager pager = (ViewPager)mInflater.inflate(R.layout.viewpager, null);
			TabContentFactory factory = new TabContentFactory() {
				@Override
				public View createTabContent(String tag) {
					return pager;
				}
			};
			addTab(newTabSpec("tab" + i).setIndicator("tab " + i).setContent(factory));
		}
		
	}

	@Override
	public void setCurrentTab(int index) {
		// TODO Auto-generated method stub
		super.setCurrentTab(index);
	}

}
