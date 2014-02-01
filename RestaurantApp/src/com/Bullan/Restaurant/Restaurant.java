package com.Bullan.Restaurant;


public class Restaurant {
	int _Id;
	String _Name;
	int _Rating;
	String _Type;
	
	public Restaurant(String name, int rating, String type){
		this._Name = name;
		this._Rating = rating;
		this._Type = type;
	}
	
	public Restaurant(int id, String name, int rating, String type)
	{
		this._Id = id;
		this._Name = name;
		this._Rating = rating;
		this._Type = type;
	}
	
	public int getId() {return _Id;}
	
	public String getName() {return _Name;}
	
	public int getRating() {return _Rating;}
	
	public String getType() {return _Type;}
}
