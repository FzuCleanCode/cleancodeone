package com.example.expresssection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends Activity {
	
	private Button currCom;
	private Button search;
	private EditText expressId;
	private String comId = "shentong";
	private String comName;
	public Toast mToast=null;
    private int coms_rep = 99;
    public StringBuilder response;
    private String[] companyCodes;
  
    public static DbHelper db;
    private static SQLiteDatabase dbWrite,dbRead;
    
    
    
    
    private Handler handler = new Handler(){
		public void handleMessage(Message msg)
	{
			if(msg.what == 0 )
    			{

				
				dbWrite.delete("history", "expressNo=?", new String[]{expressId.getText().toString()});
				ContentValues cv = new ContentValues();
				cv.put("companyName", comName);
				cv.put("expressNo",expressId.getText().toString());
				cv.put("companyCode", comId);
				dbWrite.insert("history", null, cv);
				cv.clear();
				
    				Intent intent = new Intent(MainActivity.this,Result.class);
    				intent.putExtra("jsonStr", msg.obj.toString());
    				startActivity(intent);
    				overridePendingTransition(R.drawable.push_up_in, R.drawable.push_up_out);
    				
    			
    			}
			else if(msg.what == -1)
			{
				
				showToast("亲，出错了哦");
    		}
			
	}
	};
	
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
     
       
        db = new DbHelper(this, "db", null, 1);
        dbWrite = db.getWritableDatabase();
        dbRead = db.getReadableDatabase();
        expressId = (EditText) findViewById(R.id.expressId);
        //当前公司选择
        currCom = (Button) findViewById(R.id.currCom);
        currCom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(MainActivity.this,Com.class), coms_rep);
				
				
			}
		});
        
       
        search = (Button)findViewById(R.id.search_btn);
        search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				//dbWrite.delete("history",null, null);
				MainActivity.this.sendRequestWithHttpURLConnection(comId, expressId.getText().toString());
				
			}
		});
        
        
        Button historyBtn = (Button) findViewById(R.id.history);
        historyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,History.class);
				startActivity(intent);
				
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(resultCode==RESULT_OK){
			if(requestCode==coms_rep){
			//鎺ユ敹蹇�掑叕鍙稿悕绉板拰缂栧彿
			currCom.setText(data.getExtras().getString("comName"));
			comId=data.getExtras().getString("comId");
			comName=data.getExtras().getString("comName");
			}
    	}
  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
