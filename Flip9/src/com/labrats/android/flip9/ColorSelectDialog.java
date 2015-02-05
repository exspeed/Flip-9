package com.labrats.android.flip9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class ColorSelectDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.color_pick_dialog, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle("Color");
		builder.setView(v);
		
		builder.setPositiveButton("Ok", null);
		
		
		return builder.create();
	}
}
