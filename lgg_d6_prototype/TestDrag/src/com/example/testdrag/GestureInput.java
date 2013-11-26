package com.example.testdrag;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;

public class GestureInput extends Activity implements
		OnGesturePerformedListener {
	private GestureLibrary gestureLib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
		View inflate = getLayoutInflater().inflate(
				R.layout.activity_gesture_input, null);
		gestureOverlayView.addView(inflate);
		gestureOverlayView.addOnGesturePerformedListener(this);
		gestureOverlayView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
		File dir = Environment.getExternalStorageDirectory();
		File yourFile = new File(dir, "gestures");
		gestureLib = GestureLibraries.fromFile(yourFile);
		if (!gestureLib.load()) {
			finish();
		}
		setContentView(gestureOverlayView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gesture_input, menu);
		return true;
	}

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		// TODO Auto-generated method stub
		ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
		int result=0;
		for (Prediction prediction : predictions) {
			if (prediction.score > 1.0) {
//				Toast.makeText(this, "Did u input??"+prediction.name, Toast.LENGTH_SHORT)
//						.show();
				if((prediction.name).equals("AND"))
				{
					result=1;
				}
				else if((prediction.name).equals("NOT"))
				{
					result=2;
				}
				else if((prediction.name).equals("OR"))
				{
					result=3;
				}
				else if((prediction.name).equals("NOR"))
				{
					result=5;
				}
				else if((prediction.name).equals("NAND"))
				{
					result=4;
				}
				else if((prediction.name).equals("XOR"))
				{
					result=6;
				}
				else if((prediction.name).equals("XNOR"))
				{
					result=7;
				}
				else if((prediction.name).equals("CLEAR"))
				{
					result=-1;
				}
				Intent resultIntent = new Intent();
//				resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, tabIndexValue);
				setResult(result, resultIntent);
				finish();
				break;
			}
		}
	}
}
