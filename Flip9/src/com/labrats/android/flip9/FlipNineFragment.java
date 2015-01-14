package com.labrats.android.flip9;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FlipNineFragment extends Fragment {

	public static final String EXTRA_GAME_ID = "game id";
	private static final int REQUEST_COMPLETION = 0;

	private FlipData mFlipData;
	private Button[] mTileButtons = new Button[9]; // the references of the 9 //
													// // buttons
	private String mMoveString; // "Move:" String
	private String mBestString; // don't change these strings
	private int mCounter = 0; // the number of time the user press a tile
	private int mArrayListIndex;
	private TextView mMoveTextView;
	private TextView mBestTextView;
	private TextView mTitleTextView;
	private Button mUndoButton;
	private Button mCheatButton;
	private Button mRestartButton;
	private Stack<Integer> mStackHistory;
	private MediaPlayer mSoundEffect;
	private ArrayList<FlipData> list;

	public static FlipNineFragment newInstance(UUID gameId) {
		Bundle info = new Bundle();
		info.putSerializable(EXTRA_GAME_ID, gameId);
		FlipNineFragment fragment = new FlipNineFragment();
		fragment.setArguments(info);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_flip_nine, container, false);

		mMoveTextView = (TextView) v.findViewById(R.id.moveTextView);
		mMoveString = mMoveTextView.getText().toString() + " "; // kind of bad??
		mMoveTextView.setText(mMoveString + "0");

		TableLayout tableLayout = (TableLayout) v
				.findViewById(R.id.fragment_flip_nine_tableLayout);
		// if the layout were to change, this will crash
		int index = 0;
		for (int i = 1; i <= 3; i++) {
			TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
			for (int j = 0; j < tableRow.getChildCount(); j++) {
				mTileButtons[index] = (Button) tableRow.getChildAt(j);
				mTileButtons[index].setOnTouchListener(new TileListener2(index));

				index++;
			}

		}

		initialize();

		mTitleTextView = (TextView) v.findViewById(R.id.titleTextView);
		mTitleTextView.setText(mFlipData.getTitle());

		mBestTextView = (TextView) v.findViewById(R.id.bestTextView);
		mBestString = mBestTextView.getText().toString();
		if (mFlipData.getBestScore() != 0) {
			mBestTextView.setText(mBestString + " " + mFlipData.getBestScore());
		}

		mUndoButton = (Button) v.findViewById(R.id.undoButton);
		mUndoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mStackHistory.isEmpty())
					return;
				mCounter--;
				int lastMove = mStackHistory.pop();
				mMoveTextView.setText(mMoveString + mCounter);
				mFlipData.flipTile(lastMove);
				updateChange();
			}
		});

		// Cheat Button
		mCheatButton = (Button) v.findViewById(R.id.cheatButton);
		mCheatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<Integer> answer = Cheat.getCheat(mFlipData
						.getCurrentState());
				for (int num : answer) {
					mTileButtons[num].setText("*");
				}

			}
		});

		mRestartButton = (Button) v.findViewById(R.id.restartButton);
		mRestartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				restart();

			}
		});

		return v;
	}

	private void restart() {
		mCounter = 0;
		mMoveTextView.setText(mMoveString + "0");
		mFlipData.restart();
		updateChange();
		mStackHistory.clear();
		if (mFlipData.getBestScore() != 0) {
			mBestTextView.setText(mBestString + " " + mFlipData.getBestScore());
		} else {
			mBestTextView.setText(mBestString);
		}
		mTitleTextView.setText(mFlipData.getTitle());

		for (Button tile : mTileButtons) {
			if (mTileButtons[0].isClickable() == false)
				tile.setClickable(true);
			tile.setText("");
		}

	}

	private void initialize() {
		// find FlipData that corresponds to the puzzle
		UUID gameId = (UUID) getArguments().getSerializable(EXTRA_GAME_ID);

		list = UserData.get(getActivity()).getLevelList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(gameId)) {
				mFlipData = list.get(i);
				mArrayListIndex = i;
				break;
			}
		}

		// in case the user goes back to the same level
		// and currentState was saved, restart() prevents cheating
		mFlipData.restart();

		mStackHistory = new Stack<Integer>();
		updateChange();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mSoundEffect != null) {
			mSoundEffect.release();
			mSoundEffect = null;
		}
	}

	private void updateChange() {
		int temp = mFlipData.getCurrentState();
		for (int i = 0; i < 9; i++) {
			mTileButtons[i].setPressed(false);
			if ((temp & 1) == 1)
				mTileButtons[i].setBackgroundResource(R.drawable.button_state2);
			else {
				mTileButtons[i].setBackgroundResource(R.drawable.button_state1);
			}
			temp >>= 1;
		}
	}

	// TODO: make a pop up window to show user's result
	private void checkCompleted() {
		if (mFlipData.getCurrentState() == 0) {
			mFlipData.setBestScore(mCounter);
			try {
				UserData.get(getActivity()).saveData();
			} catch (Exception e) {
				Log.d("FlipNineFragment", "Error in saving: " + e);
			}
			mBestTextView.setText(mBestString + " " + mFlipData.getBestScore());

			for (Button tile : mTileButtons) {
				tile.setClickable(false);
			}

			FragmentManager fm = getActivity().getSupportFragmentManager();
			CompleteDialog dialog = CompleteDialog.newInstance(mCounter);
			dialog.setTargetFragment(this, REQUEST_COMPLETION);
			dialog.show(fm, "Complete Game");

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			boolean next = data.getBooleanExtra(CompleteDialog.EXTRA_NEXT,
					false);
			if (next) {
				mArrayListIndex = ++mArrayListIndex % list.size();
			}

			mFlipData = list.get(mArrayListIndex);
			restart();

		}
	}

	private class TileListener2 implements OnTouchListener {

		private int position;

		public TileListener2(int index) {
			this.position = index;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				highlightTile();
				break;
			case MotionEvent.ACTION_UP:
				mCounter++;
				mMoveTextView.setText(mMoveString + mCounter);
				mStackHistory.push(position);
				playSound();
				mFlipData.flipTile(position);
				updateChange();
				checkCompleted();

				// remove " * "
				if (mTileButtons[position].getText().equals("*"))
					mTileButtons[position].setText("");
				break;
			default:
				// do nothing?
			}
			return true;
		}

		private void highlightTile(){
			int mask = FlipData.getBitmask(position);
			for (int i = 0; i < 9; i++) {
				if ((mask & 1) == 1)
					mTileButtons[i].setPressed(true);
				mask >>= 1;
			}
		}
		
		private void playSound() {
			if (mSoundEffect == null) {
				mSoundEffect = MediaPlayer.create(getActivity(), R.raw.mouse1);
			}
			mSoundEffect.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					if (mSoundEffect != null) {
						mSoundEffect.release();
						mSoundEffect = null;
					}

				}
			});
			mSoundEffect.start();
		}

	}
}
