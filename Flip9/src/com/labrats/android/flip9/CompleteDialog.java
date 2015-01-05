package com.labrats.android.flip9;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CompleteDialog extends DialogFragment {

	public static final String EXTRA_NEXT = "next";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Good job!");

		builder.setPositiveButton("next", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				i.putExtra(EXTRA_NEXT, true);
				
				getTargetFragment().onActivityResult(getTargetRequestCode(),
						Activity.RESULT_OK, i);
			}
		});
		builder.setNegativeButton("retry", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				i.putExtra(EXTRA_NEXT, false);
				getTargetFragment().onActivityResult(getTargetRequestCode(),
						Activity.RESULT_OK, i);
			}
		});

		return builder.create();
	}
}
