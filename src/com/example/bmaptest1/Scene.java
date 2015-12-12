package com.example.bmaptest1;

import java.io.Serializable;

import android.net.Uri;

public class Scene implements Serializable {
	
	
	private String name;
	private int imageId;
	private String summary;
	private String uri;

	public Scene(String name,int imageId,String summary,String uri)
	{
		this.name = name;
		this.imageId = imageId;
		this.summary = summary;
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public int getImageId() {
		return imageId;
	}
	public String getSummary() {
		return summary;
	}
	public String getUri() {
		return uri;
	}

}
