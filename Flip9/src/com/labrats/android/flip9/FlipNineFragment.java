package com.labrats.android.flip9;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

import android.app.ActionBar.LayoutParams;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FlipNineFragment extends Fragment {

	public static final String EXTRA_GAME_ID = "game id";

	private FlipData mFlipData;
	private Button[] mTileButtons = new Button[9]; // the references of the 9 //
													// // buttons
	private String mMoveString; // "Move:" String
	private int mCounter = 0; // the number of time the user press a tile
	private TextView mMoveTextView;
	private TextView mBestTextView;
	private TextView mTitleTextView;
	private Button mUndoButton;
	private Button mCheatButton;
	private Button mRestartButton;
	private Stack<Integer> mStackHistory;
	private MediaPlayer mSoundEffect;

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
		mMoveString = mMoveTextView.getText().toString(); // kind of bad??

		TableLayout tableLayout = (TableLayout) v
				.findViewById(R.id.fragment_flip_nine_tableLayout);
		// if the layout were to change, this will crash
		int index = 0;
		for (int i = 1; i <= 3; i++) {
			TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
			for (int j = 0; j < tableRow.getChildCount(); j++) {
				mTileButtons[index] = (Button) tableRow.getChildAt(j);
				mTileButtons[index].setOnClickListener(new TileListener(index));
				index++;
			}

		}

		initialize();
		
		mTitleTextView = (TextView) v.findViewById(R.id.titleTextView);
		mTitleTextView.setText(mFlipData.getTitle());
				
		
		mBestTextView = (TextView) v.findViewById(R.id.bestTextView);
		if(mFlipData.getBestScore() != 0){
			mBestTextView.setText(mBestTextView.getText()+ " " + mFlipData.getBestScore());
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
				Cheat g = new Cheat();
				PopupWindow popup = new PopupWindow(getActivity());
				popup.getBackground().setAlpha(50);
				TextView textV = new TextView(getActivity());
				LayoutParams linearparams1 = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				textV.setLayoutParams(linearparams1);
				ArrayList<Integer> answer = g.getCheat(mFlipData
						.getCurrentState());
				textV.setText("To solve the board, tap the following tiles: "
						+ answer);
				popup.setContentView(textV);
				popup.setWidth(600);
				popup.setHeight(337);
				popup.showAtLocation(mCheatButton, Gravity.CENTER_HORIZONTAL,
						25, 25);
				// To close, tap outside the box
				popup.setFocusable(true);
				popup.update();

				for (int num : answer) {
					mTileButtons[num - 1].setText("*");
				}

			}
		});

		mRestartButton = (Button) v.findViewById(R.id.restartButton);
		mRestartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCounter = 0;
				mMoveTextView.setText(mMoveString);
				mFlipData.restart();
				updateChange();
			}
		});

		return v;
	}

	private void initialize() {
		// find FlipData that corresponds to the puzzle
		UUID gameId = (UUID) getArguments().getSerializable(EXTRA_GAME_ID);
		for (FlipData someLevel : UserData.get(getActivity()).getLevelList()) {
			if (someLevel.getId().equals(gameId)) {
				mFlipData = someLevel;
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
			if ((temp & 1) == 1)
				mTileButtons[i]
						.setBackgroundResource(R.drawable.button_shape_flip);
			else {
				mTileButtons[i]
						.setBackgroundResource(R.drawable.button_shape_normal);
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
				Log.d("FlipNineFragment", "Erro in saving: " + e);
			}
			Toast.makeText(getActivity(), "Saving", Toast.LENGTH_SHORT).show();

		}
	}

	private class TileListener implements OnClickListener {

		private int position;

		public TileListener(int index) {
			this.position = index;
		}

		@Override
		public void onClick(View v) {
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
