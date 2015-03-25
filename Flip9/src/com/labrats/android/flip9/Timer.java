package com.labrats.android.flip9;

import java.util.concurrent.TimeUnit;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends CountDownTimer {
	public static final int TWOMNUTES = 120999;
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

	}/*
	 * public void onTick(long millisUntilFinished, TextView v){ long millis =
	 * millisUntilFinished; hms = String.format("%02d:%02d:%02d",
	 * TimeUnit.MICROSECONDS.toHours(millis),
	 * TimeUnit.MICROSECONDS.toMinutes(millis) -
	 * TimeUnit.HOURS.toMinutes(TimeUnit.MICROSECONDS.toHours(millis)),
	 * TimeUnit.
	 * MICROSECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit
	 * .MICROSECONDS.toMinutes(millis))); //v.setText(hms); }
	 */

}
