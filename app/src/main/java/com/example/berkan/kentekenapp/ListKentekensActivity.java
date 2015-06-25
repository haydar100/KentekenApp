package com.example.berkan.kentekenapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
        datasource = new KentekenDataSource(this);
        datasource.open();

        final List<Car> carList = datasource.getAllCars();

        ArrayAdapter<Car> adapter = new ArrayAdapter<Car>(this,
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
                        try {
                            car = new JSONParser().execute(kenteken).get();


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Intent intent = new Intent(ListKentekensActivity.this, CarDetailActivity.class);
                intent.putExtra("carObject", car);
                startActivity(intent);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
