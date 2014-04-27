package com.gimpel.android2048;

import android.os.Handler;
import android.widget.TextView;

public class UpdateScoreRunnable implements Runnable {
	private TextView mScoreTextView;
	private long mStartTime;
	private Handler mHandler;
	
	public UpdateScoreRunnable(TextView scoreTextView, Handler handler) {
		mScoreTextView = scoreTextView;
		mHandler = handler;
		mStartTime = System.currentTimeMillis();
	}
	
	@Override
	public void run() {
		long delay = System.currentTimeMillis() - mStartTime;
	}

}
