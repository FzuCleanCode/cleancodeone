package com.example.bmaptest1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultAdapter extends BaseAdapter {

	private Context context;
	private List<ResultEntity> followList;
//	private int state;
	
	
	
	
	
	public ResultAdapter(Context context, List<ResultEntity> followList) {
		super();
		this.context = context;
		this.followList = followList;
		
		//this.state = state;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return followList.size();
	}

	@Override
	public Object getItem(int position) {
		return followList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView;
		if(convertView == null)
		{
			itemView = LayoutInflater.from(context).inflate(R.layout.result_item, null);
		}
		else
		{
			itemView = convertView;
		}
		
		
		ResultEntity map = followList.get(position);
		
		TextView time = (TextView)itemView.findViewById(R.id.item_time);
		time.setText(map.getTime());
		
		TextView context = (TextView)itemView.findViewById(R.id.item_follow_info);
	    context.setText(map.getContext());
	    return itemView;
	
		
	}
	
	public void setFollowList(ArrayList<ResultEntity> followList)
	{
		this.followList = followList;
	}
	
	
	

}
