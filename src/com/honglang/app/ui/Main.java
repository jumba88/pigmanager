package com.honglang.app.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.honglang.app.ApiClient;
import com.honglang.app.AppContext;
import com.honglang.app.AppException;
import com.honglang.app.AppManager;
import com.honglang.app.R;
import com.honglang.app.R.layout;
import com.honglang.app.R.menu;
import com.honglang.app.adapter.NewsPagerAdapter;
import com.honglang.app.bean.ChartData;
import com.honglang.app.fragment.HeadlineFragment;
import com.honglang.app.fragment.PigPriceFragment;
import com.honglang.app.utils.UIHelper;
import com.honglang.app.viewpagerindicator.TabPageIndicator;
import com.honglang.app.widget.ScrollLayout;
import com.honglang.app.widget.ScrollLayout.OnViewChangeListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	
	private Button btn_Price_pig;
	private Button btn_Price_corn;
	
	private LinearLayout layoutPig;
	private LinearLayout layoutCorn;
	private Chart chartPig;
	private Chart chartCorn;
	private JSONArray pigData;
	private JSONArray cornData;

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
		btn_Price_pig = (Button) findViewById(R.id.price_pig);
		btn_Price_corn = (Button) findViewById(R.id.price_corn);
		
		layoutPig = (LinearLayout) findViewById(R.id.layout1);
		layoutCorn = (LinearLayout) findViewById(R.id.layout2);
		
		chartPig = (Chart) findViewById(R.id.chart1);
		chartCorn = (Chart) findViewById(R.id.chart2);
		
//		chartPig.set
		
		btn_Price_pig.setEnabled(false);
		
		btn_Price_pig.setOnClickListener(btnPriceClick(btn_Price_pig));
		btn_Price_corn.setOnClickListener(btnPriceClick(btn_Price_corn));
		
		new Thread(){
			public void run(){
				Message msg = new Message();
				try {
					ChartData data = appContext.getChartData("100000");
					Log.i("suxoyo", "size="+data.getSize());
					msg.what = data.getSize();
					msg.obj = data;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				
				priceHandler.sendMessage(msg);
			}
		}.start();
		
	}
	
	Handler priceHandler = new Handler(){
		public void handleMessage(Message msg){
			if (msg.what == 2) {
				ChartData data = (ChartData) msg.obj;
				
				pigData = data.getPigdata();
				cornData = data.getCorndata();
				setPigChartData(0);
			}
			super.handleMessage(msg);
		}
	};
	
	/**
	 * 设置生猪价格走势图数据
	 * @param index
	 */
	private void setPigChartData(int index){
		try {
			JSONObject data = pigData.getJSONObject(index);
			JSONArray cDate = data.getJSONArray("cDate");
			JSONArray cData = data.getJSONArray("cData");
			ArrayList<String> xVals = new ArrayList<String>();
			ArrayList<Entry> yVals = new ArrayList<Entry>();
			for (int i = 0; i < cDate.length(); i++) {
				xVals.add(cDate.getString(i));
				float val = (float) cData.getDouble(i);
				yVals.add(new Entry(val, i));
			}
			
			DataSet set = new DataSet(yVals, 0);
			ArrayList<DataSet> datasets= new ArrayList<DataSet>();
			datasets.add(set);
			com.github.mikephil.charting.data.ChartData chartdata = new com.github.mikephil.charting.data.ChartData(xVals, datasets);
			chartPig.setData(chartdata);
			chartPig.invalidate();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private OnClickListener btnPriceClick(final Button btn){
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (btn == btn_Price_pig) {
					btn_Price_pig.setEnabled(false);
					layoutPig.setVisibility(View.VISIBLE);
				} else {
					btn_Price_pig.setEnabled(true);
					layoutPig.setVisibility(View.GONE);
				}
				
				if (btn == btn_Price_corn) {
					btn_Price_corn.setEnabled(false);
					layoutCorn.setVisibility(View.VISIBLE);
				} else {
					btn_Price_corn.setEnabled(true);
					layoutCorn.setVisibility(View.GONE);
				}
			}
		};
	}

}
