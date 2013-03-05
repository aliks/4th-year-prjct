package com.android.alex.groupmanagement.ui;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.alex.groupmanagement.MyGrpActivity;
import com.android.alex.groupmanagement.R;
import com.android.alex.services.SoapService;
import com.android.alex.services.domain.DemandSpace;
import com.android.alex.services.utilities.UtilityClass;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class NearestFriendActivity extends MapActivity {

	static Context context;
	
	private DemandSpace ds;
	private SoapService ss;
	private String selectedGroupName;
	private String responseString;
	// current location latitude
	private Double current_latitude;
	private Double current_longitude;
	private Double[] closestFriend;
	// ui variables
	private MapView mapView;
	private GeoPoint p;
	private MapController mc;
	private CustomOverlay[] overlay;
	private Double x1;
	private Double y1;
	// GPS variables
	private Double[] gps;
	private LocationManager lm = null;
	private List<String> providers = null;
	private Location l = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearest_f_layout);
		
        // Get application context for later use
        context = getApplicationContext();
        
		Bundle b = getIntent().getExtras();
		// init variables
		selectedGroupName = b.getString("SelectedProperty");
		ss = new SoapService();
		closestFriend = new Double[4];
		overlay = new CustomOverlay[2];
		findMe();
		// make request
		responseString = findNN(selectedGroupName);
		
		if (responseString != null) {
			analyzeLocations();
		}
		drawResult();
	}
	/*
	 * draw map
	 * put nearest friend and users location as pins
	 */
	private void drawResult() 
	{
		mapView = (MapView) findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);

		Drawable drawable = this.getResources().getDrawable(
				R.drawable.marker);
		GooglePin itemizedoverlay = new GooglePin(drawable, this);
		List<Overlay> mapOverlays = mapView.getOverlays();
		
		// draw a blue circle showing user visibility region
		p = new GeoPoint((int) (current_latitude * 1E6), (int) (current_longitude * 1E6));
		overlay[0] = new CustomOverlay(p, UtilityClass.distance(current_latitude,current_longitude, x1, y1)*1000);
		mapView.getOverlays().add(overlay[0]);
		mc = mapView.getController();
		mc.animateTo(p);
		
		/*
		 *  add users current coordinates and draw a mark together with closest friend
		 *  if no friends found, draw current users location  
		 */
		if (responseString != null) {
			closestFriend[2] = current_latitude;
			closestFriend[3] = current_longitude;
		} else {
			closestFriend[0] = current_latitude;
			closestFriend[1] = current_longitude;
		}
		for (int x = 0, y = 1; y < closestFriend.length; x += 2, y += 2) {
			if (closestFriend[x] == null)
				continue;
			double lat = closestFriend[x];
			double lng = closestFriend[y];
			p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

			mapView.getController().setCenter(p);
			OverlayItem overlayitem = new OverlayItem(p, "Details", "FRIEND's LOCATION");
			if (x == 2 || responseString == null)
				overlayitem = new OverlayItem(p, "Dtails", "MY LOCATION");
			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);
			// new map pin
			drawable = this.getResources().getDrawable(R.drawable.marker2);
			itemizedoverlay = new GooglePin(drawable, this);
		}

		//mc.setZoom(20);
		mapView.invalidate();
	}
	// find current location
	private void findMe() {
		Double[] current_location = getLocation();
		if (current_location[0] != null) {
			current_latitude = current_location[0];
			current_longitude = current_location[1];
			Toast.makeText(NearestFriendActivity.this,"Location retrieved successfully", 
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(NearestFriendActivity.this,"Something went wrong, can't get location", 
					Toast.LENGTH_SHORT).show();
			current_latitude = 55.8738;
			current_longitude = -4.292178;
		}
	}
	// receive a string of the nearest neighbor's location
	private String findNN(String selectedGroupName) {
		// get number of members in a group
		int numberOfMembers = Integer.parseInt(ss.numberOfMembersInGroup(selectedGroupName));
		// create a demand space
		x1 = current_latitude + (1.0/(numberOfMembers*10));
		y1 = current_longitude + (1.0/(numberOfMembers*10));
		
		Double x2 = current_latitude - (1.0/(numberOfMembers*10));
		Double y2 = current_longitude - (1.0/(numberOfMembers*10));
		
//		Log.v("point x1 >",String.valueOf(x1));
//		Log.v("point x1 >",String.valueOf(y1));
//		Log.v("point x1 >",String.valueOf(x2));
//		Log.v("point x1 >",String.valueOf(y2));
		ds = new DemandSpace(x1,y1, x2,y2);
		// send demand space
		String rez = ss.findNearestFriend(selectedGroupName, ds);
		return rez;
	}
	
	// analyzes locations received from the server (LBS) 
	// returns closest neighbor location
	private void analyzeLocations() {
		Double d = null;
		Double shortestDistance = Double.MAX_VALUE;
		String[] rez = UtilityClass.parseString(responseString);
		for (int i = 0; i < rez.length; i += 2) {
			// calculate distance between current user and friend
			d = UtilityClass.distance(Double.valueOf(rez[i]),
					Double.valueOf(rez[i + 1]), current_latitude,
					current_longitude);
			if (d < shortestDistance) {
				shortestDistance = d;
				closestFriend[0] = Double.valueOf(rez[i]);
				closestFriend[1] = Double.valueOf(rez[i + 1]);
			}
		}
	}
	
	private Double[] getLocation() {
		gps = new Double[2];
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		providers = lm.getProviders(true);
		/*
		 * Loop over the array backwards, and if you get an accurate location,
		 * then break out the loop
		 */
		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		if (l != null) {
			gps[0] = l.getLatitude();
			gps[1] = l.getLongitude();
		}
		return gps;
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
