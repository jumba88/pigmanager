package com.honglang.app.fragment;

import com.honglang.app.R;
import com.honglang.app.adapter.HeadlineAdapter;
import com.honglang.app.widget.MyListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeadlineFragment extends Fragment {

	private MyListView mListView;
	private HeadlineAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_news_headline, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mListView = (MyListView) view.findViewById(R.id.headline_listview);
		adapter = new HeadlineAdapter(getActivity());
		mListView.setAdapter(adapter);
	}

}
