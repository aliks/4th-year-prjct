package com.android.alex.groupmanagement.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class CustomOverlay extends Overlay {

private GeoPoint point;
private Paint paint1, paint2;
private double radius; //in meters

public CustomOverlay(GeoPoint point, double radius) {
    this.point = point;

    paint1 = new Paint();
    paint1.setARGB(128, 0, 0, 255);
    paint1.setStrokeWidth(2);
    paint1.setStrokeCap(Paint.Cap.ROUND);
    paint1.setAntiAlias(true);
    paint1.setDither(false);
    paint1.setStyle(Paint.Style.STROKE);

    paint2 = new Paint();
    paint2.setARGB(64, 0, 0, 255);

    this.radius = radius;
}

@Override
public void draw(Canvas canvas, MapView mapView, boolean shadow) {

    Point pt = mapView.getProjection().toPixels(point, null);
    float projectedRadius = mapView.getProjection().metersToEquatorPixels((float) radius);

    canvas.drawCircle(pt.x, pt.y, projectedRadius, paint2);
    canvas.drawCircle(pt.x, pt.y, projectedRadius, paint1);

}

}