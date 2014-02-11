package com.android.myapp.jni;

/*
 * javah -classpath . -jni com.android.myapp.jni.MyJni
 * */
public class MyJni {
	static{
		System.loadLibrary("my_jni");
	}
	public MyJni() {
	}
	public native void write();
	public native String sayHello();
	public native int plus(int a, int b);
}
