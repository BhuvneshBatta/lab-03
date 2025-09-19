package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AddCityFragment.AddCityDialogListener, EditCityFragment.OnEditCityListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    // Called by EditCityFragment when user saves edits
    @Override
    public void onCityEdited(int position, String newName, String newProvince) {
        City c = dataList.get(position);
        c.setName(newName);
        c.setProvince(newProvince);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // FAB → open AddCity dialog
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                new AddCityFragment().show(getSupportFragmentManager(), "Add City")
        );

        // Tap a row → open EditCity dialog pre-filled with that row's City object
        cityList.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            City current = dataList.get(position);
            EditCityFragment.newInstance(position, current)
                    .show(getSupportFragmentManager(), "Edit City");
        });
    }
}
