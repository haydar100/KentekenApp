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
import android.widget.Toast;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;


public class kentekenMain extends Activity {
    public Pattern[] arrSC = new Pattern[14];
    Car carObj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kenteken_main);

        // requestWebService("https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('')?$format=json")
        //https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('57ZHD2')?$format=json
    }

    public void OK(View v) {
        EditText ingevuldeKenteken = (EditText) findViewById(R.id.editText2);
        //EditText editText = (EditText) findViewById(R.id.getMerk);
        //ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        // EditText editText1 = (EditText) findViewById(R.id.merkenType);


        try {
            System.out.println(String.valueOf(ingevuldeKenteken));
            String xyz = ingevuldeKenteken.getText().toString();

            if (checkKenteken(xyz) != -1) {
                carObj = new JSONParser().execute(xyz).get();

            } else {
                Toast.makeText(this, "Geen geldig kenteken", Toast.LENGTH_LONG).show();

            }


            if (carObj != null) {
                // editText.setText(carObj.toString());
                //  editText1.setText(carObj.getMerk());
                alertView(carObj.getMerk() + " " + carObj.getKenteken() + "s " + carObj.getAantalcilinders());
                if (carObj.getImageUrl() != null) {
                    //   new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                    //     .execute(carObj.getImageUrl());
                } else {
                    carObj.setImageUrl("");
                }
            } else {
                Log.d("Car object is empty", "Car is empty");
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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

    public int checkKenteken(String licenseplate) {
        licenseplate = licenseplate.replace("-", "").toUpperCase();


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
            if (arrSC[i].matcher(licenseplate).matches()) {
                return i + 1;
            }
        }
        if (scUitz.matcher(licenseplate).matches()) {
            return 0;
        }
        return -1;
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
