package com.example.berkan.kentekenapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Haydar on 28-05-15.
 */
public class JSONParser extends AsyncTask<String, String, Car> {

    public JSONParser() {
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
    protected Car doInBackground(String... strings) {
        Car carObj = null;
        String kenteken = strings[0];
        JSONObject rdwObj = null;
        JSONObject flickrJsonObj = null;
        JSONObject json3 = null;

        try {
            rdwObj = JSONParser.requestWebService("https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('" + kenteken + "')?$format=json").getJSONObject("d");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        //DotNetDeserializer ds = new DotNetDeserializer();
        // gsonBuilder.registerTypeAdapter(Car.class, ds);
        Gson gson = gsonBuilder.create();

        Car car = gson.fromJson(rdwObj.toString(), Car.class);


        String merk = car.getMerk();
        String handelsMerk = car.getHandelsbenaming();

        String s = "http://api.flickr.com/services/feeds/photos_public.gne?nojsoncallback=?&tags=" + merk + " " + handelsMerk + "&format=json";
        s = s.replaceAll(" ", "%20");
        flickrJsonObj = JSONParser.requestImageFromWebservice(s);
        ImageResult fsr = gson.fromJson(flickrJsonObj.toString(), ImageResult.class);

        if (fsr.getItems().size() > 0) {

            carObj = car;
            car.setImageUrl(fsr.getItems().get(0).getMedia().getM());

        } else {
            carObj = car;

        }



        return carObj;
    }


    public static class DotNetDeserializer implements JsonDeserializer {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonNull())
                return null;

            String s = json.getAsJsonPrimitive().getAsString();
            long l = Long.parseLong(s.substring(6, s.length() - 2));
            Date d = new Date(l);
            return d;
        }
    }


}
