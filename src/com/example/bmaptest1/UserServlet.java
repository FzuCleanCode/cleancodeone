package com.example.bmaptest1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.util.Log;

public class UserServlet {
	private Handler myhHandler;
	private static final String TAG = "YourApplication"; 
	private static final boolean D = true; 
	public static boolean regist (User user) {
//		String path="http://192.168.191.1:8080/map/UserServlet";
		String path="http://qq969012806.imwork.net:12067/map/UserServlet";
		Map<String,String> params=new HashMap<String, String>();
		params.put("id", user.getId());
		params.put("password", user.getPassword());
		return RegistToWeb(path,params,"UTF-8");
	}

	private static boolean RegistToWeb (String path, Map<String, String> params,
			String encoding) {
		StringBuilder data=new StringBuilder();
		HttpURLConnection connection;
		try {
			if (params!=null&&!params.isEmpty()) {
				for(Map.Entry<String, String> entry:params.entrySet()){
					data.append(entry.getKey()).append("=");
					data.append(URLEncoder.encode(entry.getValue(), encoding));
					data.append("&");
				}
				data.deleteCharAt(data.length()-1);
			}
			byte[] entity=data.toString().getBytes();
			connection = (HttpURLConnection)new URL(path).openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", String.valueOf(entity.length));
			OutputStream outputStream=connection.getOutputStream();
			outputStream.write(entity);
			if (connection.getResponseCode()==200) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public static String hasRegist(String id) {
		String result;
		
		// TODO Auto-generated method stub
		try {
//			URL url=new URL("http://192.168.191.1:8080/map/UserServlet?method=hasRegist&id="+id);
			URL url=new URL("http://qq969012806.imwork.net:12067/map/UserServlet?method=hasRegist&id="+id);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode()!=200) {
				throw new Exception("URL«Î«Û ß∞‹£°");
			}
			
			InputStream inputStream=connection.getInputStream();

			result=util.readData(inputStream, "UTF-8");
			return result;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static boolean loginin(User user){
		try {
//			String path="http://192.168.191.1:8080/map/LogininServlet";
			String path="http://qq969012806.imwork.net:12067/map/LogininServlet";
			Map<String,String> params=new HashMap<String, String>();
			params.put("id", user.getId());
			
			params.put("password", user.getPassword());
			params.put("isonline", user.getIsonline());
			params.put("latitude", user.getLatitude());
			params.put("longitude", user.getLongitude());
			
			return loginintoWeb(path,params,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean loginintoWeb(String path,
			Map<String, String> params, String encoding) {
		// TODO Auto-generated method stub
		StringBuilder data=new StringBuilder();
		HttpURLConnection connection;
		try {
			if (params!=null&&!params.isEmpty()) {
				for(Map.Entry<String, String> entry:params.entrySet()){
					data.append(entry.getKey()).append("=");
					data.append(URLEncoder.encode(entry.getValue(), encoding));
					data.append("&");
					
				}
				data.deleteCharAt(data.length()-1);
			}
			byte[] entity=data.toString().getBytes();
			
			connection = (HttpURLConnection)new URL(path).openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", String.valueOf(entity.length));
			OutputStream outputStream=connection.getOutputStream();
			outputStream.write(entity);
			if (connection.getResponseCode()==200) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String judgeloginin(String id,String password) {
		String result;
		try {
//			URL url=new URL("http://192.168.191.1:8080/map/LogininServlet?id="+id+"&password="+password);
			URL url=new URL("http://qq969012806.imwork.net:12067/map/LogininServlet?id="+id+"&password="+password);
			
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode()!=200) {
				throw new Exception("URL«Î«Û ß∞‹£°");
			}
			
			InputStream inputStream=connection.getInputStream();
			
			result=util.readData(inputStream, "UTF-8");
			return result;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getFriends(User user){
		String result;
		try {
//			URL url=new URL("http://192.168.191.1:8080/map/FriendServlet?id="+user.getId());
			URL url=new URL("http://qq969012806.imwork.net:12067/map/FriendServlet?id="+user.getId());
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if(connection.getResponseCode()!=200){
				throw new Exception("URL«Î«Û ß∞‹");
			}
			InputStream inputStream=connection.getInputStream();

			result=util.readData(inputStream, "UTF-8");
			return result;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static String getOnlineFriends(User user) {
		// TODO Auto-generated method stub
		String result;
		try {
//			URL url=new URL("http://192.168.191.1:8080/map/getOnlineFriend?id="+user.getId());
			URL url=new URL("http://qq969012806.imwork.net:12067/map/getOnlineFriend?id="+user.getId());
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if(connection.getResponseCode()!=200){
				throw new Exception("URL«Î«Û ß∞‹");
			}
			InputStream inputStream=connection.getInputStream();

			result=util.readData(inputStream, "UTF-8");
			return result;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static String getNonlineFriends(User user) {
		// TODO Auto-generated method stub
		String result;
		try {
			
//			URL url=new URL("http://192.168.191.1:8080/map/getNonlineFriend?id="+user.getId());
			URL url=new URL("http://qq969012806.imwork.net:12067/map/getNonlineFriend?id="+user.getId());
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if(connection.getResponseCode()!=200){
				throw new Exception("URL«Î«Û ß∞‹");
			}
			InputStream inputStream=connection.getInputStream();

			result=util.readData(inputStream, "UTF-8");
			return result;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static void addFriend(String id,String friendid) {
		// TODO Auto-generated method stub
		try {
//			String path="http://192.168.191.1:8080/map/AddFriendServlet";
			String path="http://qq969012806.imwork.net:12067/map/AddFriendServlet";
			
			Map<String,String> params=new HashMap<String, String>();
			params.put("id", id);
			params.put("friendid", friendid);
			AddtoWeb(path,params,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void AddtoWeb(String path, Map<String, String> params,
			String encoding) {
		// TODO Auto-generated method stub
		StringBuilder data=new StringBuilder();
		HttpURLConnection connection;
		try {
			if (params!=null&&!params.isEmpty()) {
				for(Map.Entry<String, String> entry:params.entrySet()){
					data.append(entry.getKey()).append("=");
					data.append(URLEncoder.encode(entry.getValue(), encoding));
					data.append("&");
					
				}
				data.deleteCharAt(data.length()-1);
			}
			byte[] entity=data.toString().getBytes();
			
			connection = (HttpURLConnection)new URL(path).openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", String.valueOf(entity.length));
			OutputStream outputStream=connection.getOutputStream();
			outputStream.write(entity);
			if (connection.getResponseCode()==200) {
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	public static String getMessage(User user) {
		// TODO Auto-generated method stub
		String result;
		try {
//			URL url=new URL("http://192.168.191.1:8080/map/GetMessageServlet?id="+user.getId());
			URL url=new URL("http://qq969012806.imwork.net:12067/map/GetMessageServlet?id="+user.getId());
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if(connection.getResponseCode()!=200){
				throw new Exception("URL«Î«Û ß∞‹");
			}
			InputStream inputStream=connection.getInputStream();

			result=util.readData(inputStream, "UTF-8");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public static void sureFriend(String id, String friendid) {
		// TODO Auto-generated method stub
		try {
//			String path="http://192.168.191.1:8080/map/SureFriendServlet";
			String path="http://qq969012806.imwork.net:12067/map/SureFriendServlet";
			
			Map<String,String> params=new HashMap<String, String>();
			params.put("id", id);
			params.put("friendid", friendid);
			suretoWeb(path,params,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void suretoWeb(String path, Map<String, String> params,
			String encoding) {
		// TODO Auto-generated method stub
		StringBuilder data=new StringBuilder();
		HttpURLConnection connection;
		try {
			if (params!=null&&!params.isEmpty()) {
				for(Map.Entry<String, String> entry:params.entrySet()){
					data.append(entry.getKey()).append("=");
					data.append(URLEncoder.encode(entry.getValue(), encoding));
					data.append("&");
					
				}
				data.deleteCharAt(data.length()-1);
			}
			byte[] entity=data.toString().getBytes();
			
			connection = (HttpURLConnection)new URL(path).openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", String.valueOf(entity.length));
			OutputStream outputStream=connection.getOutputStream();
			outputStream.write(entity);
			
			if (connection.getResponseCode()==200) {
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}
}
