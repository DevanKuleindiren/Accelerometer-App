package com.devankuleindiren.testaccelerometerapp;

import java.util.Arrays;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	
	Sensor accelerometer;
	SensorManager accelerometerManager;
	TextView accelerationDisplay;
	
	private static GraphicalView view;
	private LineGraph line = new LineGraph();
	
	private static Thread thread;
	private double XAcceleration;
	private double YAcceleration;
	private double ZAcceleration;
	
	private double ZVar1 = 0;
	private double ZVar2 = 0;
	
	private static final String TAG = "MyActivity";
	
	double[] ZAccelerationSaved = new double[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		accelerometerManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelerometer = accelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		accelerometerManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		accelerationDisplay = (TextView)findViewById(R.id.acceleration);
		
		thread = new Thread() {
			public void run() {
				for (int i = 0; i <= 1000; i++) {
					try {
						thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double XTemp = XAcceleration;
					double YTemp = YAcceleration;
					double ZTemp = ZAcceleration;
					
					//ADD ACCELERATION TO STORAGE
					for (int j = (ZAccelerationSaved.length-1); j >= 1; j--) {
						ZAccelerationSaved[j] = ZAccelerationSaved[j-1];
					}
					ZAccelerationSaved[0] = ZTemp;
					
					//Log.d(TAG, "**********************************");
					//Log.d(TAG, Arrays.toString(ZAccelerationSaved));
					
					//ANALYSE STORED ACCELERATION
					double sumOfSquares = 0;
					double sum = 0;
					if ((i%20) == 0 && i != 0) {
						for (int k = 0; k <= (ZAccelerationSaved.length-1); k++) {
							sumOfSquares = sumOfSquares + Math.pow(ZAccelerationSaved[k], 2);
							sum = sum + ZAccelerationSaved[k];
						}
						ZVar2 = ZVar1;
						ZVar1 = (sumOfSquares/ZAccelerationSaved.length)-Math.pow((sum/ZAccelerationSaved.length), 2);
						
						/*Log.d(TAG, Double.toString(ZVar1));
						Log.d(TAG, Double.toString(ZVar2));
						Log.d(TAG, "***");*/
						
						if ((ZVar1+ZVar2) >= 8) {
							runOnUiThread(new Runnable() {
							    public void run() {
							        Toast.makeText(MainActivity.this, "Floor changed.", Toast.LENGTH_SHORT).show();
							    }
							});
						}
						
						/*runOnUiThread(new Runnable() {
						    public void run() {
						        Toast.makeText(MainActivity.this, Double.toString(ZVar1), Toast.LENGTH_SHORT).show();
						    }
						});*/
					}
					
					line.addNewXPoint(new Point(i, XTemp));
					line.addNewYPoint(new Point(i, YTemp));
					line.addNewZPoint(new Point(i, ZTemp));
					
					view.repaint();
				}
			}
		};
		thread.start();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		view = line.getView(this);
		setContentView(view);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		accelerationDisplay.setText("X: " + event.values[0] +
				"\nY: " + event.values[1] +
				"\nZ: " + event.values[2]);
		XAcceleration = event.values[0];
		YAcceleration = event.values[1];
		ZAcceleration = event.values[2];
	}
}
