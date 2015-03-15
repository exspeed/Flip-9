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
		Intent i = getIntent();
		UUID gameId = (UUID) i.getSerializableExtra(TimeTrialFragment.EXTRA_GAME_ID);
		return TimeTrialFragment.newInstance(gameId);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.translate_right_in, R.anim.translate_right_out);
	}
}
