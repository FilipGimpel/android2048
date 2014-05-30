package com.gimpel.android2048;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SavedGamesActivity extends FragmentActivity {
	private SavedGamesFragment mSavedGamesFragment; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_games);
		mSavedGamesFragment = new SavedGamesFragment();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, mSavedGamesFragment).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_saved_games,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onBackPressed() {
	    if (!mSavedGamesFragment.shouldRestoreLastItem()) {
	        super.onBackPressed();
	        overridePendingTransition( R.anim.left_in, R.anim.right_out );
	        return;
	    }
	    
	    mSavedGamesFragment.restoreLastItemRemoved();
	} 
}
