package com.Bullan.BusinessObjects;

import java.util.ArrayList;



public class Restaurant {
	public int Id;
	public String Name;
	public AppLocation Location;
	
	public Restaurant(int id, String name, double latitude, double longitude)
	{
		Id = id;
		Name = name;
		Location = new AppLocation(latitude, longitude);
	}
	
	public Restaurant(String name, double latitude, double longitude)
	{
		Id = -1;
		Name = name;
		Location = new AppLocation(latitude, longitude);
	}
}
