package com.example.bmaptest1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class Deaboway extends Application {
	private  List<Activity> mainActivities;
	public Deaboway(){
		mainActivities=new ArrayList<Activity>();
	}
	public List<Activity> MainActivity(){
		return mainActivities;
	}
	public void addActivity(Activity act){
		if (mainActivities==null) {
			mainActivities=new ArrayList<Activity>();
		}
		mainActivities.add(act);
	}
	public void finishAll(){
		for(Activity act:mainActivities){
			if (!act.isFinishing()) {
				act.finish();
			}
		}
		mainActivities=null;
	}
}
