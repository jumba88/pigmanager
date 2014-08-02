package com.honglang.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.JsonReader;

import com.honglang.app.AppException;

public class ChartData extends Entity {
	private HashMap<String, JSONArray> data = new HashMap<String, JSONArray>();
	
	private HashMap<String,JSONArray> getChartData(){
		return data;
	}
	
	private static ChartData parse(InputStream is) throws IOException, AppException {
		ChartData chartdata = new ChartData();
//		JsonReader
//		JSONObject json = new JSONObject(null);
		return chartdata;
	}
}
