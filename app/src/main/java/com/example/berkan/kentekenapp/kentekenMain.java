package com.example.berkan.kentekenapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import domain.Car;
import json.JSONParser;
import util.KentekenDataSource;


public class kentekenMain extends Activity {

    public Pattern[] arrSC = new Pattern[14];
    Car carObj = null;
    private KentekenDataSource datasource;
    private EditText ingevuldeKenteken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kenteken_main);
        ingevuldeKenteken = (EditText) findViewById(R.id.editText2);
        datasource = new KentekenDataSource(this);
        datasource.open();

    }

    public void history(View v) {
        Intent intent = new Intent(this, ListKentekensActivity.class);
        startActivity(intent);
    }

    public void OK(View v) {

        System.out.println(String.valueOf(ingevuldeKenteken));
        String kentekenInput = ingevuldeKenteken.getText().toString();
        String kentekenRegex = kentekenInput.replaceAll("[\\-\\+\\.\\^:,]", ""); // in het geval dat een gebruiker xx-xx-xx gebruikt of wat dan ook, dan wordt dat gereplaced
        System.out.print(kentekenRegex.toString());

        if (checkKenteken(kentekenRegex) != -1) {
            // Als kenteken klopt, dan pas mag de JSONParser het proberen
            new JSONParser(this, datasource).execute(kentekenRegex);


        } else {
            Toast.makeText(this, "Geen geldig kenteken", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kenteken_main, menu);
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


    public int checkKenteken(String kenteken) {
        kenteken = kenteken.replace("-", "").toUpperCase();

        // Verschillende patronen van de kentekens

        arrSC[0] = Pattern.compile("^[a-zA-Z]{2}[\\d]{2}[\\d]{2}$"); // 1 XX-99-99
        arrSC[1] = Pattern.compile("^[\\d]{2}[\\d]{2}[a-zA-Z]{2}$"); // 2 99-99-XX
        arrSC[2] = Pattern.compile("^[\\d]{2}[a-zA-Z]{2}[\\d]{2}$"); // 3 99-XX-99
        arrSC[3] = Pattern.compile("^[a-zA-Z]{2}[\\d]{2}[a-zA-Z]{2}$"); // 4 XX-99-XX
        arrSC[4] = Pattern.compile("^[a-zA-Z]{2}[a-zA-Z]{2}[\\d]{2}$"); // 5 XX-XX-99
        arrSC[5] = Pattern.compile("^[\\d]{2}[a-zA-Z]{2}[a-zA-Z]{2}$"); // 6 99-XX-XX
        arrSC[6] = Pattern.compile("^[\\d]{2}[a-zA-Z]{3}[\\d]{1}$"); // 7 99-XXX-9
        arrSC[7] = Pattern.compile("^[\\d]{1}[a-zA-Z]{3}[\\d]{2}$"); // 8 9-XXX-99
        arrSC[8] = Pattern.compile("^[a-zA-Z]{2}[\\d]{3}[a-zA-Z]{1}$"); // 9 XX-999-X
        arrSC[9] = Pattern.compile("^[a-zA-Z]{1}[\\d]{3}[a-zA-Z]{2}$"); // 10 X-999-XX
        arrSC[10] = Pattern.compile("/^[a-zA-Z]{3}[\\d]{2}[a-zA-Z]{1}$"); // 11 XXX-99-X
        arrSC[11] = Pattern.compile("/^[a-zA-Z]{1}[\\d]{2}[a-zA-Z]{3}$"); // 12 X-99-XXX
        arrSC[12] = Pattern.compile("/^[\\d]{1}[a-zA-Z]{2}[\\d]{3}$"); // 13 9-XX-999
        arrSC[13] = Pattern.compile("/^[\\d]{3}[a-zA-Z]{2}[\\d]{1}$"); // 14 999-XX-9

        Pattern scUitz = Pattern.compile("^CD[ABFJNST][0-9]{1,3}$"); //for example: CDB1 of CDJ45

        for (int i = 0; i < arrSC.length; i++) {
            if (arrSC[i].matcher(kenteken).matches()) {
                return i + 1;
            }
        }
        if (scUitz.matcher(kenteken).matches()) {
            return 0;
        }
        return -1;
    }


}
