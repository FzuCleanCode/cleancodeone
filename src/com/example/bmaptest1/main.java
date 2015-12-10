package com.example.bmaptest1;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class main extends Activity implements OnClickListener {
	
	/**λ�÷���*/
	private LinearLayout positionLayout;
	
	/**��ݷ���*/
	private LinearLayout expressLayout;
	
	/**����Ԥ��*/
	private LinearLayout weatherLayout;
	
	/**�������ͨ*/
	private LinearLayout phoneNumLayout;
	
	/**���渣��*/
	private LinearLayout playFZLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		positionLayout = (LinearLayout)findViewById(R.id.activity_main_position);
		positionLayout.setOnClickListener(this);
		expressLayout = (LinearLayout)findViewById(R.id.activity_main_express);
		expressLayout.setOnClickListener(this);
		weatherLayout = (LinearLayout)findViewById(R.id.activity_main_weather);
		weatherLayout.setOnClickListener(this);
		phoneNumLayout = (LinearLayout)findViewById(R.id.activity_main_number);
		phoneNumLayout.setOnClickListener(this);
		playFZLayout = (LinearLayout)findViewById(R.id.activity_main_play);
		playFZLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == positionLayout) {
			Intent intent = new Intent(main.this, LoginActivity.class);
			startActivity(intent);
		}else if (v == expressLayout) {
			Toast.makeText(getApplicationContext(), "��ݷ���", Toast.LENGTH_SHORT).show();
		}else if (v == weatherLayout) {
			Toast.makeText(getApplicationContext(), "����Ԥ��", Toast.LENGTH_SHORT).show();
		}else if (v == phoneNumLayout) {
			Toast.makeText(getApplicationContext(), "�������ͨ", Toast.LENGTH_SHORT).show();
		}else if (v == playFZLayout) {
			Toast.makeText(getApplicationContext(), "���渣��", Toast.LENGTH_SHORT).show();
		}
	}

}
