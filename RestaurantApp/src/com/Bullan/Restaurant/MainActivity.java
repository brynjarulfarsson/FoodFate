package com.Bullan.Restaurant;

import java.util.List;

import com.Bullan.Restaurant.MyLocation.LocationResult;

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
		
		if(db.getContactsCount() == 0)
		{
			
			Log.d("Insert: ", "Inserting...");
			db.addRestaurant(new Restaurant("Hamborgarabúllan", 4, "Hamborgarar"));
			db.addRestaurant(new Restaurant("Hamborgara Fabrikkan", 3, "Hamborgarar"));
			db.addRestaurant(new Restaurant("Noodle Station", 4, "Asískt"));
			db.addRestaurant(new Restaurant("Svartakaffi", 4, "Súpur"));
			db.addRestaurant(new Restaurant("Devitos", 3, "Pizza"));
			db.addRestaurant(new Restaurant("Nings", 1, "Asískt"));
			db.addRestaurant(new Restaurant("Laundromat", 4, "Venjulegt"));
			db.addRestaurant(new Restaurant("Argentina", 4, "Steikhús"));
			db.addRestaurant(new Restaurant("Kolabrautin", 4, "Fine dine"));
			db.addRestaurant(new Restaurant("Hlöllabátar", 2, "Samlokur"));
		}
			
		Log.d("Reading ", "Reading all contacts..");
		restaurants = db.getAllRestaurants();
		
		for(Restaurant rst : restaurants){
			String log = "Id: " + rst.getId() + ", Name: " + rst.getName();
			Log.d("Name: ", log);
		}
		
//		restaurantCount.setText(restaurants.size()+"");
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
			restaurant = db.getRandomRestaurant();
		}catch (Exception e){ Log.d("Catched Error: ", e.getMessage());}
		
		if(restaurant == null)
			locationText.setText("Fail");
		else
			locationText.setText(restaurant.getName().toString());
	}

}
