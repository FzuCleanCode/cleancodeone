package com.example.bmaptest1;

import java.io.Serializable;

public class User implements Serializable{
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", isonline="
				+ isonline + ", longitude=" + longitude + ", latitude="
				+ latitude + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsonline() {
		return isonline;
	}
	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	private String id;
	private String password;
	private String isonline;
	private String longitude;
	private String latitude;
	public User(){
		
	}
	public User(String id, String password, String isonline, String longitude,
			String latitude) {
		super();
		this.id = id;
		this.password = password;
		this.isonline = isonline;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
