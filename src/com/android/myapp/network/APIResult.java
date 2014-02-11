package com.android.myapp.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;



public class APIResult {
	public static final int OK = 0;
	public static final int ERROR_CODE_NETWORK_ERR = -10001; //网络
	public static final int ERROR_CODE_DATA_ERR = -10002; //数据格式
	
//	public int result;
//	public String msg;
//	
//	
//	public boolean isOK(){
//		return result == 0;
//	}
	
	public static boolean isApiOK(JSONObject jsonObject, Context context, String url) {
		int result = ERROR_CODE_NETWORK_ERR;
		String msg = null;
		try{
			result =  jsonObject.getInt("result");
			if(!jsonObject.isNull("msg")){
				msg = jsonObject.getString("msg");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			msg = "Json parse failure";
			result = ERROR_CODE_DATA_ERR;
		}
		
		if(result != OK && context != null){
			return false;
		}
		return true;
	}
	
	public static boolean isApiOK(JSONObject jsonObject) {
		return isApiOK(jsonObject, null, null);
	}
	
}
