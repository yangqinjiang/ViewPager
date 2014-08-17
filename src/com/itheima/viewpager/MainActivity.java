package com.itheima.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

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

			// ״̬��
			v = new View(this);
			v.setBackgroundResource(R.drawable.point_background);
			v.setEnabled(false);
			LayoutParams lp = new LayoutParams(8, 8);
			lp.leftMargin = 5;
			v.setLayoutParams(lp);
			ll_points.addView(v);
		}
		// ��ʼ״̬
		previousPointPostion=0;
		ll_points.getChildAt(previousPointPostion).setEnabled(true);
		tvDescription.setText(imageDescriptions[0]);

		//
		mViewPager.setAdapter(new MyPagerAdapter());

		mViewPager.setOnPageChangeListener(this);

		//�����ʲô�õ�??
		//���м俪ʼ??
		int m =(Integer.MAX_VALUE/2)%imageDescriptions.length;
		int itemIndex =Integer.MAX_VALUE/2 -m;
		mViewPager.setCurrentItem(itemIndex);
	}

	private int[] getImageResIds() {
		return new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
	}

	private String[] getImageDescriptions() {
		return new String[] { "���������ף��ҾͲ��ܵ���", "�����ֻ��������ٳ������ϸ������˴�ϳ�",
				"���ر�����Ӱ�������", "������TV�������", "��Ѫ��˿�ķ�ɱ" };
	}

	/**
	 * ��Ԥ���ع���
	 * 
	 */
	class MyPagerAdapter extends PagerAdapter {

		// view pager�ĳ���
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;//�������һ���
		}

		// �ж��������� �Ƿ�ͬһ��
		// ���view��obj���,�͸���view,��֮��ȡobj
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		// ����������������Ŀؼ�
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position % imageViews.size()));
		}

		// Ԥ���ؿؼ�
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
		if(previousPointPostion==realPosition)return;
		tvDescription.setText(imageDescriptions[realPosition]);
		ll_points.getChildAt(realPosition).setEnabled(true);
		ll_points.getChildAt(previousPointPostion).setEnabled(false);
		previousPointPostion=realPosition;
		
	}
	private int previousPointPostion;//��סǰһ�����λ��

}
