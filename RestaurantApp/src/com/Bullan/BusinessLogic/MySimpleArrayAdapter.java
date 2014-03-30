package com.Bullan.BusinessLogic;

import java.util.ArrayList;
import java.util.List;

import com.Bullan.BusinessObjects.Restaurant;

//import android.R;
import com.Bullan.Restaurant.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<Restaurant>{

	private final Context context;
	private final ArrayList<Restaurant> restaurants;
	
	public MySimpleArrayAdapter(Context context, ArrayList<Restaurant> objects) {
		super(context, R.layout.restaurant_layout, objects);
		this.context = context;
		this.restaurants = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View rowView = null;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowView = inflater.inflate(R.layout.restaurant_layout, parent, false);
		
		TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
		TextView tvLocation = (TextView) rowView.findViewById(R.id.tvLocation);
		
		Restaurant res = restaurants.get(position);
		tvName.setText(res.Name);
		tvLocation.setText(res.Location.Latitude+", "+res.Location.Longitude);
		
		return rowView;
	}

}
