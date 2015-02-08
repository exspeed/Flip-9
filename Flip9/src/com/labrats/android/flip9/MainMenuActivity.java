package com.labrats.android.flip9;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenuActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new MainMenuFragment();
	}


}
