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
import org.json.JSONTokener;
import android.content.Context;
import android.util.Log;

public class UserData {

	private static UserData sUserData;
	private ArrayList<FlipData> mLevelList;
	private Context mContext;
	private final String mFilename = "userdata.json";

	private UserData(Context c) {
		mContext = c;
		try {
			loadData();
		} catch (Exception e) {
			Log.e("UserData", "Error loading data: " + e);
		}

		if (mLevelList.isEmpty()) {
			mLevelList = new ArrayList<FlipData>();
			for (int i = 1; i <= 30; i++) {
				FlipData data = new FlipData(53 * i % 512);
				data.setTitle("Puzzle " + i);
				mLevelList.add(data);

			}
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

	public void saveData() throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		for (FlipData data : mLevelList) {
			jsonArray.put(data.toJSON());
		}

		Writer writer = null;

		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(jsonArray.toString());
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

			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
					.nextValue();
			for (int i = 0; i < array.length(); i++) {
				mLevelList.add(new FlipData(array.getJSONObject(i)));
			}

		} catch (FileNotFoundException e) {

		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
