package com.Bullan.GoogleManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Bullan.BusinessObjects.AppLocation;
import com.Bullan.BusinessObjects.Restaurant;

public class PlacesAPI {

	private static JSONArray fetchRequest(String strUrl) throws ClientProtocolException, IOException, JSONException
	{
		HttpGet req = new HttpGet(strUrl);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(req);
		
		BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		StringBuilder sb = new StringBuilder();
		String s = null;
		while ((s = r.readLine()) != null) { sb.append(s); }
		String result = sb.toString();
		
		JSONObject jObject = new JSONObject(result);
		JSONArray jArray = jObject.getJSONArray("results");
		
		return jArray;
	}
	
	public static Restaurant getSingleRandomRestaurant(AppLocation location, int radius) throws JSONException, ClientProtocolException, IOException
	{
		String strUrl = "https://maps.googleapis.com/maps/api/place/search/json" +
				"?location="+location.Latitude+","+location.Longitude +
				"&radius="+radius +
				"&sensor=false" +
				"&types=restaurant" +
				"&key=AIzaSyDXVqdC5SF6puGhiZjHJ6ob1w1mfo0TYDY";
		
		JSONArray jArray = fetchRequest(strUrl);
		
		Random r = new Random();
		int rNumber = r.nextInt(jArray.length());
		JSONObject jObject = jArray.getJSONObject(rNumber);
	
		return new Restaurant(
				jObject.getString("name"), 
				jObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
				jObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
	}
	
	public static ArrayList<Restaurant> getAllRestaurants(AppLocation location, int radius) throws ClientProtocolException, IOException, JSONException
	{
		String strUrl = "https://maps.googleapis.com/maps/api/place/search/json" +
				"?location="+location.Latitude+","+location.Longitude +
				"&radius="+radius +
				"&sensor=false" +
				"&types=restaurant" +
				"&key=AIzaSyDXVqdC5SF6puGhiZjHJ6ob1w1mfo0TYDY";
		
		JSONArray jArray = fetchRequest(strUrl);
		
		ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
		
		for(int i = 0; i<jArray.length(); i++)
		{
			JSONObject jObject = jArray.getJSONObject(i);
			Restaurant r = new Restaurant(
								jObject.getString("name"),
								jObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
								jObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
			restaurantList.add(r);
		}
		
		return restaurantList;
	}
	
}
