package com.example.expresssection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ComsAdapter extends BaseAdapter {

	private Context context;
	private String[] companyCodes;
    private String[] companyNames;
    private String[] companyLogos;
	
	
	
	public ComsAdapter(Context context,String[] companyCodes,
			String[] companyNames,String[] companyLogos) {
		super();
		this.context = context;
		this.companyCodes = companyCodes;
		this.companyNames = companyNames;
		this.companyLogos = companyLogos;
	}

	@Override
	public int getCount() {
		if(companyNames == null)
		{
			return 0;
		}
		return companyNames.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View itemView = LayoutInflater.from(context).inflate(R.layout.item_coms, null);
		TextView comName = (TextView)itemView.findViewById(R.id.company_item_name);
		TextView comCode = (TextView)itemView.findViewById(R.id.company_item_code);
		ImageView comLogo = (ImageView)itemView.findViewById(R.id.company_logo);
		comName.setText(companyNames[position]);
		comCode.setText(companyCodes[position]);
		comLogo.setImageBitmap(getBmp(companyLogos[position]));
		
		return itemView;
		
	}
	
	public Bitmap getBmp(String imageName)
	{
		int imgId = context.getResources().getIdentifier(imageName, "drawable", "com.example.expresssection");
		return BitmapFactory.decodeResource(context.getResources(), imgId);
		
		
	}
	

}
