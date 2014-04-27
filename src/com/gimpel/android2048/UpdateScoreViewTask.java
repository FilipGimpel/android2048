package com.gimpel.android2048;

import java.util.TimerTask;

import android.widget.TextView;

public class UpdateScoreViewTask extends TimerTask {
	TextView mScoreView;
	long mCurrentTime = 0;
	
	public UpdateScoreViewTask(TextView scoreView) {
		mScoreView = scoreView;
	}
	
	@Override
	public void run() {
		mCurrentTime += 30;
		
		int miliseconds = (int) mCurrentTime % 1000;
		int seconds = (int) (mCurrentTime / 1000) % 60 ;
		int minutes = (int) ((mCurrentTime / (1000*60)) % 60);
		
		
		String timeString = String.format("%d:%d:%d", 
		    miliseconds,
		    seconds,
		    minutes
		);
		

		mScoreView.setText(timeString);
	}

}
