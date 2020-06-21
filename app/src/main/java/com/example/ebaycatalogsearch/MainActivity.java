package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner sortBySpinner = findViewById(R.id.sortBy);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.sortByOptions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(adapter);
        sortBySpinner.setOnItemSelectedListener(this);

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

        // Check to make sure keyword field is NOT empty
        EditText keyword = findViewById(R.id.keyword);
        String keywordString = keyword.getText().toString();
        TextView keywordWarning = findViewById(R.id.keywordsWarning);

        if (keywordString == null || keywordString.length() == 0) {
            keywordWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else {
            keywordWarning.setVisibility(View.GONE);
        }

        // Check to make sure price ranges are valid
        EditText priceFrom = findViewById(R.id.priceFrom);
        EditText priceTo = findViewById(R.id.priceTo);

        String priceFromString = priceFrom.getText().toString();
        String priceToString = priceTo.getText().toString();
        TextView priceRangeWarning = findViewById(R.id.priceRangeWarning);

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
        }
    }
}