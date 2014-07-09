package com.honglang.app.adapter;

import com.honglang.app.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HeadlineAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Activity activity;
	
	public HeadlineAdapter(Activity activity) {
		super();
		this.activity = activity;
		inflater = activity.getLayoutInflater();
	}

	@Override
	public int getCount() {
		return 20;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.news_item, null);
		return convertView;
	}

}
