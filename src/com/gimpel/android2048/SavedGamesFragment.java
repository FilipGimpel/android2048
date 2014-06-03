package com.gimpel.android2048;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.gimpel.android2048.database.SavedGame;
import com.gimpel.android2048.database.SavedGamesManager;

public class SavedGamesFragment extends Fragment {
	private SavedGamesManager mSavedGamesManager;
	private SavedGamesAdapter mAdapter;
	private List<SavedGame> mGamesList;
	private ListView mListView;
	private SavedGame mLastRemovedGame;
	private boolean shouldRestoreOnBack = false;
	private int selectedRow;
	public static final String SAVED_GAME_INTENT_TAG = "SAVED_GAME_STATE";
	public static final String SCORE_INTENT_TAG = "SCORE";
	public static final String ELAPSED_TIME_INTENT_TAG = "ELAPSED_TIME";
	
	public SavedGamesFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_saved_games, container,
				false);
		return rootView;
	}
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mSavedGamesManager = new SavedGamesManager(getActivity());
		mGamesList = mSavedGamesManager.getAllSavedGames();
		mAdapter = new SavedGamesAdapter(getActivity(), mGamesList);
		
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SavedGame game = mGamesList.get(position);
				String savedGameState = game.getGameState();
				
				Intent k = new Intent(getActivity(), GameBoardActivity.class);
				k.putExtra(SAVED_GAME_INTENT_TAG, savedGameState);
				k.putExtra(SCORE_INTENT_TAG, String.valueOf(game.getScore()));
				k.putExtra(ELAPSED_TIME_INTENT_TAG, game.getTime());
			    startActivity(k);
			    getActivity().overridePendingTransition( R.anim.right_in, R.anim.left_out );
			}
		});
        
        // Animation for deleting saved game on LongClick 
        final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        animation.setAnimationListener(new OnRemoveAnimationListener());
        
        // Long press initializes animation and passes removed element
        // index to AnimationEnd listener, in order to remove it from
        // list. On Animation end we update layout to reflect this deletion.
        mListView.setOnItemLongClickListener(new OnItemLongClickRemove(animation));
	}
	
	private class OnItemLongClickRemove implements OnItemLongClickListener {
		private Animation mRemoveItemAnimation;
		
		public OnItemLongClickRemove(Animation animation) {
			mRemoveItemAnimation = animation;
		}
		
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
					view.startAnimation(mRemoveItemAnimation);
					selectedRow = position;
			return false;
		}		
	}
	
	/**
	 * When user clicks back to undo removal of item this method is fired
	 */
	public void restoreLastItemRemoved() {
		mGamesList.add(mLastRemovedGame);
		mAdapter.notifyDataSetChanged();
	}
	
	public boolean shouldRestoreLastItem() {
		return shouldRestoreOnBack;
	}
	
	private class OnRemoveAnimationListener implements Animation.AnimationListener {
		private static final int TIME_TO_REMOVE = 5000;
		private static final String UNDO_REMOVE_MESSAGE = 
				"Saved game deleted. Press back back to undo";
		
		@Override
		public void onAnimationStart(Animation animation) {
			/* Nothing to do */
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			mLastRemovedGame = mGamesList.get(selectedRow);
			mGamesList.remove(mLastRemovedGame);
            mAdapter.notifyDataSetChanged();
            
            Toast.makeText(getActivity(), 
                    UNDO_REMOVE_MESSAGE, Toast.LENGTH_LONG).show();
            
            shouldRestoreOnBack = true;
            new Handler().postDelayed(new Runnable() {

    	        @Override
    	        public void run() {
    	            shouldRestoreOnBack = false;                     
    	        }
    	    }, TIME_TO_REMOVE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			/* Nothing to do */
			
		}
	}
}
