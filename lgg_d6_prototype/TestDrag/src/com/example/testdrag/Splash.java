package com.example.testdrag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
	 private static int SPLASH_TIME_OUT = 1000;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		new Handler().postDelayed(new Runnable() {
       	 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
            	Intent i = new Intent(Splash.this, MainActivity.class);
            	startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
	}

}
