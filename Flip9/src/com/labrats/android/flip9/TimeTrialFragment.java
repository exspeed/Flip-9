package com.labrats.android.flip9;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Random;


public class TimeTrialFragment extends Fragment{
	private final int TOTALPUZZLE = 511;
	private FlipData mFlipData;
	private Button mTileButtons [] = new Button[9];
	private TextView mCurrentScore;
	private Button mUndoButton;
	private ImageButton mInfoButton;
	private Button mRestartButton;
	private MediaPlayer mSoundEffect;
	private String mCurrentString;
	private int mScore = 0;
	private Animation mVerticalIn;
	private Animation mVerticalOut;
	private Random rand;
	private Timer countDown;
	
	
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.activity_time_trial,container, false);
		
		TextView timeTrialTimer = (TextView) v.findViewById(R.id.TimeTrialTimer);
		//Timer stuff
		countDown = new Timer(Timer.TWOMNUTES, Timer.ONESECOND, timeTrialTimer);
		countDown.start();
		
		TableLayout tableLayout = (TableLayout) v
				.findViewById(R.id.tablelayout_buttons);
		// if the layout were to change, this will crash
		int index = 0;
		for (int i = 0; i < 3; i++) {
			TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
			for (int j = 0; j < tableRow.getChildCount(); j++) {
				mTileButtons[index] = (Button) tableRow.getChildAt(j);
				mTileButtons[index]
						.setOnTouchListener(new TileListener2(index));

				index++;
			}

		}
		
		initialize();
		initializeAnimation();

		mCurrentScore = (TextView) v.findViewById(R.id.bestButton);
		mCurrentString = mCurrentScore.getText().toString() + "\n";
		mCurrentScore.setText(mCurrentString+mScore);
		
		mInfoButton = (ImageButton) v.findViewById(R.id.infoButton);
		mInfoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopupWindow infoPopUp = new PopupWindow(getActivity());
				TextView textW = new TextView(getActivity());
				LayoutParams layout = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				textW.setLayoutParams(layout);
				textW.setTextColor(-1);
				textW.setTextSize(16);
				textW.setText("To play this game, tap on a tile. The one you tap and the adjacent ones will change colors. "
						+ "The goal of the game is to make it all the same color");
				infoPopUp.setContentView(textW);
				infoPopUp.setWidth(800);
				infoPopUp.setHeight(300);
				infoPopUp.showAtLocation(mInfoButton,
						Gravity.CENTER_HORIZONTAL, 25, 25);
				// To close, Tap outside the box
				infoPopUp.setFocusable(true);
				infoPopUp.update();
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
	/*
	 * Used William J. Francis scale animation tutorial as reference for code
	 * below http://www.techrepublic.com/blog/software-engineer/
	 * use-androids-scale-animation-to-simulate-a-3d-flip/
	 */
	private void initializeAnimation() {
		mVerticalOut = AnimationUtils.loadAnimation(getActivity(),
				R.anim.flip_vertical_out);
		mVerticalIn = AnimationUtils.loadAnimation(getActivity(),
				R.anim.flip_vertical_in);

		mVerticalIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				updateChange();
				startAnimation(touchedTile, mVerticalOut);
			}
		});
	}
	
	int touchedTile = 0;

	private void startAnimation(int index, Animation anim) {
		touchedTile = index;
		int mask = FlipData.getBitmask(index);
		for (int i = 0; i < 9; i++, mask >>= 1) {
			if ((mask & 1) == 1)
				mTileButtons[i].startAnimation(anim);
		}

	}

	private void restart() {
		mScore = 0;
		mCurrentScore.setText(mCurrentString + mScore);
		int puzzle = rand.nextInt(TOTALPUZZLE);
		mFlipData = new FlipData(puzzle);
		updateChange();
		countDown.start();
		

		for (Button tile : mTileButtons) {
			if (tile.isEnabled() == false)
				tile.setEnabled(true);
			tile.setText("");
		}

	}
	
	private void initialize() {
		// find FlipData that corresponds to the puzzle
		rand = new Random();
		int puzzle = rand.nextInt(TOTALPUZZLE);
		mFlipData = new FlipData(puzzle);

		// in case the user goes back to the same level
		// and currentState was saved, restart() prevents cheating
		mFlipData.restart();
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
			// reset highlighted buttons
			mTileButtons[i].setPressed(false);
			if ((temp & 1) == 1)
				mTileButtons[i]
						.setBackgroundResource(R.drawable.tile_start_state);
			else {
				mTileButtons[i]
						.setBackgroundResource(R.drawable.tile_end_state_normal);
				GradientDrawable background = (GradientDrawable) mTileButtons[i]
						.getBackground();
				background.mutate();
				background.setColor(UserData.get(getActivity()).getColor());
				
			}
			temp >>= 1;
		}
	}

	private void checkCompleted() {
		if (mFlipData.getCurrentState() == 0) {
			mScore+=1;
			mCurrentScore.setText(mCurrentString+mScore);
			int puzzle = rand.nextInt(TOTALPUZZLE);
			mFlipData = new FlipData(puzzle);
			updateChange();
		}
	}

	
	private class TileListener2 implements OnTouchListener {

		private final int POSITION;
		private Rect rect;
		private boolean cancel = false;

		public TileListener2(int index) {
			this.POSITION = index;

		}

		/*
		 * Learn how to detect cancel action
		 * http://stackoverflow.com/questions/6410200/
		 * android-how-to-detect-if-use-touches-and-drags-out-of-button-region
		 */
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				highlightTile();
				rect = new Rect(mTileButtons[POSITION].getLeft(),
						mTileButtons[POSITION].getTop(),
						mTileButtons[POSITION].getRight(),
						mTileButtons[POSITION].getBottom());
				break;
			case MotionEvent.ACTION_MOVE:
				if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop()
						+ (int) event.getY())) {
					updateChange();
					cancel = true;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (!cancel) {
					playSound();
					mFlipData.flipTile(POSITION);
					startAnimation(POSITION, mVerticalIn);

					checkCompleted();
					
				}
				cancel = false;
				break;

			default:

			}
			return true;
		}

		private void highlightTile() {
			int mask = FlipData.getBitmask(POSITION);
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
					/*
					  if(mp == mSoundEffect){ mSoundEffect.start(); }
					 */

					if (mSoundEffect != null) {
						mSoundEffect.reset();
						mSoundEffect.release();

						mSoundEffect = null;
					}

				}
			});
			mSoundEffect.start();
		}

	}

}



