package com.example.expresssection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Result extends Activity {
	
	public Toast mToast=null;
	private ListView lv;
	private ResultAdapter adapter;
	private List<ResultEntity> rs = new ArrayList<ResultEntity>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result_layout);

		initView();
	}
	
	private void initView()
	{
		lv = (ListView) findViewById(R.id.followListView); 
		lv.setAdapter(adapter);
		String data = getIntent().getExtras().getString("jsonStr");
		parse(data);
		
		
	}
	
	public void parse(String jsonStr)
	{
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<ResultEntity>>(){}.getType();
		try 
		{
			JSONObject obj = new JSONObject(jsonStr);
			System.out.println(obj.toString());
			if(!(obj.get("status").equals("0")))
			{
				
				rs = gson.fromJson(obj.getString("data"), type);
				Collections.reverse(rs);
				
//				for(Iterator<ResultEntity> iter = rs.iterator();iter.hasNext();)
//				{
//					String time = iter.next().getTime();
//					String context = iter.next().getContext();
//					System.out.println(time + "," + context);
//				}
				
				adapter = new ResultAdapter(Result.this, rs);
				lv.setAdapter(adapter);
				
				TextView followTV = (TextView) findViewById(R.id.follow_textview);
				followTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_success,0);
				
			    findViewById(R.id.colorLine).setBackgroundResource(R.drawable.red_line_green);
			    findViewById(R.id.dot_full).setVisibility(View.VISIBLE);
				
			}
			else
			{
				showToast(obj.getString("message"));
				
			}
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	
	}
	
	public void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	

}
