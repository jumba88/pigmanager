package com.honglang.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.honglang.app.AppContext;
import com.honglang.app.AppManager;
import com.honglang.app.R;
import com.honglang.app.R.layout;
import com.honglang.app.R.menu;
import com.honglang.app.adapter.NewsPagerAdapter;
import com.honglang.app.fragment.HeadlineFragment;
import com.honglang.app.fragment.PigPriceFragment;
import com.honglang.app.utils.UIHelper;
import com.honglang.app.viewpagerindicator.TabPageIndicator;
import com.honglang.app.widget.ScrollLayout;
import com.honglang.app.widget.ScrollLayout.OnViewChangeListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class Main extends FragmentActivity {

	private ScrollLayout mScrollLayout;
	private RadioButton[] mButtons;
	private String[] mHeadTitles;
	private int mViewCount;
	private int mCurSel;

	private TextView mHeadTitle;
	private ProgressBar mHeadProgress;
	
	private TabPageIndicator newsIndicator;
	private ViewPager newsPager;
	private static final String[] CONTENT = new String[] { "头条", "猪价", "生猪", "育种", "饲料", "动保", "设备" };
	private List<Fragment> newsFragments;
	private NewsPagerAdapter newsAdapter;
	
	private TabPageIndicator pIndicator;
	private ViewPager pricePager;
	private static final String[] pCONTENT = new String[] { "生猪价格走势图", "玉米价格走势图" };
	private List<Fragment> pFragments;
	private NewsPagerAdapter pAdapter;

	private RadioButton rbNews;
	private RadioButton rbPrice;
	private RadioButton rbManufacturer;
	private RadioButton rbWiki;
	private RadioButton rbProfile;

	private AppContext appContext;// 全局Context

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_main);

		appContext = (AppContext) getApplication();
		// 网络连接判断
		if (!appContext.isNetworkConnected())
			UIHelper.ToastMessage(this, R.string.network_not_connected);

		initHeadView();
		initFootBar();
		initScrollLayout();
		initNews();
		initPrice();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mViewCount == 0) {
			mViewCount = 5;
		}
		if (mCurSel == 0 && rbNews.isChecked()) {
			rbNews.setChecked(true);
			rbPrice.setChecked(false);
			rbManufacturer.setChecked(false);
			rbWiki.setChecked(false);
			rbProfile.setChecked(false);
		}
		
		mScrollLayout.setIsScroll(appContext.isScroll());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
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
					}
					mScrollLayout.snapToScreen(pos);
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
	
	/**
	 * 初始化资讯
	 */
	private void initNews(){
		newsIndicator = (TabPageIndicator) findViewById(R.id.news_tabIndicator);
		newsPager = (ViewPager) findViewById(R.id.news_viewpager);
		
		newsFragments = new ArrayList<Fragment>();
		for (int i = 0; i < CONTENT.length; i++) {
			newsFragments.add(new HeadlineFragment());
		}
		
		newsAdapter = new NewsPagerAdapter(getSupportFragmentManager(), newsFragments, CONTENT);
		
		newsPager.setAdapter(newsAdapter);
		newsIndicator.setViewPager(newsPager);
	}
	
	/**
	 * 初始化行情
	 */
	private void initPrice(){
		pIndicator = (TabPageIndicator) findViewById(R.id.price_tabIndicator);
		pricePager = (ViewPager) findViewById(R.id.price_viewpager);
		
		pFragments = new ArrayList<Fragment>();
		for (int i = 0; i < pCONTENT.length; i++) {
			pFragments.add(new PigPriceFragment());
		}
		
		pAdapter = new NewsPagerAdapter(getSupportFragmentManager(), pFragments, pCONTENT);
		
		pricePager.setAdapter(pAdapter);
		pIndicator.setViewPager(pricePager);
	}

}
