package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Constants
    public static final String REQUEST_URL = "com.example.ebaycatalogsearch.REQUEST_URL";
    public static final String REQUEST_KEYWORDS = "com.example.ebaycatalogsearch.REQUEST_KEYWORDS";

    // Form Fields
    private EditText keywords;
    private EditText priceFrom;
    private EditText priceTo;
    private CheckBox conditionNew;
    private CheckBox conditionUsed;
    private CheckBox conditionUnspecified;
    private Spinner sortBy;

    // Warnings
    private TextView keywordWarning;
    private TextView priceRangeWarning;

    // Request URL String
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Form fields
        keywords = findViewById(R.id.keyword);
        priceFrom = findViewById(R.id.priceFrom);
        priceTo = findViewById(R.id.priceTo);
        conditionNew = findViewById(R.id.conditionNew);
        conditionUsed = findViewById(R.id.conditionUsed);
        conditionUnspecified = findViewById(R.id.conditionUnspecified);
        sortBy = findViewById(R.id.sortBy);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.sortByOptions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBy.setAdapter(adapter);
        sortBy.setOnItemSelectedListener(this);

        // Initialize the warnings
        keywordWarning = findViewById(R.id.keywordsWarning);
        priceRangeWarning = findViewById(R.id.priceRangeWarning);

        // Make the urlString blank for now
        url = "";
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void search(View view) {

        // boolean to check if any fields are invalid
        boolean invalidFields = false;

        // Check to make sure keywords field is NOT empty
        String keywordString = keywords.getText().toString();

        if (keywordString == null || keywordString.length() == 0) {
            keywordWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else {
            keywordWarning.setVisibility(View.GONE);
        }

        // Check to make sure price ranges are valid
        String priceFromString = priceFrom.getText().toString();
        String priceToString = priceTo.getText().toString();

        if (!priceFromString.equals("") && Integer.parseInt(priceFromString) < 0) {
            priceRangeWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else if (!priceToString.equals("") && Integer.parseInt(priceToString) < 0) {
            priceRangeWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else if ((!priceFromString.equals("") && !priceToString.equals("")) &&
                (Integer.parseInt(priceFromString) > Integer.parseInt(priceToString))) {
            priceRangeWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else {
            priceRangeWarning.setVisibility(View.GONE);
        }

        if (invalidFields) {
            Toast.makeText(MainActivity.this, "Please fix all fields with errors",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Make the HTTP Request
            getItems();
        }
    }

    public void clear(View view) {

        // Remove ALL warnings
        keywordWarning.setVisibility(View.GONE);
        priceRangeWarning.setVisibility(View.GONE);

        // Clear/Reset ALL fields
        keywords.setText("");

        priceFrom.setText("");
        priceTo.setText("");

        conditionNew.setChecked(false);
        conditionUsed.setChecked(false);
        conditionUnspecified.setChecked(false);

        sortBy.setSelection(0);

    }

    private void getItems() {
        url = "https://mjwong-csci-571-hw8.wl.r.appspot.com/items?";

        // Add the Keywords
        url += "keywords=" + keywords.getText().toString();

        // Add the other parameters
        if (!priceFrom.getText().toString().equals("")) {
            url += "&priceFrom=" + priceFrom.getText().toString();
        }

        if (!priceTo.getText().toString().equals("")) {
            url += "&priceTo=" + priceTo.getText().toString();
        }

        if (conditionNew.isChecked()) {
            url += "&conditionNew=true";
        }

        if (conditionUsed.isChecked()) {
            url += "&conditionUsed=true";
        }

        if (conditionUnspecified.isChecked()) {
            url += "&conditionUnspecified=true";
        }

        // Add the sortBy field
        String sortByValue = "";

        if (sortBy.getSelectedItem().toString().equals("Best Match")) {
            sortByValue = "BestMatch";
        } else if (sortBy.getSelectedItem().toString().equals("Price: Highest first")) {
            sortByValue = "CurrentPriceHighest";
        } else if (sortBy.getSelectedItem().toString().equals("Price + Shipping: Highest first")) {
            sortByValue = "PricePlusShippingHighest";
        } else if (sortBy.getSelectedItem().toString().equals("Price + Shipping: Lowest first")) {
            sortByValue = "PricePlusShippingLowest";
        }

        url += "&sortBy=" + sortByValue;

        Log.e("MainActivity.java - url", url);
        Log.e("MainActivit.java - keywords", keywords.getText().toString());

        Intent intent = new Intent(getApplicationContext(), Catalog.class);
        intent.putExtra(REQUEST_KEYWORDS, keywords.getText().toString());
        intent.putExtra(REQUEST_URL, url);

        startActivity(intent);
    }
}