package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleItem extends AppCompatActivity {

    private String productID;
    private String title;

    // Request Object
    private RequestQueue requestQueue;

    // Classes for Tabular View
    private SectionsPageAdapter sectionsPageadapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        // Initialize the variables passed by intent
        Intent intent = getIntent();
        productID = intent.getStringExtra("productID");
        title = intent.getStringExtra("title");

        // Set the Single Item Title
        this.setTitle(title);

        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Make the getSingleItem request
        getSingleItem();

        // Tab View Creation
        sectionsPageadapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = sectionsPageadapter;
        adapter.addFragment(new ProductFragment(), "Product");
        adapter.addFragment(new SellerInfoFragment(), "Seller Info");
        adapter.addFragment(new ShippingFragment(), "Shipping");
        viewPager.setAdapter(adapter);
    }

    private void getSingleItem() {

        String requestUrl = "https://mjwong-csci-571-hw8.wl.r.appspot.com/singleItem?productID=" + productID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}