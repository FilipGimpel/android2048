package com.gimpel.android2048;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gimpel.android2048.database.SavedGame;
import com.gimpel.android2048.database.SavedGamesManager;


public class GameBoardFragment extends Fragment implements FragmentCallback {
	private Grid mGrid;
	private TextView mTextScore;
	private TextView mTextTime;
	private Handler mHandler = new Handler();
	private SavedGamesManager mGamesManager;
	// TODO three hours of sleep, two hot meals, one shower
	
	public GameBoardFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_game, container,
				false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mTextScore = (TextView) getView().findViewById(R.id.score_value);
		mTextTime = (TextView) getView().findViewById(R.id.time_value);
		mHandler.post(new UpdateScoreRunnable(mTextTime, mHandler));
		mGamesManager = new SavedGamesManager(getActivity());
		
		Util.adjustGridSize(view);
		
		mGrid = new Grid();
		mGrid.addRandom();
		mGrid.addRandom();
		refreshGameBoard();
	}
	
	private void animateView() {
		updateScore();
		refreshGameBoard();
	}
	
	private void updateScore() {
		mTextScore.setText(String.valueOf(mGrid.getScore()));
	}
	
	public SavedGame getSavedGame() {
		// Load bitmap from view and save it to internal storage
		RelativeLayout grid = (RelativeLayout) getView().findViewById(R.id.grid);
		Bitmap b = Util.loadBitmapFromView(grid);
		String uri = Util.saveBitmap(b, getActivity());
		
		SavedGame game = new SavedGame();
		game.setGameState(mGrid.getGameState());
		game.setScore(mGrid.getScore());
		game.setUriToImage(uri);
		
		return game;
	}
	
	private void refreshGameBoard() {
		int newElement = mGrid.addRandom();
		
		if (newElement != -1) {
			RelativeLayout parent = (RelativeLayout) getView().findViewById(R.id.grid);
			for (int i = 0; i < parent.getChildCount(); i++) {	
				// retrieve relative layout that represents each grid element and holds textview
				// FrameLayout is placeholder for empty grid
				RelativeLayout element = (RelativeLayout) ((FrameLayout) parent.getChildAt(i)).getChildAt(0);
				// update its background and displayed value
				Util.updateElement(element, mGrid.getElementAt(i), getActivity());
				
				if (i == newElement) {
					Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.add_new_element); 
					//fadeIn.setInterpolator(new AccelerateInterpolator());
					element.startAnimation(fadeIn);
				}
			}
		} else if (mGrid.isGameLost()) {
			

			
			// TODO add popup! about losing
			// TODO add score counter
			// TODO integrate with facebook
			// TODO three dimensional elements
		}
	}
	
	@Override
	public void onSwype(Direction direction) {
		switch(direction) {
		case UP:
			mGrid.moveUp();
			break;
		case DOWN:
			mGrid.moveDown();
			break;
		case LEFT:
			mGrid.moveLeft();
			break;
		case RIGHT:
			mGrid.moveRight();
			break;
		}
		
		animateView();
	}

	@Override
	public void onBackKeyPressed() {
		
		RelativeLayout grid = (RelativeLayout) getView().findViewById(R.id.grid);
		
		if (!mGrid.isGameLost()) {
			SaveGameDialog dialog = new SaveGameDialog();
			dialog.show(getFragmentManager(), "SAVEGAME?");		
		}

	}
}
