package com.labrats.android.flip9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuFragment extends Fragment {
	private Button mClassicButton;
	private Button mTimeTrialButton;
	private Button mNightmareButton;
	private Button mSettingsButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
/*
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_flip_nine, container, false);

		mClassicButton = (Button)v.findViewById(R.id.Classic);
		mClassicButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Hi", "Classic Menu");
				
			}
		});
		
		mTimeTrialButton = (Button)v.findViewById(R.id.Time_Trial);
		mTimeTrialButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Hi", "Time Trial Menu");
				
			}
		});
		
		mNightmareButton = (Button) v.findViewById(R.id.Nightmare);
		mNightmareButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Hi", "Nightmare Menu");
				
			}
		});
		
		mTimeTrialButton = (Button) v.findViewById(R.id.Settings);
		mTimeTrialButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Hi", "Settings Menu");
				
			}
		});
		return v;

	}
*/
}
