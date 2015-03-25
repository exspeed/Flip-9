package com.labrats.android.flip9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TimeTrialFragment extends FlipNineFragment{
	private TextView mTimer;

	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.activity_time_trial,container, false);
		
		TextView timeTrialTimer = (TextView) v.findViewById(R.id.TimeTrialTimer);
		//Timer stuff
		Timer countDown = new Timer(Timer.TWOMNUTES, Timer.ONESECOND, timeTrialTimer);
		countDown.start();
		
		return v;
		
	}
}



