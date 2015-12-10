package com.example.bmaptest1;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendActivity extends Activity {
	private String result,result1;
	private Handler addHandler, myhandle,handler; 
	private User user,fu;
	private Intent intent;
	private List<User>friends,msgs;
	private EditText DEditText ;
	private TextView textView;
	private Thread firstThread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Deaboway appstate=(Deaboway)this.getApplication();
        appstate.addActivity(this);
		
		setContentView(R.layout.friendlist);
		intent=getIntent();
		user=new User();
		user=(User)intent.getSerializableExtra("user");
		
		
		firstThread=new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				result1=UserServlet.getMessage(user);
				handler.sendEmptyMessage(0x1122);
			}
		};
		firstThread.setPriority(2);
		firstThread.start();
		
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0x1122) {
					try {
						JSONObject object;
						
						msgs=new ArrayList<User>();
						JSONArray array=new JSONArray(result1);
						for (int i = 0; i < array.length(); i++) {
							object=array.getJSONObject(i);
							fu=new User();
							fu.setId(object.getString("id"));
							fu.setPassword(object.getString("password"));
							fu.setIsonline(object.getString("isonline"));
							fu.setLatitude(object.getString("latitude"));
							fu.setLongitude(object.getString("longitude"));
							isAdd(fu);
						}
						
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		};
		
		
		
		
		
		
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
								if (friend.getIsonline().equals("0")) {
									Toast.makeText(FriendActivity.this, "ta处于离线状态", 2).show();
								}else{
									Bundle bundle=new Bundle();
									Intent intent = new Intent(FriendActivity.this,FLocationActivity.class);
					        		bundle.putSerializable("fri", friend);
					        		intent.putExtras(bundle);
									startActivity(intent);
								}
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
	protected void isAdd(User fu2) {
		// TODO Auto-generated method stub
		textView= new TextView(this);
		textView.setText(fu2.getId());
		new AlertDialog.Builder(this).setTitle("是否添加对方为好友")
		.setIcon(android.R.drawable.ic_dialog_info).setView(textView)
		.setPositiveButton("确定",new sure())
		.setNegativeButton("取消",null).show();
		
	}
	private class sure implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			new Thread(){
				public void run() {
					String friendid=textView.getText().toString();
					UserServlet.sureFriend(user.getId(),friendid);
					
				};
			}.start();
		}
		
	}
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.friend, menu);
   		return true;
		
	}
	 public boolean onOptionsItemSelected(MenuItem item){
		 switch (item.getItemId()) {
		case R.id.online:
			online();
			break;
		case R.id.nonline:
			
			getNonlineFriend();
			break;
		case R.id.allfriend:
			getAllFriend();
			break;
			
		case R.id.addfriend:
			addfriend();
			break;
		case R.id.exit:
			user.setIsonline("0");
			
			new Thread(){
				public void run() {
					UserServlet.loginin(user);
					
					Deaboway appState = (Deaboway)getApplicationContext();
			        appState.finishAll();
				};
				
			}.start();
			
			break;
		default:
			break;
		}
		 return super.onOptionsItemSelected(item);
	 }
	private void addfriend() {
		// TODO Auto-generated method stub
		DEditText= new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入对方ID")
		.setIcon(android.R.drawable.ic_dialog_info).setView(DEditText)
		.setPositiveButton("确定",new Alert())
		.setNegativeButton("取消",null).show();
	}
	private void getNonlineFriend() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				result=UserServlet.getNonlineFriends(user);
				myhandle.sendEmptyMessage(0x1122);
			}
		}.start();
	}
	private void getAllFriend() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				Looper.prepare();
				result=UserServlet.getFriends(user);
				myhandle.sendEmptyMessage(0x1122);
			};
		}.start();
		
	}
	private void online() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				Looper.prepare();
				result=UserServlet.getOnlineFriends(user);
				myhandle.sendEmptyMessage(0x1122);
			};
		}.start();
	}
	private class Alert implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			new Thread(){
				public void run() {
					String friendid=DEditText.getText().toString();
					UserServlet.addFriend(user.getId(),friendid);
					
				};
			}.start();
		}
		
	}
}




