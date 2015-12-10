package com.example.expresssection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Com extends Activity {
	
	private ListView lv;
	
	//private String[] express = {"申通快递","圆通快递","顺丰快递","邮政EMS","汇通快递"};
	private ComsAdapter adapter;
	
	public String[] companyLogos ;
	public String[] companyCodes ;
	public String[] companyNames ;
	
	//public String[] companyLogos = {"shentong"} ;
	//public String[] companyCodes = {"shentong"} ;
	//public String[] companyNames = {"申通","圆通","顺丰"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.com_layout);
		lv = (ListView) findViewById(R.id.coms_listView);
		companyCodes = getResources().getStringArray(R.array.company_code);
		companyNames = getResources().getStringArray(R.array.company_name);
		companyLogos = getResources().getStringArray(R.array.company_logo);
		
		adapter = new ComsAdapter(this,companyCodes,companyNames,companyLogos);
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String> (Com.this,android.R.layout.simple_list_item_1,express);
		
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("comName", companyNames[position]);
				intent.putExtra("comId", companyCodes[position]);
				
				setResult(RESULT_OK, intent);
				onBackPressed();
				
			}
			
		});
		
		
		
		
		
	}
}
