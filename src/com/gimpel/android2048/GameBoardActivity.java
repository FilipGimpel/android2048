package com.gimpel.android2048;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.gimpel.android2048.FragmentCallback.Direction;
import com.gimpel.android2048.SaveGameDetailDialog.SaveGameDetailDialogListener;
import com.gimpel.android2048.SaveGameDialog.SaveGameDialogListener;
import com.gimpel.android2048.database.Game;
import com.gimpel.android2048.database.DatabaseHelper;
import com.gimpel.android2048.dialogs.GameOverDialog.GameOverDialogListener;

public class GameBoardActivity extends FragmentActivity implements SaveGameDialogListener, SaveGameDetailDialogListener, GameOverDialogListener {
	// Callback to GameBoard fragment for passing swype events
	private FragmentCallback mFragmentCallback;

	// Fields necessary for handling touch events
	private int mStartTouchEventX;
	private int mStartTouchEventY;
	private int mMinimalTouchEventLenght;
	
	// if current game was restored from database
	private Game mLoadedGame;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		// Initialize gameBoardFragment and register callback 
		GameBoardFragment gameBoard = new GameBoardFragment();
		mFragmentCallback = gameBoard;
		
		// Optionally pass argument to fragment (savedGameState)
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mLoadedGame = extras.getParcelable(SavedGamesFragment.LOADED_GAME_TAG);
		    gameBoard.setArguments(extras);
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, gameBoard).commit();
		}

		mMinimalTouchEventLenght = Util.getMinimalTouchEventLenght(this);
	}

	@Override
	public void onBackPressed() {
		mFragmentCallback.onBackKeyPressed();
	}

	//private static class

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
					mFragmentCallback.onSwype(Direction.LEFT);
				} else {
					mFragmentCallback.onSwype(Direction.RIGHT);
				}
			}
		} else if (Math.abs(deltaX) < Math.abs(deltaY)) {
			if (Math.abs(deltaY) > mMinimalTouchEventLenght) {
				if (deltaY > 0){
					mFragmentCallback.onSwype(Direction.UP);
				} else {
					mFragmentCallback.onSwype(Direction.DOWN);
				}
			}
		}

		return true;    
		default : 
			return super.onTouchEvent(ev);
		}

	}
	
	@Override
	public void onSaveGameDialogPositiveClick() {
		if (mLoadedGame != null) {
			DatabaseHelper manager = new DatabaseHelper(getApplicationContext());
			// delete old game state
			manager.deleteSavedGame(mLoadedGame);
			
			// update old game state and save to database
			Game game = ((GameBoardFragment)mFragmentCallback).getCurrentGame();
			game.setPlayerName(mLoadedGame.getPlayerName());
			manager.addSavedGame(game);
		
			// finish activity
			finish();
		    overridePendingTransition( R.anim.left_in, R.anim.right_out );
		} else {
			SaveGameDetailDialog dialog = new SaveGameDetailDialog();
			dialog.show(getSupportFragmentManager(), "SAVEGAMEDETAIL");	
		}
	}

	@Override
	public void onSaveGameDialogNegativeClick() {
		finish();
	    overridePendingTransition( R.anim.left_in, R.anim.right_out );
	}

	@Override
	public void onSaveGameDetailDialogPositiveClick(String userInput) {
		Game game = ((GameBoardFragment)mFragmentCallback).getCurrentGame();
		
		if (userInput.equals("")) { // if user supplied no name, we set it to default
			String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK).format(new Date());
			String defaultName = String.format("%s%s", date, game.getScore());
			game.setPlayerName(defaultName);
		} else {
			game.setPlayerName(userInput);	
		}
		
		game.setID(game.getPlayerName().hashCode());
		
		DatabaseHelper manager = new DatabaseHelper(this);
		manager.addSavedGame(game);
		finish();
		overridePendingTransition( R.anim.left_in, R.anim.right_out );
	}

	@Override
	public void onGameOverDismissClick(boolean isHigshScore) {
		if (isHigshScore) {
			((GameBoardFragment)mFragmentCallback).AddCurrentGameToHighScores();
		}
		
		finish();
		overridePendingTransition( R.anim.left_in, R.anim.right_out );
	}
}
