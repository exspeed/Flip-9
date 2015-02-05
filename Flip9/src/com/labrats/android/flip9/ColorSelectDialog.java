package com.labrats.android.flip9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorSelectDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.color_pick_dialog, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		Button[][] colorButtons = new Button[2][4];
		LinearLayout rowOfButtons = (LinearLayout) v
				.findViewById(R.id.color_row1);

		for (int i = 0; i < rowOfButtons.getChildCount(); i++) {
			colorButtons[0][i] = (Button) rowOfButtons.getChildAt(i);

		}
		rowOfButtons = (LinearLayout) v.findViewById(R.id.color_row2);
		for (int i = 0; i < rowOfButtons.getChildCount(); i++) {
			colorButtons[1][i] = (Button) rowOfButtons.getChildAt(i);
		}
		TypedArray colors = getActivity().getApplicationContext()
				.getResources().obtainTypedArray(R.array.tilecolors);
		int count = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				GradientDrawable background = (GradientDrawable) colorButtons[i][j]
						.getBackground();
				background.mutate();
				background.setColor(colors.getColor(count, 0));
				count++;
			}
		}
		colors.recycle();

		builder.setTitle("Color");
		builder.setView(v);

		builder.setPositiveButton("Ok", null);

		return builder.create();
	}
}
