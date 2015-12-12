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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends Activity{
	private static final String TAG = "YourApplication"; 
	private static final boolean D = true; 
	private User user;
	private boolean flag=true;
	private String hasRegist;
	private Button Rregist;
	private Handler myhHandler;
	private TextView username,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Deaboway appstate=(Deaboway)this.getApplication();
        appstate.addActivity(this);
		setContentView(R.layout.regist);
		myhHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0x1122) {
					try {
						JSONArray array=new JSONArray(hasRegist);
						JSONObject jsonObject=array.getJSONObject(0);
						if ("true".equals(jsonObject.getString("hasRegist"))) {
							flag=false;
							Toast.makeText(RegistActivity.this, "此账号已经注册", 2).show();
						}else {
							Toast.makeText(RegistActivity.this, "注册成功", 2).show();
							Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
							startActivity(intent);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
	
			}

		};
		Rregist =(Button) findViewById(R.id.Rregist);
		Rregist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag=true;
				new Thread(){
					public void run() {
						Looper.prepare();
						user=new User();
						username=(TextView)findViewById(R.id.Rusername);
						password=(TextView)findViewById(R.id.Rpassword);
						user.setId(username.getText().toString());
						user.setPassword(password.getText().toString());
						hasRegist=UserServlet.hasRegist(user.getId());
						myhHandler.sendEmptyMessage(0x1122);
						if (flag) {
							if (UserServlet.regist(user)) {
								Toast.makeText(RegistActivity.this, "success", 2).show();
							}
				
						}
					};
				}.start();
			
			}
		});
	}
}
