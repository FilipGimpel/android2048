package com.gimpel.android2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SaveGameDetailDialog extends DialogFragment {
    private SaveGameDetailDialogListener mListener;
    private EditText mPlayerName;
	
 // interface to deliver action events
	public interface SaveGameDetailDialogListener {
        public void onSaveGameDetailDialogPositiveClick(String playerName);
        //public void onSaveGameDetailDialogNegativeClick();
    }
	
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SaveGameDetailDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    
	    View view = inflater.inflate(R.layout.dialog_fragment_highscore, null);
	    mPlayerName = (EditText) view.findViewById(R.id.edittext_player_name);
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    // Add action buttons
	    	   .setTitle("SAVE")
	           .setPositiveButton(/*R.string.signin*/"OK", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   String userInput = mPlayerName.getText().toString();
	            	   
	                   mListener.onSaveGameDetailDialogPositiveClick(userInput);
	               }
	           });
//	           .setNegativeButton(/*R.string.cancel*/"CANCEL", new DialogInterface.OnClickListener() {
//	               public void onClick(DialogInterface dialog, int id) {
//	            	   SaveGameDetailDialog.this.getDialog().cancel();
//	            	   mListener.onDialogNegativeClick();
//	               }
//	           });  
	    
	    return builder.create();
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		
		
	}
}
