package com.example.bmaptest1;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class title extends LinearLayout{
	public title(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.titlef, this);
		
	    Button titleBack = (Button)findViewById(R.id.title_back);
	
		
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)getContext()).finish();
				
			}
		});
		

		
		
	}
	
}
