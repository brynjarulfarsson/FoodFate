package com.Bullan.DataBase;

import java.util.ArrayList;
import java.util.List;

import com.Bullan.BusinessObjects.Restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {
	
	//Database version
	private static final int DATABASE_VERSION = 3;
	
	//Database name
	private static final String DATABASE_NAME = " restaurantManager ";
	
	/* RESTAURANTS TABLE */
	//Table name
	private static final String TABLE_RESTAURANTS = " restaurants ";	
	//Table column names
	private static final String	KEY_ID 		= "id";
	private static final String KEY_NAME	= "name";
	private static final String KEY_LAT		= "latitude";
	private static final String KEY_LONG	= "longitude";
	//private static final String KEY_PICTURE = "picture";
	//private static final String KEY_RATING	= "rating";
	//private static final String KEY_TYPE	= "type";
	
	//Table name
	private static final String TABLE_CATEGORIES = " categories ";
	
	//Table columns
//	private static final String	KEY_ID 		= "id";
//	private static final String KEY_NAME	= "name";
//	private static final String	KEY_RATING	= "rating";
//	private static final String KEY_TYPE	= "type";
	
	
	public DataBase(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		String CREATE_RESTAURANT_TABLE = 
				"CREATE TABLE" 
						+ TABLE_RESTAURANTS + "("
						+ KEY_ID + " INTEGER PRIMARY KEY," 
						+ KEY_NAME + " TEXT NOT NULL,"
						+ KEY_LAT + " NUMBER NOT NULL," 
						+ KEY_LONG + " NUMBER NOT NULL" + ")";
		db.execSQL(CREATE_RESTAURANT_TABLE);
		
		Log.d("Initializing db: ", "Inserting...");
		InitializeDb(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
		onCreate(db);
	}
	
	private void InitializeDb(SQLiteDatabase db)
	{
		ArrayList<Restaurant> initRest = new ArrayList<Restaurant>();
		initRest.add(new Restaurant("Hamborgarabúllan", 64.126990, -21.896380));
		initRest.add(new Restaurant("Hamborgara Fabrikkan", 64.145583, -21.901466));
		initRest.add(new Restaurant("Noodle Station", 64.195583, -21.902486));
		initRest.add(new Restaurant("Svartakaffi", 64.245583, -21.931166));
		initRest.add(new Restaurant("Devitos", 64.445583, -21.901216));
		initRest.add(new Restaurant("Nings", 64.142383, -21.923466));
		initRest.add(new Restaurant("Laundromat", 64.645583, -21.401466));
		initRest.add(new Restaurant("Argentina", 64.345583, -21.501466));
		initRest.add(new Restaurant("Kolabrautin", 64.445583, -21.701466));
		initRest.add(new Restaurant("Hlöllabátar", 64.125283, -22.901466));
		
		if(db.isOpen())
		{
			for(Restaurant rest : initRest)
			{
				ContentValues values = new ContentValues();
				values.put(KEY_NAME, rest.Name);
				values.put(KEY_LAT, rest.Location.Latitude);
				values.put(KEY_LONG, rest.Location.Longitude);
				db.insert(TABLE_RESTAURANTS, null, values);
			}
		}		
	}
	
	public void addRestaurant(Restaurant restaurant){
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, restaurant.Name);
		values.put(KEY_LAT, restaurant.Location.Latitude);
		values.put(KEY_LONG, restaurant.Location.Longitude);
		
		
		db.insert(TABLE_RESTAURANTS, null, values);
		db.close();
	}
	
	public Restaurant getRestaurant(int id){
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.query(
				TABLE_RESTAURANTS, 
				new String[] { KEY_ID, KEY_NAME, KEY_LAT, KEY_LONG}, 
				KEY_ID + "=?", 
				new String[] {String.valueOf(id)}, 
				null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		Restaurant restaurant 
			= new Restaurant(
					Integer.parseInt(cursor.getString(0)), 
					cursor.getString(1),
					Double.parseDouble(cursor.getString(2)),
					Double.parseDouble(cursor.getString(3))
					);
		
		return restaurant;
	}
	
	public List<Restaurant> getAllRestaurants(){
		List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		
		String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				Restaurant restaurant = 
					new Restaurant(
							Integer.parseInt(cursor.getString(0)), 
							cursor.getString(1),
							Double.parseDouble(cursor.getString(2)),
							Double.parseDouble(cursor.getString(3))
							);
				restaurantList.add(restaurant);
			}while(cursor.moveToNext());
		}
		return restaurantList;
	}
	
	public Restaurant getRandomRestaurant(){
		String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor != null){
			int restaurantCount = cursor.getCount();
			int id = (int)(Math.random() * restaurantCount);
			cursor.moveToPosition(id);
		}
		
		Restaurant restaurant 
			= new Restaurant(
					Integer.parseInt(cursor.getString(0)), 
					cursor.getString(1),
					Double.parseDouble(cursor.getString(2)),
					Double.parseDouble(cursor.getString(3))
					);
	
		return restaurant;
	}
	
	public int updateRestaurant(Restaurant restaurant) {
	    SQLiteDatabase db = getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, restaurant.Name);
	    values.put(KEY_LAT, restaurant.Location.Latitude);
	    values.put(KEY_LONG, restaurant.Location.Longitude);
	 
	    // updating row
	    return db.update(TABLE_RESTAURANTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(restaurant.Id) });
	}
	
	public int getContactsCount(){
		String countQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		
		return count;
	}
	
}
