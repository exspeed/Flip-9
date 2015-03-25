package com.labrats.android.flip9;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class FlipData {
	//Initialize variables
	public static final String JSON_BESTSCORE = "best score";
	public static final String JSON_STARS = "stars";
	public static final String JSON_START = "start";
	public static final String JSON_TITLE = "title";
	public static final String JSON_ID = "id";
	private static final int[] masks = { 11, 23, 38, 89, 186, 308, 200, 464,
			416 };
	private UUID mId;
	private int mBestScore = 0;
	private int mStars = 0;
	private int mCurrentState;

	// mStart needs to final somehow... no setter method?
	private int mStart;
	private String mTitle;

	//FlipData constructor
	public FlipData(int puzzle) {
		mStart = puzzle;
		mCurrentState = puzzle;
		mId = UUID.randomUUID();
	}

	//Loads data 
	public FlipData(JSONObject json) throws JSONException {

		mStart = (Integer) json.get(JSON_START);
		mCurrentState = mStart;
		mStars = (Integer) json.get(JSON_STARS);
		mBestScore = (Integer) json.get(JSON_BESTSCORE);
		mTitle = (String) json.get(JSON_TITLE);
		mId = UUID.fromString((String) json.get(JSON_ID));

	}

	//Makes a new JSON file to save to
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		json.put(JSON_STARS, mStars);
		json.put(JSON_BESTSCORE, mBestScore);
		json.put(JSON_START, mStart);
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_ID, mId.toString());

		return json;
	}

	//Setters and getters
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

	public void setBestScore(int newScore) {
		if (mBestScore == 0)
			mBestScore = newScore;

		if (mBestScore < newScore)
			return;

		mBestScore = newScore;
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

	public void restart() {
		mCurrentState = mStart;
	}

	public static int getBitmask(int num) {
		if (num >= 0 && num < masks.length)
			return masks[num];
		return 0;
	}

}
