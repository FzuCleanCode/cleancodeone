package com.example.bmaptest1;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FriendActivity extends Activity {
	private String result;
	private Handler myhandle; 
	private User user,fu;
	private Intent intent;
	private List<User>friends;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist);
		intent=getIntent();
		user=new User();
		user=(User)intent.getSerializableExtra("user");
		if (true) {
			Log.e("111111", user.toString());
		}
		myhandle=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0x1122) {
					try {
						JSONObject object;
						
						friends=new ArrayList<User>();
						JSONArray array=new JSONArray(result);
						for (int i = 0; i < array.length(); i++) {
							object=array.getJSONObject(i);
							fu=new User();
							fu.setId(object.getString("id"));
							fu.setPassword(object.getString("password"));
							fu.setIsonline(object.getString("isonline"));
							fu.setLatitude(object.getString("latitude"));
							fu.setLongitude(object.getString("longitude"));
							friends.add(fu);
						}
						MyAdapter adapter=new MyAdapter(FriendActivity.this, R.layout.item, friends);
						ListView listView=(ListView)findViewById(R.id.friendlist);
						listView.setAdapter(adapter);
						listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								User friend=friends.get(position);
								Bundle bundle=new Bundle();
								Intent intent = new Intent(FriendActivity.this,FLocationActivity.class);
				        		bundle.putSerializable("fri", friend);
				        		intent.putExtras(bundle);
								startActivity(intent);
							}
							
						});
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		};
		
		
		
		new Thread(){
			public void run() {
				Looper.prepare();
				result=UserServlet.getFriends(user);
				myhandle.sendEmptyMessage(0x1122);
			};
		}.start();
	}
}
