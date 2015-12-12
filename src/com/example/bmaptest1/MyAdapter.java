package com.example.bmaptest1;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<User> {
	private int resourceid;
	public MyAdapter(Context context, int textViewResourceId, List<User> courses) {
		super(context, textViewResourceId, courses);
		// TODO Auto-generated constructor stub
		this.resourceid=textViewResourceId;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		User user=getItem(position);
		View view=LayoutInflater.from(getContext()).inflate(resourceid,null);
		TextView friend=(TextView)view.findViewById(R.id.friend);
		friend.setText(user.getId());
		return view;
	}
}
