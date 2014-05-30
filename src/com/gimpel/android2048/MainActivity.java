package com.gimpel.android2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	public void goToNewGame(View v) {
		Intent k = new Intent(MainActivity.this, GameBoardActivity.class);
	    startActivity(k);
	    overridePendingTransition( R.anim.right_in, R.anim.left_out );
	}
	
	public void goToResumeGame(View v) {
		Intent k = new Intent(MainActivity.this, SavedGamesActivity.class);
	    startActivity(k);
	    overridePendingTransition( R.anim.right_in, R.anim.left_out );
	}
	
	public void goToScores(View v) {
//		Intent k = new Intent(MainActivity.this, GameBoardActivity.class);
//	    startActivity(k);
	}
}
