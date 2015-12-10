package com.example.expresssection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Splash extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; //—”≥Ÿ»˝√Î 
	 
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash); 
      
        new Handler().postDelayed(new Runnable(){ 
 
         @Override
         public void run() { 
             Intent mainIntent = new Intent(Splash.this,MainActivity.class); 
             Splash.this.startActivity(mainIntent); 
                 Splash.this.finish(); 
         } 
             
        }, SPLASH_DISPLAY_LENGHT); 
    } 

}
