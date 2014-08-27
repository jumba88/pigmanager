package com.honglang.app.widget;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.utils.MarkerView;
import com.honglang.app.R;

public class ChartMarkerView extends MarkerView {

	private TextView tvContent;
	
	public ChartMarkerView(Context context, int layoutResource) {
		super(context, layoutResource);
		tvContent = (TextView) findViewById(R.id.tvContent);
	}

	@Override
	public void refreshContent(int xIndex, float value, int dataSetIndex) {
		tvContent.setText("" + value);

	}

}
