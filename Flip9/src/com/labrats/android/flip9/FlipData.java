package com.labrats.android.flip9;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class FlipData {

	public static final String JSON_BESTSCORE = "best score";
	public static final String JSON_STARS = "stars";
	public static final String JSON_START = "start";
	public static final String JSON_TITLE = "title";
	public static final String JSON_ID = "id";

	private UUID mId;
	private int mBestScore = 0;
	private int mStars = 0;
	private int mCurrentState;
	private int mStart;
	private String mTitle;

	public FlipData(int puzzle) {
		mStart = puzzle;
		mCurrentState = puzzle;
		mId = UUID.randomUUID();
	}

	public FlipData(JSONObject json) throws JSONException {

		mStart = (Integer) json.get(JSON_START);
		mCurrentState = mStart;
		mStars = (Integer) json.get(JSON_STARS);
		mBestScore = (Integer) json.get(JSON_BESTSCORE);
		mTitle = (String) json.get(JSON_TITLE);
		mId = UUID.fromString((String) json.get(JSON_ID));

	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		json.put(JSON_STARS, mStars);
		json.put(JSON_BESTSCORE, mBestScore);
		json.put(JSON_START, mStart);
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_ID, mId.toString());

		return json;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}

	public UUID getId() {
		return mId;
	}

	public int getStart() {
		return mStart;
	}

	public int getBestScore() {
		return mBestScore;
	}

	public void setBestScore(int bestScore) {
		if (mBestScore == 0)
			mBestScore = bestScore;

		if (mBestScore < bestScore)
			return;

		mBestScore = bestScore;
		setStars();

	}

	public int getStars() {
		return mStars;
	}

	private void setStars() {
		if (mBestScore < 10)
			mStars = 3;
		else if (mBestScore < 30)
			mStars = 2;
		else
			mStars = 1;
	}

	public int getCurrentState() {
		return mCurrentState;
	}

	public void flipTile(int index) {
		mCurrentState = getBitmask(index) ^ mCurrentState;
	}

	public static int getBitmask(int num) {
		switch (num) {
		case 0:
			return 11;
		case 1:
			return 23;
		case 2:
			return 38;
		case 3:
			return 89;
		case 4:
			return 186;
		case 5:
			return 308;
		case 6:
			return 200;
		case 7:
			return 464;
		case 8:
			return 416;
		default:
			return 0; // do nothing
		}
	}

}
