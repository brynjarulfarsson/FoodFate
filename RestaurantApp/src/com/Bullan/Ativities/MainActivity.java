package com.Bullan.Ativities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.Bullan.BusinessLogic.MyLocation;
import com.Bullan.BusinessLogic.MyLocation.LocationResult;
import com.Bullan.BusinessLogic.MySimpleArrayAdapter;
import com.Bullan.BusinessObjects.AppLocation;
import com.Bullan.BusinessObjects.Restaurant;
import com.Bullan.DataBase.DataBase;
import com.Bullan.GoogleManager.PlacesAPI;
import com.Bullan.Restaurant.R;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	MyLocation myLocation;
	TextView locationText;
	TextView restaurantCount;
	List<Restaurant> restaurants;
	DataBase db;
	TextView TEEEEST;
	ListView resultListView;
	AppLocation currLocation;
	public Location currentLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		myLocation = new MyLocation();
//		locationText = (TextView)findViewById(R.id.textView1);
		restaurantCount = (TextView)findViewById(R.id.textView2);
		resultListView = (ListView)findViewById(R.id.listView1);
		
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
	
//	public void GetLocation(View view){
//		LocationResult locationResult = new LocationResult(){
//			@Override
//			public void gotLocation(Location location) {
////				Toast.makeText(getApplicationContext(), "Latitude: "+location.getLatitude()+"\n Longitude: "+location.getLongitude() , Toast.LENGTH_SHORT).show();
////				Toast.makeText(getApplicationContext(), "Binni", Toast.LENGTH_SHORT).show();
//				 currLocation = new AppLocation(location.getLatitude(),location.getLongitude());
//			}
//		};
//		boolean worked = myLocation.getLocation(this, locationResult);
//		
//		Toast.makeText(getApplicationContext(),worked+" ",Toast.LENGTH_SHORT).show();
//	}
	
	public boolean getLocation()
	{
		LocationResult locationResult = new LocationResult(){
			@Override
			public void gotLocation(Location location) {
				Toast.makeText(getApplicationContext(), "Latitude: "+location.getLatitude()+"\n Longitude: "+location.getLongitude() , Toast.LENGTH_SHORT).show();
//				Toast.makeText(getApplicationContext(), "Binni", Toast.LENGTH_SHORT).show();
				if(location != null)
				{
					try {
						MainActivity.this.responseFromLocation(location);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		boolean worked = myLocation.getLocation(this, locationResult);

		Toast.makeText(getApplicationContext(),worked+" ",Toast.LENGTH_SHORT).show();
		return worked;
	}
	
	public void responseFromLocation(Location location) throws ClientProtocolException, IOException, JSONException
	{
		currentLocation = location;
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		if(currentLocation==null)
			restaurants.add(new Restaurant("No coordinates", 0,0));
		else
			restaurants = PlacesAPI.getAllRestaurants(new AppLocation(currentLocation.getLatitude(),currentLocation.getLongitude()), 2000);
		
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, restaurants);
		
		resultListView.setAdapter(adapter);
	}
	
//	public AppLocation getLocation()
//	{
//		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//		// check if enabled and if not send user to the GSP settings
//		// Better solution would be to display a dialog and suggesting to 
//		// go to the settings
//		if (!enabled) {
//		  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//		  startActivity(intent);
//		} 
//		
//		
//		Criteria criteria = new Criteria();
//		String provider = locationManager.getBestProvider(criteria, false);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1000, );
//		Location location = locationManager.getLastKnownLocation(provider);
//		
//		if(location != null)
//		{
//			currLocation = new AppLocation(location.getLatitude(), location.getLongitude());
//			Toast.makeText(getApplicationContext(),location.getAccuracy()+" ",Toast.LENGTH_SHORT).show();
//		}
//	}
	
	public void getRestaurant(View view) throws IOException, JSONException
	{
		
		
		
		
		boolean isLocation = getLocation();
		
		
		
//		locationText.setText(length);
	
	}

}
