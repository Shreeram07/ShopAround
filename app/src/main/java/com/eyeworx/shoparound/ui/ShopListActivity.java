package com.eyeworx.shoparound.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eyeworx.shoparound.AlertUserDialog;
import com.eyeworx.shoparound.CacheHelper;
import com.eyeworx.shoparound.R;
import com.eyeworx.shoparound.ShopListAdapter;
import com.eyeworx.shoparound.ShopListDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShopListActivity.this));
        recyclerView.setHasFixedSize(true);
        new AsyncFetch().execute();
    }

    //Fetching data asynchronously
    private class AsyncFetch extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String URI = getString(R.string.BOTTLE_ROCKET_API_KEY);

            if (isNetworkAvailable()) {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder().url(URI).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                       CacheHelper cacheHelper = new CacheHelper();
                         String result = response.body().string();
                       cacheHelper.writeData(result,ShopListActivity.this);
                        return result;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else AlertUser();
            return null;
        }

        @Override
        protected void onPostExecute(String jsonData) {

            //Intialize view with JSON Data
                if (jsonData != null) {
                    List<ShopListDataModel> shopList = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray array = jsonObject.getJSONArray("stores");
                        final int itemsInJSON = array.length();
                        for (int i = 0; i <= itemsInJSON; i++) {
                            JSONObject o = array.getJSONObject(i);
                            ShopListDataModel shopListDataModel = new ShopListDataModel();
                            shopListDataModel.setPhone(o.getString("phone"));
                            shopListDataModel.setAddress(o.getString("address"));
                            shopListDataModel.setLogoUrl(o.getString("storeLogoURL"));
                            shopList.add(shopListDataModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerAdapter = new ShopListAdapter(ShopListActivity.this, shopList);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();

                } else AlertUser();
            }
        }

     //Notify user under no internet or cache memory
        public void AlertUser() {
        AlertUserDialog dialog = new AlertUserDialog();
        dialog.show(getFragmentManager(), "Alert User");
    }

    //checks if Network is available in the device or emulator
        private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = null;
            if (manager != null) {
                activeNetwork = manager.getActiveNetworkInfo();
            }
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


}