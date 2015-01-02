package com.labrats.android.flip9;

import java.util.UUID;

public class FlipData {
	private UUID mId;
	private int mBestScore = -1;
	private int mStars;
	private int mPuzzle;
	private final int mStart;
	private String mTitle;

	public FlipData(int puzzle) {
		mPuzzle = puzzle;
		mStart = puzzle;
		mId = UUID.randomUUID();
	}

	public void setTitle(String title){
		mTitle = title;
	}
	
	public String getTitle(){
		return mTitle;
	}
	
	public UUID getId(){
		return mId;
	}
	
	public int getStart(){
		return mStart;
	}
	
	public int getBestScore() {
		return mBestScore;
	}

	public void setBestScore(int bestScore) {
		mBestScore = bestScore;
	}

	public int getStars() {
		return mStars;
	}

	public void setStars(int stars) {
		mStars = stars;
	}

	public int getCurrentState() {
		return mPuzzle;
	}
	
	public void flipTile(int index){
		mPuzzle = getBitmask(index) ^ mPuzzle;
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
