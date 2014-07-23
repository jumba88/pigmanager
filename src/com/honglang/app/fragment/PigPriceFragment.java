package com.honglang.app.fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.honglang.app.R;
import com.honglang.app.widget.MyMarkerView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PigPriceFragment extends Fragment implements OnChartValueSelectedListener {

	private LineChart mChart;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_price_pig, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
        // create a color template for one dataset with only one color
        ColorTemplate ct = new ColorTemplate();
//        ct.addColorsForDataSets(new int[] {
//            R.color.colorful_1
//        }, this);
        ct.addDataSetColors(new int[] {R.color.colorful_1}, getActivity());

        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setColorTemplate(ct);
        
     // if enabled, the chart will always start at zero on the y-axis
        mChart.setStartAtZero(false);

        // disable the drawing of values into the chart
        mChart.setDrawYValues(false);

        mChart.setLineWidth(4f);
        mChart.setCircleSize(4f);

        // set the number of y-legend entries the chart should have
        mChart.setYLegendCount(6);

        // enable value highlighting
        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        mChart.setMarkerView(mv);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mChart.setHighlightIndicatorEnabled(false);

        // set the line to be drawn like this "- - - - - -"
        mChart.disableDashedLine();
	}

	@Override
	public void onValuesSelected(Entry[] values, Highlight[] highlights) {
		
	}

	@Override
	public void onNothingSelected() {
		
	}
	
	private void loadData(){
		new Thread(){
			public void run(){
				
			}
		}.start();
	}

}