package com.labrats.android.flip9;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class PuzzleListFragment extends ListFragment {

	private ArrayList<FlipData> mLevelList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mLevelList = UserData.get(getActivity()).getLevelList();
		setListAdapter(new LevelAdapter(mLevelList));
	}

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_settings:
			FragmentManager fm = getActivity().getSupportFragmentManager();
			ColorSelectDialog dialog = new ColorSelectDialog();
			dialog.show(fm, "Choose Color");
			
		}
		return true;
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FlipData level = (FlipData) getListAdapter().getItem(position);
		Intent i = new Intent(getActivity(), FlipNineActivity.class);

		i.putExtra(FlipNineFragment.EXTRA_GAME_ID, level.getId());
		startActivity(i);
		getActivity().overridePendingTransition(R.anim.translate_left_in,R.anim.translate_left_out);

	}

	@Override
	public void onResume() {
		super.onResume();
		((LevelAdapter) getListAdapter()).notifyDataSetChanged();
	}

	private class LevelAdapter extends ArrayAdapter<FlipData> {

		public LevelAdapter(ArrayList<FlipData> data) {
			super(getActivity(), android.R.layout.simple_list_item_1, data);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.puzzle_list_item, null);
			}
			
			FlipData data = getItem(position);
			TextView puzzleTextView = (TextView) convertView
					.findViewById(R.id.level_list_textView);
			String title = data.getTitle();
			if(UserData.get(getActivity()).getUserCurrentLevent() < position){
				puzzleTextView.setText("???");
			}else{
			puzzleTextView.setText(title);
			}
			
			LinearLayout verticalLayout = (LinearLayout) convertView
					.findViewById(R.id.puzzle_list_vertial_layout);
			TextView bestScoreTextView = (TextView) verticalLayout
					.getChildAt(1);
			String record = getResources().getString(R.string.record);
			if (data.getStars() > 0) {

				bestScoreTextView.setText(record +": "
						+ getItem(position).getBestScore());

			} else {
				bestScoreTextView.setText(record + ": - -");
			}
			
			RatingBar puzzleRatingBar = (RatingBar) verticalLayout
					.getChildAt(0);
			puzzleRatingBar.setRating(data.getStars());
			
			return convertView;
		}
	}
}
