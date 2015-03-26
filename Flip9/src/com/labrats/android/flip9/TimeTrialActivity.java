package com.labrats.android.flip9;

import java.util.UUID;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TimeTrialActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new TimeTrialFragment();
	}

	
}
