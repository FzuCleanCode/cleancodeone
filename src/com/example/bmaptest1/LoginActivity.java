package com.example.bmaptest1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private String result;
	private Button regist;
	private Button login;
	private TextView username;
	private TextView password;
	private User user;
	private Handler myhandle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loginin);
		
	
		login=(Button)findViewById(R.id.login);
		myhandle=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0x1122) {
					try {
						JSONArray array=new JSONArray(result);
						JSONObject object=array.getJSONObject(0);
						if ("true".equals(object.getString("login"))) {
							Bundle bundle=new Bundle();
							bundle.putSerializable("myself", user);
							Intent intent=new Intent(LoginActivity.this,MainActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}else{
							Toast.makeText(LoginActivity.this, "’À∫≈ªÚ√‹¬Î¥ÌŒÛ", 2).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				user=new User();
				// TODO Auto-generated method stub
				username=(TextView)findViewById(R.id.username);
				password=(TextView)findViewById(R.id.password);
				user.setId(username.getText().toString());
				user.setPassword(password.getText().toString());
				new Thread(){
					public void run() {
						Looper.prepare();
						result=UserServlet.judgeloginin(user.getId(),user.getPassword());
						myhandle.sendEmptyMessage(0x1122);
					};
				}.start();
			}
		});
		
		regist=(Button)findViewById(R.id.regist);
		regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
}
