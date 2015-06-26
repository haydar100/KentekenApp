package com.example.berkan.kentekenapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import domain.Car;
import json.JSONParser;
import util.KentekenDataSource;


public class ListKentekensActivity extends ListActivity {
    ListView listView;
    private KentekenDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kentekens);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        datasource = new KentekenDataSource(this);
        datasource.open();

        final List<Car> carList = datasource.getAllCars();

        final ArrayAdapter<Car> adapter = new ArrayAdapter<Car>(this,
                android.R.layout.simple_list_item_1, carList);

        setListAdapter(adapter);


        listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car car = null;

                for (int i = 0; i < carList.size(); i++) {
                    if (carList.get(position) != null) {
                        
                        String kenteken = carList.get(position).getKenteken();
                        new JSONParser(ListKentekensActivity.this, datasource).execute(kenteken);

                    }
                }


            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {
                Car selectedItem = carList.get(position);
                datasource.deleteCar(selectedItem);
                carList.remove(selectedItem);
                adapter.notifyDataSetChanged();

                return true;
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_kentekens, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
