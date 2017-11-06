package com.epicodus.myrestaurants;

import android.content.Context;
import android.widget.ArrayAdapter;



public class FoodtrucksArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mFoodtrucks;
    private String[] mCuisines;

    public FoodtrucksArrayAdapter(Context mContext, int resource, String[] mFoodtrucks, String[] mCuisines) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mFoodtrucks = mFoodtrucks;
        this.mCuisines = mCuisines;
    }
    @Override
    public Object getItem(int position) {
        String foodtruck = mFoodtrucks[position];
        String cuisine = mCuisines[position];
        return String.format("%s \nServes great: %s", foodtruck, cuisine);
    }

    @Override
    public int getCount() {
        return mFoodtrucks.length;
    }
}
