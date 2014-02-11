package com.android.myapp.network;

import com.android.myapp.R;
import com.android.myapp.utils.MyLog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("JavascriptInterface")
public class MyWebViewActivity extends Activity{
	private WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		mWebView = (WebView)findViewById(R.id.webView1);
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				MyLog.d("shouldOverrideUrlLoading,url:"+url);
				view.loadUrl(url);
				return true;
			}
			
		});
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
//		mWebView.loadUrl("http://www.baidu.com");
		mWebView.addJavascriptInterface(new Object(){
			public void clickOnAndroid(){
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						MyLog.d("clickOnAndroid>>>>>");
						mWebView.loadUrl("javascript:wave()");
					}
				});
			}
		}, "demo");
		mWebView.loadUrl("file:///android_asset/demo.html");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
