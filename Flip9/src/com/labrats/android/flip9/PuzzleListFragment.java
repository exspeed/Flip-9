package com.labrats.android.flip9;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class PuzzleListFragment extends ListFragment {

	private ArrayList<FlipData> mLevelList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLevelList = UserData.get(getActivity()).getLevelList();

		setListAdapter(new LevelAdapter(mLevelList));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FlipData level = (FlipData) getListAdapter().getItem(position);
		Intent i = new Intent(getActivity(), FlipNineActivity.class);

		i.putExtra(FlipNineFragment.EXTRA_GAME_ID, level.getId());
		startActivity(i);

	}

	@Override
	public void onResume() {
		super.onResume();
		((LevelAdapter)getListAdapter()).notifyDataSetChanged();
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

			TextView puzzleTextView = (TextView) convertView
					.findViewById(R.id.level_list_textView);
			String title = getItem(position).getTitle();
			puzzleTextView.setText(title);

			RatingBar puzzleRatingBar = (RatingBar) convertView
					.findViewById(R.id.level_list_ratingBar);
			puzzleRatingBar.setRating(getItem(position).getStars());

			return convertView;
		}

	}
}