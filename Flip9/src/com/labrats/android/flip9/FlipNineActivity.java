package com.labrats.android.flip9;

import java.util.UUID;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class FlipNineActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Intent i = getIntent();
		UUID gameId = (UUID) i.getSerializableExtra(FlipNineFragment.EXTRA_GAME_ID);
		return FlipNineFragment.newInstance(gameId);
	}

}
