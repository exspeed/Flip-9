package com.labrats.android.flip9;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class FlipNineActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Intent i = getIntent();
		int level = i.getIntExtra(FlipNineFragment.EXTRA_GAME_NUMBER, 0);
		return FlipNineFragment.newInstance(level);
	}

}
