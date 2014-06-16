package com.gimpel.android2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SaveGameDialog extends DialogFragment {
	public interface SaveGameDialogListener {
        public void onSaveGameDialogPositiveClick();
        public void onSaveGameDialogNegativeClick();
    }
	
	private SaveGameDialogListener mListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
        // Verify that the host activity implements the callback interface
        try {
            // Register the SaveGameDialogListener so we can send events to the host
            mListener = (SaveGameDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement SaveGameDialogListener");
        }
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SAVE")
        	   .setMessage("Do you want to save??")
               .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.onSaveGameDialogPositiveClick();
                   }
               })
               .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.onSaveGameDialogNegativeClick();
                   }
               });
        
        return builder.create();
    }
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		//mListener.onSaveGameDialogNegativeClick();
		super.onDismiss(dialog);
	}
}
