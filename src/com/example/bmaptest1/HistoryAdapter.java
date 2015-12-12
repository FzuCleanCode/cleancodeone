package com.example.bmaptest1;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter {

	private Context mContext;
	private List<HistoryEntity> list;

	public HistoryAdapter(Context mContext, List<HistoryEntity> list) {
		super();
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		if(list == null)
		{
			return 0;
		}
		else
		{
			return list.size();
		}
		
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = LayoutInflater.from(mContext).inflate(R.layout.history_item, null);
		HistoryEntity history = list.get(position);
		TextView companyName = (TextView) itemView.findViewById(R.id.company_name);
		companyName.setText(history.getCompanyName() + " ");
		
		TextView expressNo = (TextView) itemView.findViewById(R.id.express_number);
		expressNo.setText(history.getExpressNo());
		
		TextView companyCode = (TextView)itemView.findViewById(R.id.company_code);
		companyCode.setText(history.getCompanyCode());
		
		return itemView;
	
	}

}
