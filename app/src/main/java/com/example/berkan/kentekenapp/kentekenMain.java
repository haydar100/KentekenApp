package com.example.berkan.kentekenapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;


public class kentekenMain extends Activity {
    Car carObj = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kenteken_main);
        // requestWebService("https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('')?$format=json")
        //https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('57ZHD2')?$format=json
    }

    public void OK(View v) {
        EditText ingevuldeKenteken = (EditText) findViewById(R.id.vulKenteken);
        EditText editText = (EditText) findViewById(R.id.getMerk);
        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        EditText editText1 = (EditText) findViewById(R.id.merkenType);


        try {
            System.out.println(String.valueOf(ingevuldeKenteken));
            String xyz = ingevuldeKenteken.getText().toString();
            carObj = new JSONParser().execute(xyz).get();

            editText.setText(carObj.toString());
            editText1.setText(carObj.getMerk());
            new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                    .execute(carObj.getImageUrl());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        alertView(carObj.getMerk() + " " + carObj.getKenteken() + " " + carObj.getAantalcilinders());

    }

    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Hello")
                .setIcon(R.mipmap.ic_launcher)

                .setMessage(message)
//  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
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
            bmImage.setImageBitmap(result);
        }
    }
}
