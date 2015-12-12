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
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;
	
	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;
	
	/**
	 * 图片资源id
	 */
	private int[] imgIdArray ;
	
	/**位置服务*/
	private LinearLayout positionLayout;
	
	/**快递服务*/
	private LinearLayout expressLayout;
	
	/**天气预报*/
	private LinearLayout weatherLayout;
	
	/**号码百事通*/
	private LinearLayout phoneNumLayout;
	
	/**游玩福州*/
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
		
		//载入图片资源ID
		imgIdArray = new int[]{R.drawable.item01, R.drawable.item02, R.drawable.item03, R.drawable.item04,
				R.drawable.item05,R.drawable.item06, R.drawable.item07, R.drawable.item08, R.drawable.item09};
		
		
		//将点点加入到ViewGroup中
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
		
		
		//将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		for(int i=0; i<mImageViews.length; i++){
			ImageView imageView = new ImageView(this);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}
		
		//设置Adapter
		viewPager.setAdapter(new MyAdapter());
		//设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);
		//设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
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
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
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
	 * 设置选中的tip的背景
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
