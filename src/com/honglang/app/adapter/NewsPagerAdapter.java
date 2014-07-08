package com.honglang.app.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> items;
	private String[] titles;
	public NewsPagerAdapter(FragmentManager fm,  List<Fragment> items, String[] titles) {
		super(fm);
		this.items = items;
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position%titles.length].toUpperCase();
	}

	@Override
	public int getCount() {
		return items.size();
	}

}
