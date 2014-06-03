package com.gimpel.android2048;

import android.os.Handler;
import android.widget.TextView;

public class UpdateScoreRunnable implements Runnable {
	private TextView mScoreTextView;
	private long mStartTime;
	private Handler mHandler;
	private long mCurrentMilis;
	
	public UpdateScoreRunnable(TextView scoreTextView, Handler handler) {
		mScoreTextView = scoreTextView;
		mHandler = handler;
		mStartTime = System.currentTimeMillis();
	}
	
	public UpdateScoreRunnable(TextView scoreTextView, Handler handler, long elapsedTime) {
		this(scoreTextView, handler);
		mStartTime = System.currentTimeMillis() - elapsedTime;
	}
	
	@Override
	public void run() {
		mCurrentMilis = System.currentTimeMillis() - mStartTime;
		
		// could't use TimeUnit class because of low api 
		int miliseconds = (int) mCurrentMilis % 1000;
		int seconds = (int) (mCurrentMilis / 1000) % 60 ;
		int minutes = (int) ((mCurrentMilis / (1000*60)) % 60);
		
		// %02d - pad number d to 2 digits with 0
		String timeString = String.format("%02d:%02d:%03d", 
			minutes,
		    seconds,
		    miliseconds
		);
		

		mScoreTextView.setText(timeString);
		mHandler.postDelayed(this, 30);
	}

	public long getMilis() {
		return mCurrentMilis;
	}
	
}
