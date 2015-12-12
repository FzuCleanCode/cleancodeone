package com.example.bmaptest1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.bmaptest1.MyOrientationListener.OnOrientationListener;


public class MainActivity extends Activity {  
	private String result;
	private MapView mMapView = null; 
    private BaiduMap mbaimap;
    private User user,fuser,fri;
    private LocationClient mLocClient;
    private LocationMode mCurrentMode;
	private BitmapDescriptor mCurrentMarker;
	private Button requestLocButton;
	private boolean isFirstIn=true;
	private MyLocationListener mLocationListener;
	private Context context;
	private double myLatitude;
	private double  myLongitude; 
	private MyOrientationListener myOrientationListener;
	private BitmapDescriptor mIconLocation;
	private float mCurrenX;
	private Button busline,search,friend;
	private Intent intent;
	Thread mythread;
	private Handler myhander;
    private List<User> friends;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState); 
        Deaboway appstate=(Deaboway)this.getApplication();
        appstate.addActivity(this);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());  
        setContentView(R.layout.activity_main); 
        this.context=this;
        intent=this.getIntent();
        initView(); 
        initLocation();
        busline=(Button)findViewById(R.id.busline);
        search=(Button)findViewById(R.id.search);
        friend=(Button)findViewById(R.id.friend);
 
        busline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, BuslineActivity.class);
				startActivity(intent);
				
			}
		});
      
       search.setOnClickListener(new OnClickListener() {
		
		@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
				
				Intent intent = new Intent(MainActivity.this, RouteActivity.class);
				startActivity(intent);
			}
       });
       friend.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bundle bundle=new Bundle(); 
			bundle.putSerializable("user",user);
			Intent fIntent=new Intent(MainActivity.this,FriendActivity.class);
			fIntent.putExtras(bundle);
			startActivity(fIntent);
		}
	});
    }
	private void initLocation() {
		// TODO Auto-generated method stub
		mLocClient=new LocationClient(this);
		mLocationListener=new MyLocationListener();
		mLocClient.registerLocationListener(mLocationListener);
		mCurrentMode=LocationMode.NORMAL;
		LocationClientOption option=new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mIconLocation=BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
		myOrientationListener=new MyOrientationListener(context);
		myOrientationListener.setmOnOrientationListener(new OnOrientationListener() {
			
			@Override
			public void onOrientationChange(float x) {
				// TODO Auto-generated method stub
				mCurrenX=x;
			}
		});
		
		
		
	}
	private void initView() {//设置初始化比例
		// TODO Auto-generated method stub
    	mMapView = (MapView) findViewById(R.id.bmapView); 
    	mbaimap=mMapView.getMap();
    	MapStatusUpdate msu=MapStatusUpdateFactory.zoomTo(15.0f);
    	mbaimap.setMapStatus(msu);
	}
	
	@Override  
    protected void onDestroy() {  
		mLocClient.stop();
		// 关闭定位图层
		mbaimap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
         
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	mbaimap.setMyLocationEnabled(true);
    	if (!mLocClient.isStarted()) {
    		mLocClient.start();
		}
    	myOrientationListener.start();
    	
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	mbaimap.setMyLocationEnabled(false);
    	mLocClient.stop();
    	myOrientationListener.stop();
    	
    }
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
     }  
    
    private class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			MyLocationData data=new MyLocationData.Builder()//
			.direction(mCurrenX)//
			.accuracy(location.getRadius())//
			.latitude(location.getLatitude())//
			.longitude(location.getLongitude()).build();
			mbaimap.setMyLocationData(data);
//			System.out.println(mCurrenX);
			
			/*设置自定义图标*/
			MyLocationConfiguration configuration=new MyLocationConfiguration(mCurrentMode, true, mIconLocation);
			mbaimap.setMyLocationConfigeration(configuration);
			
			myLatitude=location.getLatitude();
			myLongitude=location.getLongitude();
			if (isFirstIn) {
				LatLng latLng=new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
				mbaimap.animateMapStatus(msu);
				isFirstIn=false;
				Toast.makeText(context, location.getAddrStr(), 2).show();
			}
			mythread=new Thread(){
				public void run() {
					if (true) {
						Log.e("tag", myLongitude+"");
					}
					user=new User();
					user=(User)intent.getSerializableExtra("myself");
					user.setIsonline("1");
					user.setLatitude(String.valueOf(myLatitude));
					user.setLongitude(String.valueOf(myLongitude));
					UserServlet.loginin(user);
				};
			};
			mythread.start();
			
			
		}
    	
    }
    

   	@Override
   	public boolean onCreateOptionsMenu(Menu menu) {
   		// TODO Auto-generated method stub
   		getMenuInflater().inflate(R.menu.main, menu);
   		return true;
   	}
    @SuppressWarnings("deprecation")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
	   // TODO Auto-generated method stub
	switch (item.getItemId()) {
	case R.id.normalmenu:
		mbaimap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		break;
	case R.id.sitemenu:
		mbaimap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		break;
	
		
	case R.id.trafficmenu:
		if (item.getTitle().equals("交通地图")) {
			mbaimap.setTrafficEnabled(true);
			item.setTitle("隐藏交通图");
		}else{
			mbaimap.setTrafficEnabled(false);
			item.setTitle("交通地图");
		}
	case R.id.normal:
		mCurrentMode=LocationMode.NORMAL;
		break;
	case R.id.gensui:
		mCurrentMode=LocationMode.FOLLOWING;
		break;
	case R.id.luopan:
		mCurrentMode=LocationMode.COMPASS;
		break;
	case R.id.mylocation:
		relocation();
		break;
	
	default:
		break;
	}
	return super.onOptionsItemSelected(item);
   }
	private void relocation() {
		// TODO Auto-generated method stub
		
		LatLng latLng=new LatLng(myLatitude,myLongitude);
		System.out.println(latLng);
		mbaimap.setMyLocationEnabled(true);
		MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
		mbaimap.animateMapStatus(msu);
		
	}

    
}