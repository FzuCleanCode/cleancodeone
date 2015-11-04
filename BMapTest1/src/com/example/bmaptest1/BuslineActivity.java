package com.example.bmaptest1;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BusLineOverlay;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;

public class BuslineActivity extends FragmentActivity implements OnGetPoiSearchResultListener,OnMapClickListener, OnGetBusLineSearchResultListener{
	private Button mPre=null;//上一个节点
	private Button mNext=null;//下一个节点
	private int nodeIndex=-2;//节点索引，供浏览节点时使用
	private BusLineResult route=null;//保存驾车/步行路线的数据的变量
	private List<String> busLineIDList=null;
	private int busLineIndex=0;
	private PoiSearch mSearch=null;//搜索模块，可以去掉地图模块独立使用
	private BusLineSearch mBusLineSearch = null;
	private BaiduMap mBaiduMap = null;
	BusLineOverlay overlay;//公交路线绘制
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.busline);
		CharSequence titleLable = "公交线路查询功能";
		setTitle(titleLable);
		mPre=(Button)findViewById(R.id.pre);
		mNext=(Button)findViewById(R.id.next);
		mPre.setVisibility(View.INVISIBLE);
		mNext.setVisibility(View.INVISIBLE);
		mBaiduMap= ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.bmapView)).getBaiduMap();
		mBaiduMap.setOnMapClickListener(this);
		mSearch=PoiSearch.newInstance();
		mSearch.setOnGetPoiSearchResultListener(this);
		mBusLineSearch=BusLineSearch.newInstance();
		mBusLineSearch.setOnGetBusLineSearchResultListener(this);
		busLineIDList=new ArrayList<String>();
		overlay=new BusLineOverlay(mBaiduMap);
		mBaiduMap.setOnMarkerClickListener(overlay);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mSearch.destroy();
		mBusLineSearch.destroy();
		super.onDestroy();
	}
	
	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub
		if (result==null||result.error!=SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(BuslineActivity.this,  "抱歉，未找到结果1", 2).show();
			return;
		}
		// 遍历所有poi，找到类型为公交线路的poi
		busLineIDList.clear();
		for (PoiInfo poi : result.getAllPoi()) {
			if (poi.type==PoiInfo.POITYPE.BUS_LINE|| poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
				busLineIDList.add(poi.uid);
			}
		}
		SearchNextBusline(null);
		route = null;
		
	}

	public void SearchButtonProcess(View v){
		busLineIDList.clear();
		busLineIndex=0;
		mPre.setVisibility(View.INVISIBLE);
		mNext.setVisibility(View.INVISIBLE);
		EditText editCity=(EditText)findViewById(R.id.city);
		EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
		mSearch.searchInCity((new PoiCitySearchOption()).city(
				editCity.getText().toString()).keyword(
				editSearchKey.getText().toString()));
	}
	
	
	public void SearchNextBusline(View v) {
		// TODO Auto-generated method stub
		if (busLineIndex>=busLineIDList.size()) {
			busLineIndex=0;
			
		}if (busLineIndex>=0&&busLineIndex<busLineIDList.size()&& busLineIDList.size() > 0) {
			mBusLineSearch.searchBusLine((new BusLineSearchOption()
			.city(((EditText) findViewById(R.id.city)).getText()
					.toString()).uid(busLineIDList.get(busLineIndex))));
			busLineIndex++;
		}
	}

	public void nodeClick(View v) {
		// TODO Auto-generated method stub
		if (nodeIndex<-1||route==null||nodeIndex>=route.getStations().size()) {
			return;
		}
		TextView popupText=new TextView(this);
		popupText.setBackgroundResource(R.drawable.popup);
		popupText.setTextColor(0xff000000);
		if (mPre.equals(v)&&nodeIndex>0) {
			nodeIndex--;
		}if (mNext.equals(v)&&nodeIndex<(route.getStations().size()-1)) {
			nodeIndex++;
			
		}if (nodeIndex>=0) {
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(route
					.getStations().get(nodeIndex).getLocation()));
			popupText.setText(route.getStations().get(nodeIndex).getTitle());
			mBaiduMap.showInfoWindow(new InfoWindow(popupText, route.getStations()
					.get(nodeIndex).getLocation(), 0));
		}
	}
	
	
	
	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onGetBusLineResult(BusLineResult result) {
		// TODO Auto-generated method stub
		if (result==null||result.error!=SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(BuslineActivity.this, "抱歉，未找到结果2", 2).show();
			return;
		}
		mBaiduMap.clear();
		route=result;
		nodeIndex=-1;
		overlay.removeFromMap();
		overlay.setData(result);
		overlay.addToMap();
		overlay.zoomToSpan();
		mPre.setVisibility(View.VISIBLE);
		mNext.setVisibility(View.VISIBLE);
		Toast.makeText(BuslineActivity.this, result.getBusLineName(),Toast.LENGTH_SHORT).show();
	}
}
