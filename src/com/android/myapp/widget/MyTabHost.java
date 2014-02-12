package com.android.myapp.widget;

import com.android.myapp.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

public class MyTabHost extends TabHost{
	private LayoutInflater mInflater;
	private static final int TAB_COUNT = 3;
	private FragmentActivity mActivity;
	public MyTabHost(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		mInflater = LayoutInflater.from(context);
	}
	public void setActivity(FragmentActivity activity){
		mActivity = activity;
		for (int i = 0; i < TAB_COUNT; i++) {
			final MyViewPager pager = (MyViewPager)mInflater.inflate(R.layout.viewpager, getTabContentView(), false);
			pager.init(mActivity.getSupportFragmentManager());
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
	protected void onFinishInflate() {
		super.onFinishInflate();
		setup();
		
	}

	@Override
	public void setCurrentTab(int index) {
		// TODO Auto-generated method stub
		super.setCurrentTab(index);
	}

}
