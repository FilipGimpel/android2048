package com.gimpel.android2048;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Util {
	private static int sScreenWidth;

	public static int getColorForTileValue(int value, Context context) {
		int resourceId = 0;

		switch(value) {
		case 0:
			resourceId = R.color.color_0;
			break;
		case 2:
			resourceId = R.color.color_2;
			break;
		case 4:
			resourceId = R.color.color_4;
			break;
		case 8:
			resourceId = R.color.color_8;
			break;
		case 16:
			resourceId = R.color.color_16;
			break;
		case 32:
			resourceId = R.color.color_32;
			break;
		case 64:
			resourceId = R.color.color_64;
			break;
		case 128:
			resourceId = R.color.color_128;
			break;
		case 256:
			resourceId = R.color.color_256;
			break;
		case 512:
			resourceId = R.color.color_512;
			break;
		case 1024:
			resourceId = R.color.color_1024;
			break;
		case 2048:
			resourceId = R.color.color_2048;
			break;
		}

		String string_value = context.getResources().getString(resourceId);
		return Color.parseColor(string_value);
	}

	public static GradientDrawable getDrawableForValue(int value, Context context) {
		GradientDrawable drawable = (GradientDrawable) context.
				getResources().getDrawable(R.drawable.tile_backgroudn);

		int color = getColorForTileValue(value, context);
		drawable.setColor(color);

		return drawable;
	}


	public static String saveBitmap(Bitmap bitmapImage, Context context) {
		String filename = String.format("%s.%s",
				String.valueOf(System.currentTimeMillis()/1000),
				"jpeg");

		ContextWrapper cw = new ContextWrapper(context);
		// path to /data/data/android2048/app_data/imageDir
		File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
		// Create imageDir
		File mypath=new File(directory,filename);

		FileOutputStream fos = null;
		try {           
			fos = new FileOutputStream(mypath);
			// Use the compress method on the bitmap object to write image to the OutputStream
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 80, fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mypath.getAbsolutePath();
	}


	/**
	 * Get screen width and set mMinimalTouchEventLenght to 1/7 screen's width
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int getMinimalTouchEventLenght(Activity activity) {
		WindowManager w = activity.getWindowManager();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			w.getDefaultDisplay().getSize(size);
			sScreenWidth = size.x;
		} else {
			Display d = w.getDefaultDisplay();
			sScreenWidth = d.getWidth(); 
		}

		return sScreenWidth/7;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void updateElement(RelativeLayout element,
			int element_value, Activity activity) {

		// set element background
		GradientDrawable element_drawable = Util.getDrawableForValue(element_value, activity);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
			element.setBackgroundDrawable(element_drawable);
		} else {
			element.setBackground(element_drawable);
		}

		// set element value
		TextView element_text = (TextView) element.getChildAt(0);
		element_text.setText(String.valueOf(element_value));
	}

	/**
	 * This method dynamically adjusts grid size to fit screen width,
	 * maintaining square shape
	 */
	public static void adjustGridSize(View view) {
		int padding = (int) (sScreenWidth * (5.0/100.0));
		view.setPadding(padding, padding, padding, padding);

		RelativeLayout grid = (RelativeLayout) view.findViewById(R.id.grid);
		padding = (int) (sScreenWidth * (2.0/100.0));
		grid.setPadding(padding, padding, padding, padding);

		for (int i = 0; i < grid.getChildCount(); i++) {
			FrameLayout slot = (FrameLayout) grid.getChildAt(i);

			/* <view padding> [grid padding] {slot width}  (slot margin right) <view padding> */
			/* <5%> + [2%] + {20%} + (2%) + {20%} + (2%) + {20%} + (2%) + {20%} + [2%] + <5%> = 100% */  
			LayoutParams params = (LayoutParams) slot.getLayoutParams();
			params.width =  (int) (sScreenWidth * (20.0/100.0));
			params.height =  (int) (sScreenWidth * (20.0/100.0));

			if ((i+1)%4 != 0) params.rightMargin = (int) (sScreenWidth * (2.0/100.0));			
			if (i/12 == 0) params.bottomMargin = (int) (sScreenWidth * (2.0/100.0));

			slot.setLayoutParams(params);
		}
	}

	public static Bitmap loadBitmapFromView(View v) {
		Bitmap b = Bitmap.createBitmap(
				687, 687,
				//v.getLayoutParams().width,
				//v.getLayoutParams().height,
				Bitmap.Config.ARGB_8888);

		Canvas c = new Canvas(b);
		v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
		v.draw(c);
		return b;
	}
}
