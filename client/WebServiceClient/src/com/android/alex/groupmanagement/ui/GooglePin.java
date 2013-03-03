package com.android.alex.groupmanagement.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class GooglePin extends ItemizedOverlay 
{
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;

	public GooglePin(Drawable marker) {
		super(boundCenterBottom(marker));
	}

	public GooglePin(Drawable marker, Context context) {
		super(boundCenterBottom(marker));
		mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int i) {
		// when you tap on the marker this will show the informations provided
        GeoPoint  gpoint = mOverlays.get(i).getPoint();
        double lat = gpoint.getLatitudeE6()/1e6;
        double lon = gpoint.getLongitudeE6()/1e6;
        String toast = mOverlays.get(i).getSnippet();
        toast += 	"\nCoordinates: Latitude = "+lat+" Longitude = "+lon;
        Toast.makeText(NearestFriendActivity.context, toast, Toast.LENGTH_LONG).show();
		return true;
	}
}
