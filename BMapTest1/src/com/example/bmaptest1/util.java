package com.example.bmaptest1;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.os.Handler;

public class util {

	public static String readData(InputStream inputStream,String code) {
		// TODO Auto-generated method stub
		byte[] data;
		try {
			
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024];
			int hasRead;
			while ((hasRead=inputStream.read(buffer))!=-1) {
				outputStream.write(buffer,0,hasRead);
				
			}
			data=outputStream.toByteArray();
			outputStream.close();
			inputStream.close();
			return new String(data,code);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
