package com.example.testdrag;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		OnGesturePerformedListener {
	// Gesture ---->
	GestureLibrary gLibrary1;
	GestureOverlayView mView1;
	// <------Gesture ---

	@SuppressWarnings("unused")
	private ImageView img_drag, img_drop,conn1,conn2,conn3,conn5,imgsmall_conn,conn6,conn_plus;

	private ImageView conn4;
	// ---->Gates
	private int gate = 0;
	// ---<gates

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Gesture ---->
		gLibrary1 = GestureLibraries.fromRawResource(this, R.raw.slide_g);
		if (gLibrary1 != null) {
			if (!gLibrary1.load()) {
				Log.e("GestureSample", "Gesture library was not loaded…");
				finish();
			} else {
				mView1 = (GestureOverlayView) findViewById(R.id.gestures);
				mView1.addOnGesturePerformedListener(this);
			}
		}
		// ---< Gesture

		GridLayout grd = (GridLayout) findViewById(R.id.gridlayout);
		img_drag = (ImageView) findViewById(R.id.imgdrag);
		img_drag.setBackground(getResources().getDrawable(R.drawable.blank));

		int c = 0, r = 0;
		
		for (int i = 0; i <= 320; i++) {
			GridLayout.LayoutParams param = new GridLayout.LayoutParams();
			param.columnSpec = GridLayout.spec(c);
			param.rowSpec = GridLayout.spec(r);
			
			ImageButton img = new ImageButton(this);
			img.setId(i);
			Drawable d = getResources().getDrawable(R.drawable.gridelement);
			img.setBackground(d);
			img.setLayoutParams(param);
			img.setTag(i);
			grd.addView(img);
			img.setScaleType(ImageView.ScaleType.CENTER);
			img.setOnDragListener(new ChoiceDragListener());
			c++;
			if (c == 21) {
				r++;
				c = 0;
			}
			img.setOnLongClickListener(new OnLongClickListener() {
			    public boolean onLongClick(View arg0) {
			        //Toast.makeText(getApplicationContext(), "Long Clicked "+ arg0.getTag() , Toast.LENGTH_SHORT).show();
			        showPopup(arg0);
			        return false;
			    }
			});
		}

		img_drag.setOnTouchListener(new ChoiceTouchListener(false));
		
		imgsmall_conn=(ImageView) findViewById(R.id.imgsmall_conn);
		imgsmall_conn.setOnTouchListener(new ChoiceTouchListener(false));
		
		conn1=(ImageView) findViewById(R.id.imgconn1);
		conn1.setOnTouchListener(new ChoiceTouchListener(false));

		conn2=(ImageView) findViewById(R.id.imgconn2);
		conn2.setOnTouchListener(new ChoiceTouchListener(false));
		
		conn3=(ImageView) findViewById(R.id.imgconn3);
		conn3.setOnTouchListener(new ChoiceTouchListener(false));
		
		conn_plus=(ImageView) findViewById(R.id.imgconn_plus);
		conn_plus.setOnTouchListener(new ChoiceTouchListener(false));
		
		conn4=(ImageView) findViewById(R.id.imgconn4);
		conn4.setOnTouchListener(new ChoiceTouchListener(false));
		
		conn5=(ImageView) findViewById(R.id.imgconn5);
		conn5.setOnTouchListener(new ChoiceTouchListener(false));

		conn6=(ImageView) findViewById(R.id.imgconn6);
		conn6.setOnTouchListener(new ChoiceTouchListener(false));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ChoiceTouchListener will handle touch events on draggable views
	 * 
	 */

	private final class ChoiceTouchListener implements OnTouchListener {
		boolean flag=false;
		public ChoiceTouchListener(boolean f)
		{
			flag=f;
		}
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				/*
				 * Drag details: we only need default behavior - clip data could
				 * be set to pass data as part of drag - shadow can be tailored
				 */
				if(flag)
					view.setVisibility(View.GONE);
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
						view);
				// start dragging the item touched
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			} else {
				return false;
			}
		
		}
	}

	/**
	 * DragListener will handle dragged views being dropped on the drop area -
	 * only the drop action will have processing added to it as we are not -
	 * amending the default behavior for other parts of the drag process
	 * 
	 */

	private class ChoiceDragListener implements OnDragListener {

		@SuppressLint("NewApi")
		@Override
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// no action necessary
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				// no action necessary
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				// no action necessary
				break;
			case DragEvent.ACTION_DROP:
				// handle the dragged view being dropped over a drop view
				View view = (View) event.getLocalState();
				// stop displaying the view where it was before it was dragged
				// view.setVisibility(View.INVISIBLE);
				// view dragged item is being dropped on
				ImageView dropTarget = (ImageView) v;
				// view being dragged and dropped
				ImageView dropped = (ImageView) view;
				// update the text in the target view to reflect the data being
				// dropped
				Log.v("Main", "dropped is" + dropped);
				dropTarget.setBackground(dropped.getBackground());
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				// no action necessary
				break;
			default:
				break;
			}
			return true;
		}
	}

	// ---Gesture-->
	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = gLibrary1.recognize(gesture);
		for (Prediction prediction : predictions) {
			if (prediction.score > 1.0) {
				/*
				 * Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT)
				 * .show();
				 */
				if ((prediction.name).equals("input")) {
					Toast toast=Toast.makeText(this, "Please Input Gesture", Toast.LENGTH_SHORT);
					LinearLayout toastLayout = (LinearLayout) toast.getView();
					TextView toastTV = (TextView) toastLayout.getChildAt(0);
					toastTV.setTextSize(50);
					toast.show();
					Intent i = new Intent(this, GestureInput.class);
					startActivityForResult(i, gate);
				}
			}
		}
	}

	// <-----Gesture---

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String gate_value = "";
		switch (resultCode) {
		case -1:
			gate_value = "CLEAR";
			for (int i = 0; i <= 320; i++) {
				ImageButton img=(ImageButton) findViewById(i);
				img.setBackground(getResources().getDrawable(R.drawable.gridelement));
			}
			
			break;
		case 1:
			gate_value = "AND";
			img_drag.setBackground(getResources().getDrawable(R.drawable.and_gate));
			break;
		case 2:
			gate_value = "NOT";
			img_drag.setBackground(getResources().getDrawable(R.drawable.not_gate));
			break;
		case 3:
			gate_value = "OR";
			img_drag.setBackground(getResources().getDrawable(R.drawable.or_gate));
			break;
		case 4:
			gate_value = "NAND";
			img_drag.setBackground(getResources().getDrawable(R.drawable.nand_gate));
			break;
		case 5:
			gate_value = "NOR";
			img_drag.setBackground(getResources().getDrawable(R.drawable.nor_temp));
			break;
		case 6:
			gate_value = "XOR";
			img_drag.setBackground(getResources().getDrawable(R.drawable.xor_gate));
			break;
		case 7:
			gate_value = "XNOR";
			img_drag.setBackground(getResources().getDrawable(R.drawable.xnor_gate));
			break;
		default:
			gate_value="something which i cant figure out!";
				
		}
		Toast toast=Toast.makeText(this, "You've input " + gate_value, Toast.LENGTH_SHORT);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		TextView toastTV = (TextView) toastLayout.getChildAt(0);
		toastTV.setTextSize(50);
		toast.show();
	}
	
	public void showPopup(final View v) {
	    PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.gird_menu, popup.getMenu());
	    popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
		        case R.id.menu_edit:
		        	
		        	RelativeLayout rL= (RelativeLayout) findViewById(R.id.relL);
		        	ImageButton img = new ImageButton(rL.getContext());
		        	RelativeLayout float_L=new RelativeLayout(rL.getContext());
		        	float_L.setBackgroundColor(Color.CYAN);
		        	float_L.setMinimumHeight(160);
		        	img.setMinimumWidth(145);
		        	float_L.setX(130);
		        	float_L.setY(348);
					Drawable d = v.getBackground();
					img.setBackground(d);
					img.setX(10);
					img.setY(10);
					img.setMinimumHeight(140);
					img.setMinimumWidth(140);
					img.setOnTouchListener(new ChoiceTouchListener(true));
					v.setBackground(getResources().getDrawable(R.drawable.gridelement));
					float_L.addView(img);
		        	rL.addView(float_L);
		            return true;
		        case R.id.menu_clear:
		            Toast.makeText(getApplicationContext(), "Clear = "+ v.getTag() +"?" , Toast.LENGTH_SHORT).show();
	            v.setBackground(getResources().getDrawable(R.drawable.gridelement));
		        default:
		            return false;
		    }
			}
	    	
	    });
	    popup.show();	
	}
}
