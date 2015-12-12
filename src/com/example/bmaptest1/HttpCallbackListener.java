package com.example.bmaptest1;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
