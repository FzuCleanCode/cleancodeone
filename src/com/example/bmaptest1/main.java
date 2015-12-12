package com.example.bmaptest1;


import com.example.bmaptest1.R;
import com.example.bmaptest1.main.MyAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class main extends Activity implements OnClickListener,OnPageChangeListener {
	
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;
	
	/**
	 * װ����ImageView����
	 */
	private ImageView[] tips;
	
	/**
	 * װImageView����
	 */
	private ImageView[] mImageViews;
	
	/**
	 * ͼƬ��Դid
	 */
	private int[] imgIdArray ;
	
	/**λ�÷���*/
	private LinearLayout positionLayout;
	
	/**��ݷ���*/
	private LinearLayout expressLayout;
	
	/**����Ԥ��*/
	private LinearLayout weatherLayout;
	
	/**�������ͨ*/
	private LinearLayout phoneNumLayout;
	
	/**���渣��*/
	private LinearLayout playFZLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		positionLayout = (LinearLayout)findViewById(R.id.activity_main_position);
		positionLayout.setOnClickListener(this);
		expressLayout = (LinearLayout)findViewById(R.id.activity_main_express);
		expressLayout.setOnClickListener(this);
		weatherLayout = (LinearLayout)findViewById(R.id.activity_main_weather);
		weatherLayout.setOnClickListener(this);
		phoneNumLayout = (LinearLayout)findViewById(R.id.activity_main_number);
		phoneNumLayout.setOnClickListener(this);
		playFZLayout = (LinearLayout)findViewById(R.id.activity_main_play);
		playFZLayout.setOnClickListener(this);
		
		ViewGroup group = (ViewGroup)findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		
		//����ͼƬ��ԴID
		imgIdArray = new int[]{R.drawable.item01, R.drawable.item02, R.drawable.item03, R.drawable.item04,
				R.drawable.item05,R.drawable.item06, R.drawable.item07, R.drawable.item08, R.drawable.item09};
		
		
		//�������뵽ViewGroup��
		tips = new ImageView[imgIdArray.length];
		for(int i=0; i<tips.length; i++){
			ImageView imageView = new ImageView(this);
	    	imageView.setLayoutParams(new LayoutParams(10,10));
	    	tips[i] = imageView;
	    	if(i == 0){
	    		tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
	    	}else{
	    		tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
	    	}
	    	
	    	 group.addView(imageView);
		}
		
		
		//��ͼƬװ�ص�������
		mImageViews = new ImageView[imgIdArray.length];
		for(int i=0; i<mImageViews.length; i++){
			ImageView imageView = new ImageView(this);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}
		
		//����Adapter
		viewPager.setAdapter(new MyAdapter());
		//���ü�������Ҫ�����õ��ı���
		viewPager.setOnPageChangeListener(this);
		//����ViewPager��Ĭ����, ����Ϊ���ȵ�100���������ӿ�ʼ�������󻬶�
		viewPager.setCurrentItem((mImageViews.length) * 100);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == positionLayout) {
			Intent intent = new Intent(main.this, LoginActivity.class);
			startActivity(intent);
		}else if (v == expressLayout) {
			Intent intent = new Intent(main.this, twoActivity.class);
			startActivity(intent);
		}else if (v == weatherLayout) {
			Intent intent = new Intent(main.this, ChooseAreaActivity.class);
			startActivity(intent);
		}else if (v == phoneNumLayout) {
			Intent intent = new Intent(main.this, thirdActivity.class);
			startActivity(intent);
		}else if (v == playFZLayout) {
			Intent intent = new Intent(main.this, fourActivity.class);
			startActivity(intent);
		}
	}
	
	public class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			//((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
			
		}

		/**
		 * ����ͼƬ��ȥ���õ�ǰ��position ���� ͼƬ���鳤��ȡ�����ǹؼ�
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mImageViews[position % mImageViews.length];
		}
		
		
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
	}
	
	/**
	 * ����ѡ�е�tip�ı���
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems){
		for(int i=0; i<tips.length; i++){
			if(i == selectItems){
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

}
