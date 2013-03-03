package com.android.alex.services.utilities;

/**
 * class of static methods
 * @author alex
 *
 */
public class UtilityClass {
	/**
	 * 
	 * @param unformated str
	 * @return array of strings
	 */
	public static String[] parseString(String str) {
		return str.replace("[", "").replace("]", "").split("\\,\\s");
	}
	
	public static Double distance(Double lat1, Double lon1, 
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
	
	public static Double toRadians(Double value) {
		return value * Math.PI / 180;
	}
}
