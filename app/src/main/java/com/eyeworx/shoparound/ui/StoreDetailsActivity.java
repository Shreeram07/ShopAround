package com.eyeworx.shoparound.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyeworx.shoparound.AlertUserDialog;
import com.eyeworx.shoparound.CacheHelper;
import com.eyeworx.shoparound.R;
import com.eyeworx.shoparound.ShopListDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StoreDetailsActivity extends AppCompatActivity {
    CacheHelper cacheHelper = new CacheHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        if(cacheHelper.isCacheAvailable(StoreDetailsActivity.this)) {
            loadFromCache();
        }
        else {
            AlertUserDialog dialog = new AlertUserDialog();
            dialog.show(getFragmentManager(),"Cache unavailable");
        }
    }
    //Retrieve JSON data from Cache memory
    public void loadFromCache() {
        JSONObject result = cacheHelper.readData(this);
        JSONArray array = null;
            ShopListDataModel storeDetails = new ShopListDataModel();
        try {
            array = result.getJSONArray("stores");
            int index = getIntent().getIntExtra("position", 0);
            if (array != null) {
                JSONObject o = array.getJSONObject(index);
                storeDetails.setPhone(o.getString("phone"));
                storeDetails.setAddress(o.getString("address"));
                storeDetails.setLogoUrl(o.getString("storeLogoURL"));
                storeDetails.setName(o.getString("name"));
                storeDetails.setCity(o.getString("city"));
                storeDetails.setStoreId(o.getString("storeID"));
                storeDetails.setLatitude(o.getString("latitude"));
                storeDetails.setLongitude(o.getString("longitude"));
                storeDetails.setState(o.getString("state"));
                storeDetails.setZipcode(o.getString("zipcode"));
            }
        }
            catch(JSONException e){
                e.printStackTrace();
            }

        //Initialize views to their respective ID's
            TextView name = findViewById(R.id.name);
            TextView address = findViewById(R.id.address);
            TextView city = findViewById(R.id.city);
            TextView phone = findViewById(R.id.phone);
            TextView state = findViewById(R.id.state);
            TextView storeID = findViewById(R.id.storeID);
            ImageView logo = findViewById(R.id.logo);
            TextView zipcode = findViewById(R.id.zip);
            TextView latitude = findViewById(R.id.latitude);
            TextView longitude = findViewById(R.id.longitude);

        //Set views with resources
        name.setText("Name : "+ storeDetails.getName());
        address.setText("Address : "+storeDetails.getAddress());
        city.setText("City : "+storeDetails.getCity());
        phone.setText("Phone : "+storeDetails.getPhone());
        state.setText("State : "+storeDetails.getState());
        storeID.setText("StoreID : "+storeDetails.getStoreId());
        zipcode.setText("Zipcode : "+storeDetails.getZipcode());
        latitude.setText("Latitude : "+storeDetails.getLatitude());
        longitude.setText("Longitude : "+storeDetails.getLongitude());
        Glide.with(this).load(storeDetails.getLogoUrl()).into(logo);
        }
}
