package com.labrats.android.flip9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ColorSelectDialog extends DialogFragment {
	ImageButton[] colorButtons = new ImageButton[8];

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.color_pick_dialog, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		initializeColorButton(v);

		colorButtons[0].setImageResource(R.drawable.check);

		builder.setTitle("Color");
		builder.setView(v);

		builder.setPositiveButton("OK", null);

		return builder.create();
	}

	private void initializeColorButton(View v) {
		LinearLayout rowOfButtons = (LinearLayout) v
				.findViewById(R.id.color_row1);
		int index = 0;
		for (int i = 0; i < rowOfButtons.getChildCount(); i++) {
			colorButtons[index] = (ImageButton) rowOfButtons.getChildAt(i);
			index++;
		}
		rowOfButtons = (LinearLayout) v.findViewById(R.id.color_row2);
		for (int i = 0; i < rowOfButtons.getChildCount(); i++) {
			colorButtons[index] = (ImageButton) rowOfButtons.getChildAt(i);
			index++;
		}
		TypedArray colors = getActivity().getApplicationContext()
				.getResources().obtainTypedArray(R.array.tilecolors);
		int count = 0;

		for (int i = 0; i < colorButtons.length; i++) {
			colorButtons[i].setOnClickListener(new AddCheckListener());
			GradientDrawable background = (GradientDrawable) colorButtons[i]
					.getBackground();
			background.mutate();
			background.setColor(colors.getColor(count, 0));
			count++;

		}

		colors.recycle();
	}

	private class AddCheckListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			for (int i = 0; i < colorButtons.length; i++) {
				colorButtons[i].setImageDrawable(null);
			}

			((ImageButton) v).setImageResource(R.drawable.check);

		}

	}
}
