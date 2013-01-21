package com.android.alex.groupmanagement;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;

import com.android.alex.services.SoapService;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class UserDetails extends MapActivity {
	
	private String[] selectedProperty;
	private MapView mapView;
	private String responseString;
	private SoapService ss;
	
	private MapController mc;
	private GeoPoint p;
	private HashMap<Integer, Integer> timeMap;
	private int currentHour = 0;
	
	class MapOverlay extends com.google.android.maps.Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {

			super.draw(canvas, mapView, shadow);
			
			// raduius changes depending on current time
			float mRadius = timeMap.get(currentHour);

			Projection projection = mapView.getProjection();

			// ---translate the GeoPoint to screen pixels---
			Point pt = new Point();
			projection.toPixels(p, pt);

			float circleRadius = projection.metersToEquatorPixels(mRadius)
					* (1 / FloatMath.cos((float) Math.toRadians(pt.y)));

			Paint innerCirclePaint;

			innerCirclePaint = new Paint();
			innerCirclePaint.setColor(Color.BLUE);
			innerCirclePaint.setAlpha(25);
			innerCirclePaint.setAntiAlias(true);

			innerCirclePaint.setStyle(Paint.Style.FILL);

			canvas.drawCircle((float) pt.x, (float) pt.y, circleRadius,
					innerCirclePaint);
			return true;
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_details);
		ss = new SoapService();
		// init variables
		initMap();
		getCurrentHour();
		
		setUpView();
	}

	private void setUpView() 
	{
		Bundle b = getIntent().getExtras(); // Getting the Bundle object that pass from another activity
		// [GroupName, UserName]
		selectedProperty = b.getStringArray("SelectedProperty");
		Log.v(">>> ", Arrays.toString(selectedProperty));
		responseString = ss.findUsersLocation(selectedProperty[1], selectedProperty[0]);
		// Latitude, Longitude
		selectedProperty = responseString.split(", ");
		Log.v("LOCATION >>> ", Arrays.toString(selectedProperty));
		
		mapView = (MapView)findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);
		
		mc = mapView.getController();
		mapView.setBuiltInZoomControls(true);
		
		/* TO-DO get details from the hashMap */
		//hashMap.get("")

		double lat = Double.parseDouble(selectedProperty[0]);
		double lng = Double.parseDouble(selectedProperty[1]);

		p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

		mc.animateTo(p);
		mc.setZoom(17);
		mapView.invalidate();
		
        // Add the Overlay
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
 
        mapView.invalidate();
	}
	
	private void getCurrentHour() {
		SimpleDateFormat df = new SimpleDateFormat("HH");
		Date date = new Date();
		currentHour = 3;//Integer.parseInt(df.format(date));
	}

	private void initMap() {
		timeMap = new HashMap<Integer, Integer>();
		for (int h = 0; h < 24; ++h) {
			if (h >= 0 && h <= 9)
				timeMap.put(h, 300);
			else if (h >= 10 && h <= 17)
				timeMap.put(h, 150);
			else if (h >= 18 && h <= 21)
				timeMap.put(h, 200);
			else 
				timeMap.put(h, 250);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
