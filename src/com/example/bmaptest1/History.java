package com.example.bmaptest1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class History extends Activity {
	
	
	private SQLiteDatabase dbRead;
	private ListView lv;
	private StringBuilder response;
	private HistoryAdapter adapter;
	private HistoryEntity history;
	private List<HistoryEntity> dataList = new ArrayList<HistoryEntity>();
	private static SQLiteDatabase dbWrite;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg)
	{
			
			if(msg.what == 0 )
    			{
				//起到刷新数据库的作用
				Intent intent2 = new Intent(History.this,History.class);
				startActivity(intent2);
				finish();
				
				dbWrite.delete("history", "expressNo=?", new String[]{history.getExpressNo()});
				
					ContentValues cv = new ContentValues();
					cv.put("companyName",history.getCompanyName() );
					cv.put("expressNo",history.getExpressNo());
					cv.put("companyCode", history.getCompanyCode());
					dbWrite.insert("history", null, cv);
					cv.clear();
				
				
    				Intent intent = new Intent(History.this,Result.class);
    				intent.putExtra("jsonStr", msg.obj.toString());
    				startActivity(intent);
    				overridePendingTransition(R.drawable.push_up_in, R.drawable.push_up_out);
    				
    			
    			}
			else if(msg.what == -1)
			{
				
    		}
			
	}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history_layout);
		dbRead = twoActivity.db.getReadableDatabase();
		dbWrite = twoActivity.db.getReadableDatabase();
		Cursor c = dbRead.query("history", new String[]{"companyName","expressNo","companyCode"},null , null, null, null,null);
		
		while(c.moveToNext())
		{
			HistoryEntity historyEntity = new HistoryEntity();
			historyEntity.setCompanyName(c.getString(c.getColumnIndex("companyName")));
			historyEntity.setExpressNo(c.getString(c.getColumnIndex("expressNo")));
			historyEntity.setCompanyCode(c.getString(c.getColumnIndex("companyCode")));
			dataList.add(historyEntity);
			
		}
		lv = (ListView) findViewById(R.id.historyListView);
		adapter = new HistoryAdapter(this, dataList);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			
				history = (HistoryEntity) adapter.getItem(position);
				History.this.sendRequestWithHttpURLConnection(history.getCompanyCode(), history.getExpressNo());
			}
		});
		
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(History.this);
				dialog.setTitle("提醒").setMessage("您确定要删除该项吗？").setNegativeButton("取消", null);
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Cursor c = dbRead.query("history", null, null, null, null, null, null);
						c.moveToPosition(position);
						
						int itemId = c.getInt(c.getColumnIndex("_id"));
						
						dbWrite.delete("history", "_id = ?", new String[]{itemId+""});
						
						Intent intent = new Intent(History.this,History.class);
						
						startActivity(intent);
						finish();
						
					}
					
					
				}).show();
				return true;
			}
		});

		
	}
	
	
	 private void sendRequestWithHttpURLConnection(final String comCode,final String expressNo)
	    {
		 
	    	
	    	new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					HttpURLConnection connection = null;
					final String EXPRESS_API_HOST = 
							"http://api.ickd.cn/?id=102616&secret=16135ea51cb60246eff620f130a005bd&com=";
					StringBuffer sb = new StringBuffer();
					sb.append(EXPRESS_API_HOST).append(comCode).append("&nu=").append(expressNo).append("&type=json&encode=utf8&ord=asc");
					String urlAddress = sb.toString();
					try 
					{
						URL url = new URL(urlAddress);
						connection = (HttpURLConnection)url.openConnection();
						connection.setRequestMethod("GET");
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
						JSONObject obj=new JSONObject(response.toString());
						Message message = new Message();
						if(!(obj.get("status").equals("0")))
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
	    
	    

}
