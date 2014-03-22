package com.Bullan.BusinessObjects;

public class Location {
	public double Latitude;
	public double Longitude;
	
	public Location(double latitude, double longitude)
	{
		Latitude = latitude;
		Longitude = longitude;
	}
	
	public double CalculateDistance(Location toLocation)
	{
		return 3.4;
	}
}
