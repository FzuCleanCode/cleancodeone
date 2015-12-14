package com.example.compusassist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends Activity {
	
	
	private String address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_layout);
		
		Intent intent = this.getIntent(); 
		Scene scene =(Scene)intent.getSerializableExtra("scene");
		
		address = scene.getUri();
		Button button = (Button)findViewById(R.id.back);
		ImageView image = (ImageView)findViewById(R.id.display_image);
		TextView text = (TextView)findViewById(R.id.display_text);
		
		image.setImageResource(scene.getImageId());
		text.setText(scene.getSummary());
		
		text.setMovementMethod(ScrollingMovementMethod.getInstance()); //为TextView设置下拉滚动条
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(address));
				startActivity(intent);

System.out.println("Hello World")；
				
			}
		);
		
	}

}
