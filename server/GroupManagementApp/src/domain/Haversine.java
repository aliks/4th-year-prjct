package domain;

public class Haversine {
	
	private Double[] mid_point;
	private double radiusBetweenOriginPoints;
	private Double x1;
	private Double y1;
	// bottom left corner
	private Double x2;
	private Double y2;
	
	public Haversine(DemandSpace ds) {
		x1 = ds.getX1();
		y1 = ds.getY1();
		x2 = ds.getX2();
		y2 = ds.getY2();
	}
	
	public void midpoint() {
		mid_point = calculateMidPoint(x1,y1,x2,y2);
	}
	
	public void radiusBetween() {
		radiusBetweenOriginPoints = distance(x1,y1,x2,y2)/2;
	}
	
	public boolean isInDemandSpace(Double lat, Double lon) {
		Double distanceBetweenNewPoints = distance(lat, lon, mid_point[0], mid_point[1]);
		distanceBetweenNewPoints = round(distanceBetweenNewPoints, 10);
		radiusBetweenOriginPoints = round(radiusBetweenOriginPoints, 10);
		return (distanceBetweenNewPoints > radiusBetweenOriginPoints) ? false : true;
	}

	private Double[] calculateMidPoint(Double lat1, Double lon1, 
			Double lat2, Double lon2) {
		Double dLon = toRadians(lon2 - lon1);
		
		lat1 = toRadians(lat1);
		lat2 = toRadians(lat2);
		lon1 = toRadians(lon1);
		lon2 = toRadians(lon2);
		
		Double Bx = Math.cos(lat2) * Math.cos(dLon);
		Double By = Math.cos(lat2) * Math.sin(dLon);
		
		Double point_x = Math.atan2(Math.sin(lat1)+Math.sin(lat2), 
				Math.sqrt( Math.pow((Math.cos(lat1)+Bx),2)+Math.pow(By,2) ));
		Double point_y = lon1 + Math.atan2(By, Math.cos(lat1)+Bx);
		return new Double[]{point_x*180/Math.PI, point_y*180/Math.PI};
	}
	
	private Double distance(Double lat1, Double lon1, 
			Double lat2, Double lon2) {
		Double R = 6378.137;
		
		Double dLat = toRadians(lat2 - lat1);
		Double dLon = toRadians(lon2 - lon1);
		
		lat1 = toRadians(lat1);
		lat2 = toRadians(lat2);
		lon1 = toRadians(lon1);
		lon2 = toRadians(lon2);
		
		Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ (Math.sin(dLon / 2) * Math.sin(dLon / 2))
				* (Math.cos(lat1) * Math.cos(lat2));
		Double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R*c;
	}
	
	private Double round(double d, int numbersAfterDecimalPoint) {
	    long n = (long) Math.pow(10, numbersAfterDecimalPoint);
	    double d2 = d * n;
	    long l = (long) d2;
	    return ((double) l) / n;
	}
	
	private Double toRadians(Double value) {
		return value * Math.PI/180;
	}

}
