package com.android.myapp.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.myapp.MyApp;
import com.android.myapp.R;
import com.android.myapp.utils.Constants;
import com.android.myapp.utils.ImageCacheManager;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.annotation.SuppressLint;
import android.app.ApplicationErrorReport.CrashInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class MyViewPager extends ViewPager{
	private ArrayList<MyApp> mApps = new ArrayList<MyApp>();
	private HashMap<Integer, Fragment> mFragmentMap = new HashMap<Integer, Fragment>();
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		//init
		String[] imgs = Constants.IMAGES;
		for (int i = 0; i < imgs.length; i++) {
			MyApp app = new MyApp();
			app.iconUrl = imgs[i];
			app.name = "item "+i;
			mApps.add(app);
		}
	}

	public MyViewPager(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	public void init(FragmentManager fm){
		setAdapter(new MyviewPagerAdapter(fm, 2));
		setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class MyviewPagerAdapter extends FragmentPagerAdapter{
		private int mCount;
		public MyviewPagerAdapter(FragmentManager fm, int count) {
			super(fm);
			mCount = count;
		}

		@Override
		public Fragment getItem(int arg0) {
			return createFragment(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCount;
		}
		
	}
	
	private Fragment createFragment(int id){
		Fragment fragment = mFragmentMap.get(id);
		if(fragment == null){
			fragment = new MyListFragment();
			mFragmentMap.put(id, fragment);
		}
		return fragment;
	}
	
	class MyListFragment extends ListFragment{

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			setListAdapter(new MyAdapter(getActivity(), mApps));
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Toast.makeText(getActivity(), "Item clicked: " + id, 200);
		}
		
	}
	
	static class MyAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		private ArrayList<MyApp> mList;
		public MyAdapter(Context context, ArrayList<MyApp> list) {
			super();
			mInflater = LayoutInflater.from(context);
			mList = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyApp app = mList.get(position);
			View v = convertView;
			final ViewHolder holder;
			if(v == null){
				v = mInflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder();
				holder.text = (TextView)v.findViewById(R.id.textView1);
				holder.image = (NetworkImageView)v.findViewById(R.id.imageView1);
				v.setTag(holder);
			}else{
				holder = (ViewHolder)v.getTag();
			}
			holder.text.setText(app.name);
			holder.image.setImageUrl(app.iconUrl, ImageCacheManager.getInstance().getImageLoader());
			return v;
		}

		static  class ViewHolder {
			public TextView text;
			public NetworkImageView image;
		}
	}
	
}
