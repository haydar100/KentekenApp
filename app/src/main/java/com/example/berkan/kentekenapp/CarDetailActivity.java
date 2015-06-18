package com.example.berkan.kentekenapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;


public class CarDetailActivity extends Activity {
    Car carObj = null;
    private TextView kentekenContent, voertuigSoortContent, merkContent, typeContent,
            carrossieContent, brandstofContent, kleurContent, cilinderContent, zitplaatsenContent,
            vermogenContent, massaContent, maxMassaContent, bpmContent, gestolenContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        kentekenContent = (TextView) findViewById(R.id.kentekenContent);
        voertuigSoortContent = (TextView) findViewById(R.id.voertuigSoortContent);
        merkContent = (TextView) findViewById(R.id.merkContent);
        typeContent = (TextView) findViewById(R.id.typeContent);
        carrossieContent = (TextView) findViewById(R.id.carroserieContent);
        brandstofContent = (TextView) findViewById(R.id.brandstofContent);
        kleurContent = (TextView) findViewById(R.id.kleurContent);
        cilinderContent = (TextView) findViewById(R.id.cilinderContent);
        zitplaatsenContent = (TextView) findViewById(R.id.zitPlaatsenContent);
        vermogenContent = (TextView) findViewById(R.id.vermogenContent);
        massaContent = (TextView) findViewById(R.id.massaContent);
        maxMassaContent = (TextView) findViewById(R.id.maxMassaContent);
        bpmContent = (TextView) findViewById(R.id.bpmContent);
        gestolenContent = (TextView) findViewById(R.id.gestolenContent);


        carObj = (Car) getIntent().getSerializableExtra("carObject");


        if (carObj.getImageUrl() != null) {
            new DownloadImageTask((ImageView) findViewById(R.id.imageViewCarDetail))
                    .execute(carObj.getImageUrl());
        } else {
            carObj.setImageUrl("");
        }


        setTextViews();


    }

    public void setTextViews() {
        kentekenContent.setText(carObj.getKenteken());
        voertuigSoortContent.setText(carObj.getVoertuigsoort());
        merkContent.setText(carObj.getMerk());
        typeContent.setText(carObj.getHandelsbenaming());
        carrossieContent.setText("Niet beschikbaar");
        brandstofContent.setText(carObj.getHoofdbrandstof());
        kleurContent.setText(carObj.getEerstekleur());
        cilinderContent.setText(carObj.getAantalcilinders());
        zitplaatsenContent.setText(carObj.getAantalzitplaatsen());
        vermogenContent.setText(carObj.getVermogen() + " kW");
        massaContent.setText(carObj.getMassaleegvoertuig() + " kg");
        maxMassaContent.setText(carObj.getMassarijklaar() + " kg");
        bpmContent.setText("€ " + carObj.getBPM());
        gestolenContent.setText("Niet beschikbaar");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_detail, menu);
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


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (bmImage != null) {
                bmImage.setImageBitmap(result);
            }
        }

    }


}
