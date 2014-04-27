package com.gimpel.android2048;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class GameBoardFragment extends Fragment implements onDirectionSwype {
	// TODO three hours of sleep, two hot meals, one shower
	private Grid mGrid;
	private TextView mTextScore;
	long startTime = System.currentTimeMillis();
	
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
		TextView scoreView = (TextView) getView().findViewById(R.id.score_value);
		scoreView.setText(mGrid.getScore());
	}
	
	private void refreshGameBoard() {
		int newElement = mGrid.addRandom();
		
		if (newElement != -1) { // we can still play!
			RelativeLayout parent = (RelativeLayout) getView().findViewById(R.id.grid);
			for (int i = 0; i < parent.getChildCount(); i++) {	
				// retrieve relative layout that represents each grid element and holds textview
				// FrameLayout is placeholder for empty grid
				RelativeLayout element = (RelativeLayout) ((FrameLayout) parent.getChildAt(i)).getChildAt(0);
				// update its background and displayed value
				Util.updateElement(element, mGrid.getElementAt(i), getActivity());
				
				if (i == newElement) {
					Animation fadeIn = new AlphaAnimation(0, 1);
					fadeIn.setInterpolator(new AccelerateInterpolator()); //and this
					fadeIn.setDuration(10);
					element.startAnimation(fadeIn);
				}
			}	
		} else { // game is lost!
//			FireMissilessDialogFragment dialog = new FireMissilessDialogFragment();
//			dialog.show(getFragmentManager(), "TAG");
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
			Log.d("SHIT","RIGHT");
			break;
		case DOWN:
			mGrid.moveDown();
			Log.d("SHIT","DOWN");
			break;
		case LEFT:
			mGrid.moveLeft();
			Log.d("SHIT","LEFT");
			break;
		case RIGHT:
			mGrid.moveRight();
			Log.d("SHIT","RIGHT");
			break;
		}
		
		animateView();
	}
}
