package com.labrats.android.flip9;

import java.util.concurrent.TimeUnit;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends CountDownTimer{

	public Timer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		/*
		long millis = millisUntilFinished;
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MICROSECONDS.toHours(millis), 
				TimeUnit.MICROSECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MICROSECONDS.toHours(millis)),
				TimeUnit.MICROSECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MICROSECONDS.toMinutes(millis)));
		*/
		
	}

	@Override
	public void onFinish() {
		
		
	}
	public void onTick(long millisUntilFinished, TextView v){
		long millis = millisUntilFinished;
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MICROSECONDS.toHours(millis), 
				TimeUnit.MICROSECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MICROSECONDS.toHours(millis)),
				TimeUnit.MICROSECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MICROSECONDS.toMinutes(millis)));
		v.setText(hms);
	}
	public void onFinish(TextView v){
		v.setText("Game over");
	}

}
