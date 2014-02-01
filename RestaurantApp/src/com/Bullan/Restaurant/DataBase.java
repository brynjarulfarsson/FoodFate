package com.Bullan.Restaurant;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
	
	//Database version
	private static final int DATABASE_VERSION = 1;
	
	//Database name
	private static final String DATABASE_NAME = " restaurantManager ";
	
	/* RESTAURANTS TABLE */
	//Table name
	private static final String TABLE_RESTAURANTS = " restaurants ";
	
	//Table columns
	private static final String	KEY_ID 		= "id";
	private static final String KEY_NAME	= "name";
	private static final String	KEY_RATING	= "rating";
	private static final String KEY_TYPE	= "type";
	
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
						+ KEY_NAME + " TEXT,"
						+ KEY_RATING + " INTEGER," 
						+ KEY_TYPE + "TEXT" + ")";
		db.execSQL(CREATE_RESTAURANT_TABLE);
		
//		String CREATE_CATEGORY_TABLE =
//				"CREATE TABLE"
//					+ TABLE_CATEGORIES + "("
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
		
		onCreate(db);
	}
	
	public void addRestaurant(Restaurant restaurant){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, restaurant.getName());
		values.put(KEY_RATING, restaurant.getRating());
		//values.put(KEY_TYPE, restaurant.getType());
		
		db.insert(TABLE_RESTAURANTS, null, values);
		db.close();
	}
	
	public Restaurant getRestaurant(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(
				TABLE_RESTAURANTS, 
				new String[] { KEY_ID, KEY_NAME, KEY_RATING, KEY_TYPE}, 
				KEY_ID + "=?", 
				new String[] {String.valueOf(id)}, 
				null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		Restaurant restaurant 
			= new Restaurant(
					Integer.parseInt(cursor.getString(0)), 
					cursor.getString(1),
					Integer.parseInt(cursor.getString(2)),
					cursor.getString(3)
					);
		
		return restaurant;
	}
	
	public List<Restaurant> getAllRestaurants(){
		List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		
		String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				Restaurant restaurant = 
					new Restaurant(
							Integer.parseInt(cursor.getString(0)), 
							cursor.getString(1),
							Integer.parseInt(cursor.getString(2)),
							cursor.getString(3)
							);
				restaurantList.add(restaurant);
			}while(cursor.moveToNext());
		}
		return restaurantList;
	}
	
	public Restaurant getRandomRestaurant(){
		String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
		
		SQLiteDatabase db = this.getReadableDatabase();
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
					Integer.parseInt(cursor.getString(2)),
					cursor.getString(3)
					);
	
		return restaurant;
	}
	
	public int updateRestaurant(Restaurant restaurant) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, restaurant.getName());
	    values.put(KEY_RATING, restaurant.getRating());
	    values.put(KEY_TYPE, restaurant.getType());
	 
	    // updating row
	    return db.update(TABLE_RESTAURANTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(restaurant.getId()) });
	}
	
	public int getContactsCount(){
		String countQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		
		return count;
	}
	
}
