package com.gimpel.android2048.dialogs;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class GameOverDialog extends DialogFragment {
	private GameOverDialogListener mListener;
	private int mScore;
	private boolean isHighScore;
	public static final String BUNDLE_SCORE_TAG = "SCORE_EARNED";
	public static final String BUNDLE_HIGHSCORE_TAG = "IS_HIGHSCORE";
	// interface to deliver action events
	public interface GameOverDialogListener {
		public void onGameOverDismissClick(boolean wasHighScore);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mScore = getArguments().getInt(BUNDLE_SCORE_TAG);
		isHighScore = getArguments().getBoolean(BUNDLE_HIGHSCORE_TAG);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (GameOverDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		String text = String.format(Locale.UK, "You have earned %d points!", mScore);

		builder.setTitle("GAME OVER!")
			   .setMessage(text)
			   .setPositiveButton(/*R.string.signin*/"OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
//						mListener.onGameOverDismissClick(isHighScore);
						dialog.dismiss();
					}
			   	});

		return builder.create();
	}


	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		mListener.onGameOverDismissClick(isHighScore);
	}
}
