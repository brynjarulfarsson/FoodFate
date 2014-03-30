package com.Bullan.BusinessObjects;

public class AppLocation {
	public double Latitude;
	public double Longitude;
	
	public AppLocation(double latitude, double longitude)
	{
		Latitude = latitude;
		Longitude = longitude;
	}
	
	public double CalculateDistance(AppLocation toLocation)
	{
		return 3.4;
	}
}
