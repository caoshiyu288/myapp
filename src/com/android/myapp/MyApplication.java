package com.android.myapp;


import com.android.myapp.network.RequestManager;
import com.android.myapp.utils.ImageCacheManager;
import com.android.myapp.utils.ImageCacheManager.CacheType;

import android.app.Application;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;

public class MyApplication extends Application{
	private static int DISK_IMAGECACHE_SIZE = 1024*1024*512;//512M
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		RequestManager.init(this);
		// init volley image cache 
		ImageCacheManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, CacheType.DISK);
        // Register for changes to the favorites
        ContentResolver resolver = getContentResolver();
        resolver.registerContentObserver(MyAppSettings.DbParameter.CONTENT_URI, true,
        		mDbObserver);
        
	}

	@Override
	public void onTerminate() {
		ContentResolver resolver = getContentResolver();
		resolver.unregisterContentObserver(mDbObserver);
		super.onTerminate();
	}
	
    private final ContentObserver mDbObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
        	//...
        }
    };
}
