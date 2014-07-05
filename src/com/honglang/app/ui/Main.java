package com.honglang.app.ui;

import com.honglang.app.AppContext;
import com.honglang.app.R;
import com.honglang.app.R.layout;
import com.honglang.app.R.menu;
import com.honglang.app.utils.UIHelper;
import com.honglang.app.widget.ScrollLayout;
import com.honglang.app.widget.ScrollLayout.OnViewChangeListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class Main extends BaseActivity {

	private ScrollLayout mScrollLayout;
	private RadioButton[] mButtons;
	private String[] mHeadTitles;
	private int mViewCount;
	private int mCurSel;

	private TextView mHeadTitle;
	private ProgressBar mHeadProgress;

	private RadioButton rbNews;
	private RadioButton rbPrice;
	private RadioButton rbManufacturer;
	private RadioButton rbWiki;
	private RadioButton rbProfile;

	private AppContext appContext;// 全局Context

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		appContext = (AppContext) getApplication();
		// 网络连接判断
		if (!appContext.isNetworkConnected())
			UIHelper.ToastMessage(this, R.string.network_not_connected);

		initHeadView();
		initFootBar();
		initScrollLayout();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	public void initHeadView() {
		mHeadTitle = (TextView) findViewById(R.id.title);
		mHeadProgress = (ProgressBar) findViewById(R.id.loading);
	}

	public void initFootBar() {
		rbNews = (RadioButton) findViewById(R.id.main_footbar_news);
		rbPrice = (RadioButton) findViewById(R.id.main_footbar_price);
		rbManufacturer = (RadioButton) findViewById(R.id.main_footbar_manufacturer);
		rbWiki = (RadioButton) findViewById(R.id.main_footbar_wiki);
		rbProfile = (RadioButton) findViewById(R.id.main_footbar_profile);
	}

	public void initScrollLayout() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.scroll);

		LinearLayout frame = (LinearLayout) findViewById(R.id.main_footer_frame);
		mViewCount = mScrollLayout.getChildCount();
		mButtons = new RadioButton[mViewCount];
		mHeadTitles = getResources().getStringArray(R.array.head_titles);

		for (int i = 0; i < mViewCount; i++) {
			mButtons[i] = (RadioButton) frame.getChildAt(i);
			mButtons[i].setTag(i);
			mButtons[i].setChecked(false);
			mButtons[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int pos = (Integer) (v.getTag());
					// 点击当前项刷新
					if (mCurSel == pos) {
						switch (pos) {
						case 0:

							break;
						case 1:
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
						}
						mScrollLayout.snapToScreen(pos);
					}
				}
			});
		}

		// 设置第一显示屏
		mCurSel = 0;
		mButtons[mCurSel].setChecked(true);

		mScrollLayout.SetOnViewChangeListener(new OnViewChangeListener() {
			@Override
			public void OnViewChange(int viewIndex) {
				switch (viewIndex) {
				case 0:

					break;
				case 1:
					break;
				case 2:
					break;
				}
				setCurPoint(viewIndex);
			}
		});

	}

	/**
	 * 设置底部栏当前焦点
	 * 
	 * @param index
	 */
	private void setCurPoint(int index){
		if (index < 0 || index > mViewCount - 1 || mCurSel == index)
			return;
		
		mButtons[mCurSel].setChecked(false);
		mButtons[index].setChecked(true);
		mHeadTitle.setText(mHeadTitles[index]);
		mCurSel = index;
	}
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

}
