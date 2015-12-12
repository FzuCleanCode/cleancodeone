package com.example.phonesection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TitleLayout extends LinearLayout {

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
	    Button titleBack = (Button)findViewById(R.id.title_back);
		Button titleEdit = (Button)findViewById(R.id.title_edit);
		
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)getContext()).finish();
				
			}
		});
		
  titleEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(),AddNumberActivity.class);
				getContext().startActivity(intent);
				//((Activity) getContext()).finish();
				
			}
		});
		
		
	}
	
	

}