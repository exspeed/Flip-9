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
import android.widget.TextView;

public class PuzzleListFragment extends ListFragment {

	private ArrayList<FlipData> mUserData;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUserData = new ArrayList<FlipData>();
		for (int i = 1; i <= 30; i++) {
			FlipData data =new FlipData(53 * i % 512);
			data.setTitle("Puzzle "+ i);
			mUserData.add(data);
			
		}
		setListAdapter(new LevelAdapter(mUserData));
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

		i.putExtra(FlipNineFragment.EXTRA_GAME_NUMBER, level.getStart());
		startActivity(i);

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

			return convertView;
		}

	}
}
