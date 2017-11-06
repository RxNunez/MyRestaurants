package com.epicodus.myrestaurants;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.epicodus.myrestaurants.R.id.listView;

public class FoodtrucksActivity extends AppCompatActivity {
    public static final String TAG = FoodtrucksActivity.class.getSimpleName();

    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(listView) ListView mListView;

    public ArrayList<Foodtruck> foodtrucks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodtrucks);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        mLocationTextView.setText("Here are all the foodtrucks near: " + location);
        getFoodtrucks(location);
    }

    private void getFoodtrucks(String location) {
        final YelpService yelpService = new YelpService();
        yelpService.findFoodtrucks(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                foodtrucks = yelpService.processResults(response);

                FoodtrucksActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String[] foodtruckNames = new String[foodtrucks.size()];
                        for (int i = 0; i < foodtruckNames.length; i++) {
                            foodtruckNames[i] = foodtrucks.get(i).getName();
                        }

                        ArrayAdapter adapter = new ArrayAdapter(FoodtrucksActivity.this,
                                android.R.layout.simple_list_item_1, foodtruckNames);
                        mListView.setAdapter(adapter);

                        for (Foodtruck foodtruck : foodtrucks) {
                            Log.d(TAG, "Name: " + foodtruck.getName());
                            Log.d(TAG, "Phone: " + foodtruck.getPhone());
                            Log.d(TAG, "Website: " + foodtruck.getWebsite());
                            Log.d(TAG, "Image url: " + foodtruck.getImageUrl());
                            Log.d(TAG, "Rating: " + Double.toString(foodtruck.getRating()));
                            Log.d(TAG, "Address: " + android.text.TextUtils.join(", ",foodtruck.getAddress()));
                            Log.d(TAG, "Categories: " + foodtruck.getCategories().toString());
                        }
                    }
                });
            }
        });
    }
}
