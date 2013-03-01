package com.android.alex.groupmanagement.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;

import com.android.alex.groupmanagement.R;
import com.android.alex.groupmanagement.R.drawable;
import com.android.alex.groupmanagement.R.id;
import com.android.alex.groupmanagement.R.layout;
import com.android.alex.services.SoapService;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class UserDetails extends MapActivity {

	private String[] selectedProperty;
	private MapView mapView;
	private String responseString;
	private SoapService ss;
	String[] locationCoord = new String[2];

	private MapController mc;
	private GeoPoint p;
	private HashMap<Integer, Integer> timeMap;
	private int currentHour = 0;
	private double lat;
	private double lng;
	

	private CustomOverlay overlay[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_details);
		ss = new SoapService();
		// init variables
		initMap();
		getCurrentHour();
		setUpView();
	
	}

	private void setUpView() {
		String[] listOfCommon = null;
		String[] listOfDistinct = null;
		
		Bundle b = getIntent().getExtras(); // Getting the Bundle object that
											// pass from another activity
		// [Single/Multiple, GroupName, UserName]
		selectedProperty = b.getStringArray("SelectedProperty");
		Log.v(">>> ", Arrays.toString(selectedProperty));

		responseString = ss.findUsersLocation(selectedProperty[2],
				selectedProperty[1]);

		// if there is only one person in a database
		if (selectedProperty[0].equals("Single")) {
			// Latitude, Longitude
			locationCoord = responseString.split(", ");
			overlay = new CustomOverlay[2];
			// if there are many persons with the same name
		} else if (selectedProperty[0].equals("Multiple")) {
			// pairs of latitude and longitude
			String[] parseString = responseString.replace("[", "").replace("]", "")
					.split("\\,\\s");
			
			overlay = new CustomOverlay[parseString.length];
			
			// create a list of locations which repeat in a list
			listOfCommon = findCommon(parseString);
			// create a list of distinct locations 
			listOfDistinct = findDistinct(parseString);

			Log.v("List of distinct >>", Arrays.toString(listOfDistinct));
			Log.v("List of common >>", Arrays.toString(listOfCommon));
		}

		Log.v("LOCATION >>> ", responseString);

		mapView = (MapView) findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);

		Drawable drawable = this.getResources().getDrawable(
				R.drawable.ic_launcher);
		GooglePin itemizedoverlay = new GooglePin(drawable, this);

		/* TO-DO get details from the hashMap */
		// hashMap.get("")



		List<Overlay> mapOverlays = mapView.getOverlays();
		float mRadius = timeMap.get(currentHour);

		if (selectedProperty[0].equals("Multiple")) {
			int i = 0;
			for (int x = 0, y = 1; y < listOfDistinct.length; x += 2, y += 2) {
				if (listOfDistinct[x] == null)
					continue;
				lat = Double.parseDouble(listOfDistinct[x]);
				lng = Double.parseDouble(listOfDistinct[y]);
				p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
				
				overlay[i] = new CustomOverlay(p, mRadius);
				mapView.getOverlays().add(overlay[i++]);
				mc = mapView.getController();
				mc.animateTo(p);
			}

			for (int x = 0, y = 1; y < listOfCommon.length; x += 2, y += 2) {
				if (listOfCommon[x] == null)
					continue;
				lat = Double.parseDouble(listOfCommon[x]);
				lng = Double.parseDouble(listOfCommon[y]);
				p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

				mapView.getController().setCenter(p);
				OverlayItem overlayitem = new OverlayItem(p, "User", "Location");
				itemizedoverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedoverlay);
			}

			mc.setZoom(20);
			mapView.invalidate();
			
		} else {			
			lat = Double.parseDouble(locationCoord[0]);
			lng = Double.parseDouble(locationCoord[1]);
			p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

			overlay[0] = new CustomOverlay(p, mRadius);
			mapView.getOverlays().add(overlay[0]);
			mc = mapView.getController();
			mc.animateTo(p);

			mapView.invalidate();
		}
	}

	private String[] findDistinct(String[] parseString) {
		String[] newList = new String[parseString.length];
		int repeat = 0;
		for (int x=0, y=1; y < parseString.length; x+=+2, y+=2) {
			// if x,y pair does not appear in a list put into new list
			for (int x1=0, y1=1; y1 < parseString.length; x1+=2, y1+=2)
				if (parseString[x].equals(parseString[x1]) && 
					parseString[y].equals(parseString[y1]))
					repeat++;
			if (repeat == 1) {
				newList[x] = parseString[x];
				newList[y] = parseString[y];
			}
			repeat = 0;
		}
		
		return newList;
	}

	private String[] findCommon(String[] parseString) {
		String[] newList = new String[parseString.length];
		
		int repeat = 0;
		for (int x=0, y=1; y < parseString.length; x+=2, y+=2) {
			// if x,y pair appears in a list put into new list
			for (int x1=0, y1=1; y1 < parseString.length; x1+=2, y1+=2)
				if (parseString[x].equals(parseString[x1]) && 
						parseString[y].equals(parseString[y1]))
					repeat++;
			if (repeat > 1) {
				newList[x] = parseString[x];
				newList[y] = parseString[y];
			}
			repeat = 0;
		}
		
		return newList;
	}

	private void getCurrentHour() {
		SimpleDateFormat df = new SimpleDateFormat("HH");
		Date date = new Date();
		currentHour = 3;// Integer.parseInt(df.format(date));
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
