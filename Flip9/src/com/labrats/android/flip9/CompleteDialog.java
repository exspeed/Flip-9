package com.labrats.android.flip9;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AbsListView.SelectionBoundsAdjuster;

public class CompleteDialog extends DialogFragment {

	public static final String EXTRA_NEXT = "next";
	public static final String EXTRA_SCORE ="stars";

	private static final String[] MOTIVATION = { "Keep it up!", "Outstanding",
			"Good job", "Way to go", "Right on", "Nice work" };
	private TextView mMotivationTextView;
	private RatingBar mRatingBar;

	public static CompleteDialog newInstance(int numOfStars) {
		Bundle args = new Bundle();
		args.putInt(EXTRA_SCORE, numOfStars);
		
		CompleteDialog fragment = new CompleteDialog();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View v = getActivity().getLayoutInflater().inflate(
				R.layout.complete_fragment, null);
		mMotivationTextView = (TextView) v.findViewById(R.id.congratsTextView);
		Random rand = new Random();
		int index = rand.nextInt(MOTIVATION.length);
		mMotivationTextView.setText(MOTIVATION[index]);

		mRatingBar = (RatingBar) v.findViewById(R.id.congratsRatingBar);
		int score = getArguments().getInt(EXTRA_SCORE);
		mRatingBar.setRating(getStars(score));


		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setView(v);

		builder.setPositiveButton("Next", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				i.putExtra(EXTRA_NEXT, true);

				getTargetFragment().onActivityResult(getTargetRequestCode(),
						Activity.RESULT_OK, i);
			}
		});

		builder.setNegativeButton("Retry", new OnClickListener() {

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
	
	public int getStars(int score){
		if(score <= 10)
			return 3;
		else if (score <= 20)
			return 2;
		else
			return 1;
	}
}
