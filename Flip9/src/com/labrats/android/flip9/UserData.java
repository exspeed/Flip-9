package com.labrats.android.flip9;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.widget.Toast;

public class UserData {

	private static UserData sUserData;
	private ArrayList<FlipData> mLevelList;
	private Context mContext;
	private final String mFilename = "userdata.json";
	private int mCurrentLevel;
	private TypedArray mColorPalette;
	private int mColorIndex;

	private UserData(Context c) {
		mContext = c;
		try {
			loadData();
		} catch (Exception e) {
			Log.e("UserData", "Error loading data: " + e);
		}
		mColorPalette = c.getResources().obtainTypedArray(
				R.array.tilecolors);

		if (mLevelList.isEmpty()) {
			mLevelList = new ArrayList<FlipData>();
			for (int i = 1; i <= 30; i++) {
				FlipData data = new FlipData(53 * i % 512);
				data.setTitle("Puzzle " + i);
				mLevelList.add(data);

			}
			mCurrentLevel = 0;
			mColorIndex = 0;
			Log.d("UserData", "Fail to load");
		}
	}

	public static UserData get(Context c) {
		if (sUserData == null) {
			sUserData = new UserData(c.getApplicationContext());
		}
		return sUserData;
	}

	
	
	public ArrayList<FlipData> getLevelList() {
		return mLevelList;
	}

	public int getUserCurrentLevent() {
		return mCurrentLevel;
	}
	
	public int getColor(){
		return mColorPalette.getColor(mColorIndex,0) ;
	}
	
	public int getColorIndex(){
		return mColorIndex;
	}
	
	public void setColorPreference(int index){
		if(mColorPalette.length() < index){
			Log.d("UserData", "setColorPreference: index outofbounds");
		}
		else{
			mColorIndex = index;
		}
	}

	public void saveData() throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		boolean lastCompletedLevelFound = false;
		int i = 0;
		for (FlipData data : mLevelList) {
			jsonArray.put(data.toJSON());
			if (data.getStars() == 0 && !lastCompletedLevelFound) {
				mCurrentLevel = i;
				lastCompletedLevelFound = true;
			}
			i++;
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flipdata", jsonArray);
		jsonObject.put("color", mColorIndex);
		jsonObject.put("nextlevel", mCurrentLevel);

		Writer writer = null;

		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(jsonObject.toString());

		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}

	public void loadData() throws IOException, JSONException {
		BufferedReader reader = null;
		mLevelList = new ArrayList<FlipData>();

		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			Toast.makeText(mContext, "load data", Toast.LENGTH_SHORT).show();
			JSONObject data = new JSONObject(jsonString.toString());
			mCurrentLevel = data.getInt("nextlevel");
			mColorIndex = data.getInt("color");
			JSONArray array = data.getJSONArray("flipdata");

			for (int i = 0; i < array.length(); i++) {
				mLevelList.add(new FlipData(array.getJSONObject(i)));
			}

		} catch (FileNotFoundException e) {
			Log.d("UserData", "loadData is broken");
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
