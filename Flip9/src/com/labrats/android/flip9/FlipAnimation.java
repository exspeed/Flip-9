package com.labrats.android.flip9;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class FlipAnimation implements AnimationListener {

	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 0;

	private Animation mHorizontalIn;
	private Animation mHorizontalOut;
	private Animation mVerticalIn;
	private Animation mVerticalOut;
	private Button[] mButtons;
	private Context mContext;
	private int mCurrent;
	private int mCurrentBoardState;

	public FlipAnimation(Context context, Button[] buttons) {
		mHorizontalIn = AnimationUtils.loadAnimation(context,
				R.anim.flip_horizontal_in);
		mHorizontalOut = AnimationUtils.loadAnimation(context,
				R.anim.flip_horizontal_out);
		mVerticalOut = AnimationUtils.loadAnimation(context,
				R.anim.flip_vertical_out);
		mVerticalIn = AnimationUtils.loadAnimation(context,
				R.anim.flip_vertical_in);

		mHorizontalIn.setAnimationListener(this);
		mHorizontalOut.setAnimationListener(this);
		mVerticalIn.setAnimationListener(this);
		mVerticalOut.setAnimationListener(this);

		mButtons = buttons;
		mContext = context.getApplicationContext();

		initialize();
	}

	private void initialize() {
		for (int i = 0; i < mButtons.length; i++) {
			mButtons[i].setAnimation(mHorizontalIn);
			mButtons[i].setAnimation(mHorizontalOut);
			mButtons[i].setAnimation(mVerticalIn);
			mButtons[i].setAnimation(mVerticalOut);
		}
	}

	public void start(int index, int currentState) {

		for (Button b : mButtons) {
			b.setEnabled(false);
		}

		if (index + 1 < mButtons.length && (index != 2 && index != 5)) {
			mButtons[index + 1].startAnimation(mHorizontalIn);
			mCurrent = index + 1;
		}
		if (index - 1 >= 0 && (index != 3 && index != 6)) {
			mButtons[index - 1].startAnimation(mHorizontalIn);
			mCurrent = index - 1;
		}
		if (index + 3 < mButtons.length) {
			mButtons[index + 3].startAnimation(mVerticalIn);
			mCurrent = index + 3;
		}
		if (index - 3 >= 0) {
			mButtons[index - 3].startAnimation(mVerticalIn);
			mCurrent = index - 3;
		}
		mCurrentBoardState = currentState;
	}

	public void changePicture() {
		int temp = mCurrentBoardState;
		for (int i = 0; i < 9; i++) {
			if ((temp & 1) == 1){
				mButtons[i].setBackgroundResource(R.drawable.tile_start_state);
			}
			else {
				mButtons[i].setBackgroundResource(R.drawable.tile_end_state);
			}
			temp >>= 1;
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if(animation == mHorizontalIn){
			mButtons[mCurrent].startAnimation(mHorizontalOut);
			changePicture();
		}
		else if(animation == mVerticalIn){
			mButtons[mCurrent].startAnimation(mVerticalOut);
		}
		
		for(Button b: mButtons){
			b.setEnabled(true);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

}
