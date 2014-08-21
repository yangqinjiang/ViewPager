package com.itheima.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPageChangeListener {

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
		};
	};

	private boolean isLoop =true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isLoop) {
					SystemClock.sleep(2000);
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
	}
	protected void onDestroy() {
		super.onDestroy();
		isLoop=false;
	};

	ViewPager mViewPager;
	TextView tvDescription;
	LinearLayout ll_points;
	List<ImageView> imageViews;
	String[] imageDescriptions;
	int[] imageResIDs;

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		tvDescription = (TextView) findViewById(R.id.tv_images_description);
		ll_points = (LinearLayout) findViewById(R.id.ll_points);

		imageViews = new ArrayList<ImageView>();
		imageResIDs = getImageResIds();
		imageDescriptions = getImageDescriptions();

		ImageView iv;
		View v;
		//
		for (int i = 0; i < imageResIDs.length; i++) {
			iv = new ImageView(this);
			iv.setImageResource(imageResIDs[i]);
			imageViews.add(iv);

			// 状态条
			v = new View(this);
			v.setBackgroundResource(R.drawable.point_background);
			v.setEnabled(false);
			LayoutParams lp = new LayoutParams(8, 8);
			lp.leftMargin = 5;
			v.setLayoutParams(lp);
			ll_points.addView(v);
		}
		// 初始状态
		previousPointPostion = 0;
		ll_points.getChildAt(previousPointPostion).setEnabled(true);
		tvDescription.setText(imageDescriptions[0]);

		//
		mViewPager.setAdapter(new MyPagerAdapter());

		mViewPager.setOnPageChangeListener(this);

		// 这个有什么用的??
		// 从中间开始??
		int m = (Integer.MAX_VALUE / 2) % imageDescriptions.length;
		int itemIndex = Integer.MAX_VALUE / 2 - m;
		mViewPager.setCurrentItem(itemIndex);
	}

	private int[] getImageResIds() {
		return new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
	}

	private String[] getImageDescriptions() {
		return new String[] { "巩俐不低俗，我就不能低俗", "扑树又回来啦！再唱经典老歌引万人大合唱",
				"揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀" };
	}

	/**
	 * 有预加载功能
	 * 
	 */
	class MyPagerAdapter extends PagerAdapter {

		// view pager的长度
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;// 无限左右滑动
		}

		// 判断两个对象 是否同一个
		// 如果view和obj相等,就复用view,反之就取obj
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		// 销毁左右两边以外的控件
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position % imageViews.size()));
		}

		// 预加载控件
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews.get(position % imageViews.size()));
			return imageViews.get(position % imageViews.size());
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int postion) {
		int realPosition = postion % imageViews.size();
		if (previousPointPostion == realPosition)
			return;
		tvDescription.setText(imageDescriptions[realPosition]);
		ll_points.getChildAt(realPosition).setEnabled(true);
		ll_points.getChildAt(previousPointPostion).setEnabled(false);
		previousPointPostion = realPosition;

	}

	private int previousPointPostion;// 记住前一个点的位置

}
