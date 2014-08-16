package com.itheima.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	ViewPager mViewPager;
	TextView tvDescription;
	LinearLayout ll_points;
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		tvDescription =(TextView)findViewById(R.id.tv_images_description);
		ll_points = (LinearLayout) findViewById(R.id.ll_points);
		
	}
}
