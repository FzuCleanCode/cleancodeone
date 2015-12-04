package com.example.compusassist;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private List<Scene> sceneList = new ArrayList<Scene>();
	private StringBuilder response;
	private Handler handler;
	public Toast mToast=null;
	private String jsonContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initScenes();
		handler = new Handler(){
			public void handleMessage(Message msg)
		{
				if(msg.what == 0 )
	    			{
					try {
						JSONObject obj=new JSONObject(jsonContent);
						JSONArray list = obj.getJSONArray("data");
						for(int i = 0;i < list.length();i++)
						{
							JSONObject obj2 = list.getJSONObject(i);
							System.out.println(obj2.getString("name"));
							Scene scene = new Scene(obj2.getString("name"),obj2.getInt("picaddress"),
									obj2.getString("content"),obj2.getString("urladdress"));
							sceneList.add(scene);						
						}
						SceneAdapter adapter = new SceneAdapter(MainActivity.this,R.layout.scene_item,sceneList);						
						ListView listView = (ListView)findViewById(R.id.list_view);						
						listView.setAdapter(adapter);						
						listView.setOnItemClickListener(new OnItemClickListener() {							
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long id) {							
								Scene scene = sceneList.get(position);
								Bundle bundle=new Bundle();
								Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
								bundle.putSerializable("scene", scene);
								intent.putExtras(bundle);
								startActivity(intent);	
							}					
						});	
						
					} catch (Exception e) {
						
					}
					
					
	    			System.out.println(msg.obj.toString());
	    			
	    			}
				else if(msg.what == -1)
				{
					showToast("Êý¾ÝÇëÇó´íÎó");
	    		}
				
		}
		};
		
		
		
		
	}
	
	
	private void initScenes()
	{
          new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				String urlAddress = "http://121.42.211.235/project/scene.php";
				try 
				{
					URL url = new URL(urlAddress);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("POST");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
				
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					response = new StringBuilder();
					String line;
					while((line = reader.readLine())!=null)
					{
						
						response.append(line);
						
					}
					//System.out.println("{data:["+response.toString()+"]}");
					jsonContent = "{data:["+response.toString()+"]}";
					System.out.println(jsonContent);
					JSONObject obj=new JSONObject(jsonContent);
//					JSONArray list = obj.getJSONArray("data");
//					for(int i = 0;i < list.length();i++)
//					{
//						JSONObject obj2 = list.getJSONObject(i);
//						System.out.println(obj2.getString("name"));
//						Scene scene = new Scene(obj2.getString("name"),obj2.getInt("picaddress"),
//								obj2.getString("content"),obj2.getString("urladdress"));
//						sceneList.add(scene);
//					}
//					
					Message message = new Message();
					if(!(obj.get("data").equals(null)))
					{
						message.what = 0;
						message.obj = response.toString();
						handler.sendMessage(message);
					}
					else
					{
						handler.sendEmptyMessage(-1);
					}
					 
					
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(connection != null)
					{
						connection.disconnect();
					}
										
				}
			
				
			}
		}).start();
          
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
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
