package json;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.berkan.kentekenapp.CarDetailActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import domain.Car;
import domain.ImageResult;
import util.DateDeserializer;
import util.KentekenDataSource;

/**
 * Created by Haydar on 28-05-15.
 */
public class JSONParser extends AsyncTask<String, String, Car> {

    KentekenDataSource dataSource;
    ProgressDialog progress;
    Activity mActivity;


    public JSONParser() {
    }

    public JSONParser(Activity mActivity, KentekenDataSource dataSource) {
        this.mActivity = mActivity;
        this.dataSource = dataSource;
    }

    public static JSONObject requestImageFromWebservice(String serviceUrl) {

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            return new JSONObject(getResponseText(in));

        } catch (MalformedURLException e) {
            // URL is invalid
        } catch (SocketTimeoutException e) {
            // data retrieval or connection timed out
        } catch (IOException e) {
            // could not read response body
            // (could not create input stream)
        } catch (JSONException e) {
            // response body is no valid JSON string
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;

    }

    public static JSONObject requestWebService(String serviceUrl) {

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            if (in != null) {
                return new JSONObject(getResponseText(in));
            } else {
                Log.d("Geen auto", "geen auto");
            }

        } catch (MalformedURLException e) {
            // URL is invalid
        } catch (SocketTimeoutException e) {
            // data retrieval or connection timed out
        } catch (IOException e) {
            // could not read response body
            // (could not create input stream)
        } catch (JSONException e) {
            // response body is no valid JSON string
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(mActivity);
        progress.setMessage("Gegevens ophalen...");
        progress.show();
    }

    @Override
    protected Car doInBackground(String... strings) {
        Car carObj = null;
        String kenteken = strings[0];
        JSONObject rdwObj = null;
        JSONObject flickrJsonObj = null;

        try {
            // Gegevens in object stoppen die bij het kenteken horen, via de opendata van RDW

            rdwObj = JSONParser.requestWebService("https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('" + kenteken + "')?$format=json").getJSONObject("d");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();


        Car car = gson.fromJson(rdwObj.toString(), Car.class);

        String merk = car.getMerk();
        String handelsMerk = car.getHandelsbenaming();

        // Proberen een plaatje op te halen die past bij het kenteken, als er geen plaatje wordt gevonden dan een generiek plaatje van een auto

        String s = "http://api.flickr.com/services/feeds/photos_public.gne?nojsoncallback=?&tags=" + merk + " " + handelsMerk + "&format=json";
        s = s.replaceAll(" ", "%20");
        flickrJsonObj = JSONParser.requestImageFromWebservice(s);
        ImageResult fsr = gson.fromJson(flickrJsonObj.toString(), ImageResult.class);

        if (fsr.getItems().size() > 0) {
            // kijken of er een resultaat is , deze vervolgens setten in het carObject
            carObj = car;
            car.setImageUrl(fsr.getItems().get(0).getMedia().getM());

        } else {
            carObj = car;

        }


        // Car object uiteindelijk returnen
        return carObj;
    }

    @Override
    protected void onPostExecute(Car car) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        if (car != null) {
            Intent intent = new Intent(this.mActivity, CarDetailActivity.class);
            intent.putExtra("carObject", car);
            mActivity.startActivity(intent);

            dataSource.open();
            dataSource.createCar(car);

        } else {
            Log.d("Car object is empty", "Car is empty");
        }
        super.onPostExecute(car);
    }
}
