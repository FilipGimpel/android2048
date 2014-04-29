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
		
		// could't use TimeUnit class because of low api 
		int miliseconds = (int) delay % 1000;
		int seconds = (int) (delay / 1000) % 60 ;
		int minutes = (int) ((delay / (1000*60)) % 60);
		
		// %02d - pad number d to 2 digits with 0
		String timeString = String.format("%02d:%02d:%03d", 
			minutes,
		    seconds,
		    miliseconds
		);
		

		mScoreTextView.setText(timeString);
		mHandler.postDelayed(this, 30);
	}

}
