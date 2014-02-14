package com.android.myapp.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.myapp.MyApp;
import com.android.myapp.R;
import com.android.myapp.utils.Constants;
import com.android.myapp.utils.ImageCacheManager;
import com.android.myapp.utils.MyLog;
import com.android.volley.toolbox.NetworkImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyPager extends FrameLayout {
	private ArrayList<MyApp> mApps = new ArrayList<MyApp>();
	private HashMap<Integer, Fragment> mFragmentMap = new HashMap<Integer, Fragment>();
	private ViewPager mViewPager;
	public MyPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		MyLog.d("MyPager structing~~~~~~~~~~");
	}

	public MyPager(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public MyPager(Context context) {
		this(context,null,0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mViewPager = (ViewPager)findViewById(R.id.pager); 
	}

	public void init(FragmentManager fm){
		//init
		String[] imgs = Constants.IMAGES;
		for (int i = 0; i < imgs.length; i++) {
			MyApp app = new MyApp();
			app.iconUrl = imgs[i];
			app.name = "item "+i;
			mApps.add(app);
		}
		MyviewPagerAdapter adapter = new MyviewPagerAdapter(fm, 2);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
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
		adapter.notifyDataSetChanged();
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

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return "title "+position;
		}
		
	}
	
	private Fragment createFragment(int id){
		MyLog.d("createFragment==="+id+",total="+mFragmentMap.size());
		Fragment fragment = mFragmentMap.get(id);
		if(fragment == null){
			fragment = new MyListFragment();
			Bundle bd = new Bundle();
			bd.putInt("listId", id);
			fragment.setArguments(bd);
			mFragmentMap.put(id, fragment);
		}
		return fragment;
	}
	
	@SuppressLint("ValidFragment")
	class MyListFragment extends ListFragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.list, container, false);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			MyLog.d("onActivityCreated==="+mApps.size());
			setListAdapter(new MyAdapter(getActivity(), mApps));
//			setListAdapter(new MyListAdapter(getActivity(), android.R.layout.simple_list_item_1,mApps));
			
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Toast.makeText(getActivity(), "Item clicked: " + id, 200).show();
		}
		
	}
	class MyAdapter extends BaseAdapter{
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
			MyLog.d("position==="+position);
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

		class ViewHolder {
			public TextView text;
			public NetworkImageView image;
		}
	}
}
