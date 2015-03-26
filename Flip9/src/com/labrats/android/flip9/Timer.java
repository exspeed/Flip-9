package com.labrats.android.flip9;

import java.util.concurrent.TimeUnit;

import android.os.CountDownTimer;
import android.widget.TextView;

public abstract class Timer extends CountDownTimer {
	public static final int ONEMINUTE = 60999;
	public static final int ONESECOND = 1000;
	private TextView clock;

	public Timer(long millisInFuture, long countDownInterval, TextView clock) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
		this.clock = clock;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub

		long millis = millisUntilFinished;

		String hms = String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis)));
		clock.setText(hms);

	}

	@Override
	public void onFinish() {
		String done = "Game Over";
		clock.setText(done);
		onComplete();

	}
	
	public abstract void onComplete();
}
