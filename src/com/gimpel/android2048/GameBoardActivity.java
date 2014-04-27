package com.gimpel.android2048;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.gimpel.android2048.onDirectionSwype.Direction;

public class GameBoardActivity extends ActionBarActivity {
	// Callback to GameBoard fragment for passing swype events
	private onDirectionSwype mOnSwypeCallback;
	
	// Fields necessary for handling touch events
	int mStartTouchEventX;
	int mStartTouchEventY;
	private int mMinimalTouchEventLenght;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		// Initialize gameBoardFragment and register callback 
		GameBoardFragment gameBoard = new GameBoardFragment();
		mOnSwypeCallback = gameBoard;
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, gameBoard).commit();
		}

		mMinimalTouchEventLenght = Util.getMinimalTouchEventLenght(this);
	}
	

	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = MotionEventCompat.getActionMasked(ev);
        
	    switch(action) {
	        case (MotionEvent.ACTION_DOWN) :
	        	mStartTouchEventX = (int) ev.getX();
	        	mStartTouchEventY = (int) ev.getY();
	        	
	        	Log.d("GiorgioTouchEvent", String.format("minimal width! =%d",
	        			mMinimalTouchEventLenght));
        	
	            return true;
	        case (MotionEvent.ACTION_UP) :
		        int deltaX = (int) (mStartTouchEventX - ev.getX());
	        	int deltaY = (int) (mStartTouchEventY - ev.getY());
	        	
	        	if (Math.abs(deltaX) > Math.abs(deltaY)) {
	        		if (Math.abs(deltaX) > mMinimalTouchEventLenght) {	        			
	        			if (deltaX > 0){
	        				mOnSwypeCallback.onSwype(Direction.LEFT);
	        			} else {
	        				mOnSwypeCallback.onSwype(Direction.RIGHT);
	        			}
	        		}
	        	} else if (Math.abs(deltaX) < Math.abs(deltaY)) {
	        		if (Math.abs(deltaY) > mMinimalTouchEventLenght) {
		        		if (deltaY > 0){
		        			mOnSwypeCallback.onSwype(Direction.UP);
		        		} else {
		        			mOnSwypeCallback.onSwype(Direction.DOWN);
		        		}
	        		}
	        	}
	        	
	            return true;    
	        default : 
	            return super.onTouchEvent(ev);
	    }
	    
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
