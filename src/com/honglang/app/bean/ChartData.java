package com.honglang.app.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.JsonReader;
import android.util.Log;

import com.honglang.app.AppException;

public class ChartData extends Entity {
	
	private int size;
	private JSONArray pigdata;
	private JSONArray corndata;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public JSONArray getPigdata() {
		return pigdata;
	}

	public void setPigdata(JSONArray pigdata) {
		this.pigdata = pigdata;
	}

	public JSONArray getCorndata() {
		return corndata;
	}

	public void setCorndata(JSONArray corndata) {
		this.corndata = corndata;
	}

	public static ChartData parse(InputStream is) throws IOException, AppException {
		ChartData chartdata = new ChartData();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder builder = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			JSONObject json = new JSONObject(builder.toString());
			chartdata.setSize(json.length());
			chartdata.setPigdata(json.getJSONArray("pig"));
			chartdata.setCorndata(json.getJSONArray("corn"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return chartdata;
	}
}
