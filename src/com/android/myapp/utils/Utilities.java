/*
 * caoshiyu add for some kit
 */

package com.android.myapp.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;
import android.view.View;

public final class Utilities {
	private static final String TAG = "Utilities";
	private static int sIconWidth = -1;
	private static int sIconHeight = -1;
	private static int sIconTextureWidth = -1;
	private static int sIconTextureHeight = -1;
	private static final Rect sOldBounds = new Rect();
	private static final Canvas sCanvas = new Canvas();
	private static final Matrix mReflectMatrix = new Matrix();
	private static final Paint mShaderPaint = new Paint();
	private static final float mReflectRate = 0.4f;
	static {
		sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));
		mShaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        mReflectMatrix.preScale(1, -1);
	}


	static Bitmap createIconBitmap(Bitmap icon, Context context, int size) {
		int textureWidth = sIconTextureWidth;
		int textureHeight = sIconTextureHeight;
		int sourceWidth = icon.getWidth();
		int sourceHeight = icon.getHeight();
		if (sourceWidth > textureWidth && sourceHeight > textureHeight) {
			// Icon is bigger than it should be; clip it (solves the GB->ICS
			// migration case)
			return Bitmap.createBitmap(icon, (sourceWidth - textureWidth) / 2, (sourceHeight - textureHeight) / 2, textureWidth, textureHeight);
		} else if (sourceWidth == textureWidth && sourceHeight == textureHeight) {
			// Icon is the right size, no need to change it
			return icon;
		} else {
			// Icon is too small, render to a larger bitmap
			final Resources resources = context.getResources();
			return createIconBitmap(new BitmapDrawable(resources, icon), context, size);
		}
	}

	/**
	 * Returns a bitmap suitable for the all apps view.
	 */
	public static Bitmap createIconBitmap(Drawable icon, Context context, int size) {
		synchronized (sCanvas) {
			if (sIconWidth == -1) {
				initStatics(context, size);
			}

			int width = sIconWidth;
			int height = sIconHeight;

			if (icon instanceof PaintDrawable) {
				PaintDrawable painter = (PaintDrawable) icon;
				painter.setIntrinsicWidth(width);
				painter.setIntrinsicHeight(height);
			} else if (icon instanceof BitmapDrawable) {
				// Ensure the bitmap has a density.
				BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
				Bitmap bitmap = bitmapDrawable.getBitmap();
				if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
					bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
				}
			}
			int sourceWidth = icon.getIntrinsicWidth();
			int sourceHeight = icon.getIntrinsicHeight();
			if (sourceWidth > 0 && sourceHeight > 0) {
				// There are intrinsic sizes.
				if (width < sourceWidth || height < sourceHeight) {
					// It's too big, scale it down.
					final float ratio = (float) sourceWidth / sourceHeight;
					if (sourceWidth > sourceHeight) {
						height = (int) (width / ratio);
					} else if (sourceHeight > sourceWidth) {
						width = (int) (height * ratio);
					}
				} else if (sourceWidth < width && sourceHeight < height) {
					// Don't scale up the icon
					width = sourceWidth;
					height = sourceHeight;
				}
			}
			// no intrinsic size --> use default size
			int textureWidth = sIconTextureWidth;
			int textureHeight = sIconTextureHeight;
			final Bitmap bitmap = Bitmap.createBitmap(textureWidth, textureHeight, Bitmap.Config.ARGB_8888);
			final Canvas canvas = sCanvas;
			canvas.setBitmap(bitmap);
			final int left = (textureWidth - width) / 2;
			final int top = (textureHeight - height) / 2;
			sOldBounds.set(icon.getBounds());
			icon.setBounds(left, top, left + width, top + height);
			icon.draw(canvas);
			icon.setBounds(sOldBounds);
			canvas.setBitmap(null);
			return bitmap;
		}
	}
	private static void initStatics(Context context, int size) {
		sIconWidth = sIconHeight = size;
		sIconTextureWidth = sIconTextureHeight = sIconWidth;
	}
	
	public static boolean isComponentEnabled(final Context context, final ComponentName cmpName) {
		final String pkgName = cmpName.getPackageName();
		final PackageManager pm = context.getPackageManager();
		// Check whether the package has been uninstalled.
		PackageInfo pInfo = null;
		try {
			pInfo = pm.getPackageInfo(pkgName, 0);
		} catch (NameNotFoundException e) {
			Log.d(TAG, "package name not found!!!");
		}
		if (pInfo == null) {
			return false;
		}
		final int pkgEnableState = pm.getApplicationEnabledSetting(pkgName);
		Log.d(TAG, "pkgEnableState enable state:"+pkgEnableState);
		if (pkgEnableState == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || pkgEnableState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			final int cmpEnableState = pm.getComponentEnabledSetting(cmpName);
			Log.d(TAG, "cmpEnableState enable state:"+cmpEnableState);
			if (cmpEnableState == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || cmpEnableState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
				return true;
			}
		}
		return false;
	}
	public static boolean isPackageEnable(Context context, String packageName){
		final String pkgName = packageName;
		final PackageManager pm = context.getPackageManager();
		// Check whether the package has been uninstalled.
		PackageInfo pInfo = null;
		try {
			pInfo = pm.getPackageInfo(pkgName, 0);
		} catch (NameNotFoundException e) {
			Log.d(TAG, "package name not found!!!");
		}
		if (pInfo == null) {
			return false;
		}
		return true;
	}
	public static Bitmap scaleBitmap(Bitmap bm, float sx, float sy) {
		if (sx == 1.0f && sy == 1.0f) {
			return bm;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);
		return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	}
	public static Bitmap createBitmapFromDrawable(Drawable icon){
		int sourceWidth = icon.getIntrinsicWidth();
		int sourceHeight = icon.getIntrinsicHeight();
		final Bitmap bitmap = Bitmap.createBitmap(sourceWidth, sourceHeight, Bitmap.Config.ARGB_8888);
		final Canvas canvas = sCanvas;
		canvas.setBitmap(bitmap);
		icon.setBounds(0, 0, sourceWidth, sourceHeight);
		icon.draw(canvas);
		canvas.setBitmap(null);
		return bitmap;
	}
	public static Bitmap getViewBitmap(View v){
		Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Config.ARGB_8888);
		Canvas sourceCanvas = new Canvas(b);
		v.draw(sourceCanvas);
		sourceCanvas.setBitmap(null);
		return b;
		
	}
	public static Bitmap getViewReflect(Bitmap b){
		return getViewReflect(b, b.getHeight());
	}
	public static Bitmap getViewReflect(Bitmap b, int customHeight){
		
		Bitmap reflectBp = Bitmap.createBitmap(b, 0, (int) (b.getHeight()-customHeight*mReflectRate), b.getWidth(),
				(int) (customHeight*mReflectRate), mReflectMatrix, false);
		//create a new canvas for drawing reflect img and gradient shader again
		Bitmap reflectAndShaderBp = Bitmap.createBitmap(reflectBp.getWidth(), reflectBp.getHeight(),
				Config.ARGB_8888);
		Canvas reflectCanvas = new Canvas(reflectAndShaderBp);
		reflectCanvas.drawBitmap(reflectBp, 0, 0, null);
		LinearGradient shader = new LinearGradient(0, 0, 0, 0
				+ reflectBp.getHeight(), 0x7dFFFFFF, 0x00ffffff,
				TileMode.CLAMP);
		mShaderPaint.setShader(shader);
		reflectCanvas.drawRect(0, 0, reflectBp.getWidth(), reflectBp.getHeight(), mShaderPaint);
		reflectCanvas.setBitmap(null);
		return reflectAndShaderBp;
	}
	public static Bitmap getViewWithReflect(Bitmap sourceBp, int reflectGap){
		return getViewWithReflect(sourceBp, reflectGap, sourceBp.getHeight());
	}
	public static Bitmap getViewWithReflect(Bitmap sourceBp, int reflectGap, int customHeight){
//		Bitmap sourceBp = getViewBitmap(v);
		Bitmap reflectBp = getViewReflect(sourceBp, customHeight);
		final int width = sourceBp.getWidth();
		final int height = sourceBp.getHeight() + reflectGap + reflectBp.getHeight();
		Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.drawBitmap(sourceBp, 0, 0, null);
		c.drawBitmap(reflectBp, 0, sourceBp.getHeight() + reflectGap, null);
		c.setBitmap(null);
		return b;
	}
	public static Intent getIntent(PackageManager pm, String packageName){
		Intent intent =  new Intent(Intent.ACTION_MAIN, null);
		intent.setPackage(packageName);
//		List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent, 0);
		ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
		if(resolveInfo != null){
			ComponentName component = new ComponentName(resolveInfo.activityInfo.packageName,
					resolveInfo.activityInfo.name);
			intent.setComponent(component);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		}
		return intent;
	}
	public static Intent getIntent(ComponentName component, boolean isNewTask){
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setComponent(component);
		if(isNewTask){
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		}
		return intent;
	}
}
