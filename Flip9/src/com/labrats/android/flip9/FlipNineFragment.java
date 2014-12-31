package com.labrats.android.flip9;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FlipNineFragment extends Fragment {

	private int mGameNumber; // number between 1-511 to represent the level
	private int[] mTiles = new int[9]; // array representation of gameNumber
	private Button[] mTileButtons = new Button[9]; // the references of the 9
													// buttons
	private String mMoveString; // "Move:" String
	private int mCounter = 0; // the number of time the user press a tile
	private TextView mMoveTextView;

	private MediaPlayer mSoundEffect;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_flip_nine, container, false);

		initialize();

		mMoveTextView = (TextView) v.findViewById(R.id.moveTextView);
		mMoveString = mMoveTextView.getText().toString();

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
		return v;
	}

	private void initialize() {
		// TODO: mGameNumber should be retrieved from listFragment
		mGameNumber = 0;
		int temp = mGameNumber;
		for (int i = 0; i < mTiles.length; i++) {
			mTiles[i] = temp & 1;
			temp = temp >> 1;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mSoundEffect != null) {
			mSoundEffect.release();
			mSoundEffect = null;
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
			playSound();
			
			changeColor(position);
			changeColor(position - 3);
			if (position != 3 && position != 6)
				changeColor(position - 1);
			if (position != 2 && position != 5)
				changeColor(position + 1);
			changeColor(position + 3);

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

		private void changeColor(int index) {
			if (index >= 0 && index <= mTileButtons.length - 1) {
				mTiles[index] = mTiles[index] ^ 1;
				if (mTiles[index] == 1)
					mTileButtons[index]
							.setBackgroundResource(R.drawable.button_shape_normal);
				else {
					mTileButtons[index]
							.setBackgroundResource(R.drawable.button_shape_flip);
				}

			}
		}
	}
}
