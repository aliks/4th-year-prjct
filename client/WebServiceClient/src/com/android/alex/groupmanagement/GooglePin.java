package com.android.alex.groupmanagement;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

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
		return true;
	}
}
