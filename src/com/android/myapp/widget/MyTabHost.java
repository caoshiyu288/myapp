package com.android.myapp.widget;

import java.util.HashMap;

import com.android.myapp.R;
import com.android.myapp.utils.MyLog;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MyTabHost extends TabHost{
	private LayoutInflater mInflater;
	private static final int TAB_COUNT = 3;
	private FragmentActivity mActivity;
	private FrameLayout mContentView;
	private HashMap<Integer, TabContentFactory> mFactorys = new HashMap<Integer, TabHost.TabContentFactory>();
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
		final MyPager pager = (MyPager)mInflater.inflate(R.layout.viewpager, null);
		pager.init(mActivity.getSupportFragmentManager());
		TabContentFactory factory0 = new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return pager;
			}
		};
		mFactorys.put(0, factory0);
		TabContentFactory factory1 = new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				TextView v = new TextView(getContext());
				v.setText("tab 2");
				return v;
			}
		};
		mFactorys.put(1, factory1);
		TabContentFactory factory2 = new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				TextView v = new TextView(getContext());
				v.setText("tab 3");
				return v;
			}
		};
		mFactorys.put(2, factory2);
		TabContentFactory[] factory;
		for (int i = 0; i < mFactorys.size(); i++) {
			addTab(newTabSpec("tab" + i).setIndicator("tab " + i).setContent(mFactorys.get(i)));
		}
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setup();
		mContentView = getTabContentView();
	}

	@Override
	public void setCurrentTab(int index) {
		// TODO Auto-generated method stub
		super.setCurrentTab(index);
	}

}
