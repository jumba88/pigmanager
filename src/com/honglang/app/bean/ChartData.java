package com.honglang.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.JsonReader;

import com.honglang.app.AppException;

public class ChartData extends Entity {
	private HashMap<String, JSONArray> dataMap = new HashMap<String, JSONArray>();
	
	private HashMap<String,JSONArray> getChartData(){
		return dataMap;
	}
	
	private static ChartData parse(InputStream is) throws IOException, AppException {
		ChartData chartdata = new ChartData();
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		JSONTokener tokener = new JSONTokener(new InputStreamReader(is, "UTF-8").toString());
		try {
			JSONObject json = new JSONObject(tokener);
			chartdata.getChartData().put("pig", json.getJSONArray("pig"));
			chartdata.getChartData().put("corn", json.getJSONArray("corn"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return chartdata;
	}
}
