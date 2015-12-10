package com.example.compusassist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SceneAdapter extends ArrayAdapter<Scene> {	
	private int resourceId;
	public SceneAdapter(Context context, int textViewResourceId,List<Scene> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		Scene scene = getItem(position);		
		View view;		
		ViewHolder viewHolder;		
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);			
			viewHolder = new ViewHolder();		
			viewHolder.sceneImage = (ImageView)view.findViewById(R.id.scene_image);
			viewHolder.sceneName = (TextView)view.findViewById(R.id.scene_name);			
			view.setTag(viewHolder);
		}
		else{
			view = convertView;		
			viewHolder = (ViewHolder)view.getTag();
		}		
		viewHolder.sceneImage.setImageResource(scene.getImageId());
		viewHolder.sceneName.setText(scene.getName());
		return view;		
	}	
	class ViewHolder{
		ImageView sceneImage;
		TextView sceneName;
	}
	

}
