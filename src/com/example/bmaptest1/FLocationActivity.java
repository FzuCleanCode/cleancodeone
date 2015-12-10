package com.example.bmaptest1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

public class FLocationActivity extends Activity implements OnGetGeoCoderResultListener{

	GeoCoder mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;
	Intent intent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Deaboway appstate=(Deaboway)this.getApplication();
        appstate.addActivity(this);
		
		setContentView(R.layout.activity_geocoder);
		intent=this.getIntent();
		User user=(User)intent.getSerializableExtra("fri");
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu=MapStatusUpdateFactory.zoomTo(15.0f);
    	mBaiduMap.setMapStatus(msu);
		// ��ʼ������ģ�飬ע���¼�����
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		
		Float lat=Float.valueOf(user.getLatitude());
		Float log=Float.valueOf(user.getLongitude());
		LatLng center=new LatLng(lat, log);
		// ��Geo����
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(center));
		
	}
	
	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mSearch.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(FLocationActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_geo)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		Toast.makeText(FLocationActivity.this, result.getAddress(),
				Toast.LENGTH_LONG).show();

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private LocationClient mLocClient;
//	private BitmapDescriptor mIconLocation;
//	private LocationMode mCurrentMode;
//	private MyOrientationListener myOrientationListener;
//	private BitmapDescriptor mCurrentMarker;
//	private float mCurrenX;
//	private boolean isFirstIn=true; 
//	private Location location;
//	BaiduMap mBaiduMap = null;
//	MapView mMapView = null;
//	Intent intent ;
//	User user;
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_geocoder);
//
//		// ��ͼ��ʼ��
//		mMapView = (MapView) findViewById(R.id.bmapView);
//		mBaiduMap = mMapView.getMap();
//
//		// ��ʼ������ģ�飬ע���¼�����
//		intent=getIntent();
//		user=(User)intent.getSerializableExtra("fri");
//		Float lat=Float.valueOf(user.getLatitude());
//		Float log=Float.valueOf(user.getLongitude());
//		LatLng ptCenter = new LatLng(lat,log);
//		MyLocationConfiguration configuration=new MyLocationConfiguration(mCurrentMode, true, mIconLocation);
//		mBaiduMap.setMyLocationConfigeration(configuration);
//		if (isFirstIn) {
//			MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(ptCenter);
//			mBaiduMap.animateMapStatus(msu);
//			isFirstIn=false;
//		}
//		
//	}
//	
//	
//	
//		
//	
//	
//
//	protected void onPause() {
//		mMapView.onPause();
//		super.onPause();
//	}
//
//	@Override
//	protected void onResume() {
//		mMapView.onResume();
//		super.onResume();
//	}
//
//	@Override
//	protected void onDestroy() {
//		mMapView.onDestroy();
//		
//		super.onDestroy();
//	}

	
}
