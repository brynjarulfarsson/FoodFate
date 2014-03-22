package com.Bullan.Ativities;

import java.util.List;
import java.util.Random;

import com.Bullan.BusinessLogic.MyLocation;
import com.Bullan.BusinessLogic.MyLocation.LocationResult;
import com.Bullan.BusinessObjects.Restaurant;
import com.Bullan.DataBase.DataBase;
import com.Bullan.Restaurant.R;
import com.Bullan.Restaurant.R.id;
import com.Bullan.Restaurant.R.layout;
import com.Bullan.Restaurant.R.menu;

import android.R.string;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	MyLocation myLocation;
	TextView locationText;
	TextView restaurantCount;
	List<Restaurant> restaurants;
	DataBase db;
	TextView TEEEEST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myLocation = new MyLocation();
		locationText = (TextView)findViewById(R.id.textView1);
		restaurantCount = (TextView)findViewById(R.id.textView2);
		
		db = new DataBase(this);
			
		Log.d("Reading ", "Reading all contacts..");
		restaurants = db.getAllRestaurants();
		
		for(Restaurant rst : restaurants){
			String log = "Id: " + rst.Id + ", Name: " + rst.Name;
			Log.d("Restaurant: ", log);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void GetLocation(View view){
		LocationResult locationResult = new LocationResult(){
			@Override
			public void gotLocation(Location location) {
				Toast.makeText(getApplicationContext(), "Latitude: "+location.getLatitude()+"\n Longitude: "+location.getLongitude() , Toast.LENGTH_SHORT).show();
//				Toast.makeText(getApplicationContext(), "Binni", Toast.LENGTH_SHORT).show();
			}
		};
		boolean worked = myLocation.getLocation(this, locationResult);
		
		
		locationText.setText(worked+" ");
	}
	
	public void getRestaurant(View view)
	{
		Restaurant restaurant = null;
		try{
			Random r = new Random();
			int rNumber = r.nextInt((restaurants.size()));
			restaurant = restaurants.get(rNumber);
		}catch (Exception e){ Log.d("Catched Error: ", e.getMessage());}
		
		if(restaurant == null)
			locationText.setText("Fail");
		else
			locationText.setText(restaurant.Id+", "+restaurant.Name+", "+restaurant.Location.Latitude+", "+restaurant.Location.Longitude);
	}

}
