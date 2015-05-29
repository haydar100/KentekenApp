package com.example.berkan.kentekenapp;

import android.os.AsyncTask;
import android.os.Build;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Haydar on 28-05-15.
 */
public class JSONParser extends AsyncTask<String, String, Auto> {

    public JSONParser() {

    }


    public static JSONObject requestImageFromWebservice(String serviceUrl) {
        disableConnectionReuseIfNecessary();

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
        disableConnectionReuseIfNecessary();

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

    private static void disableConnectionReuseIfNecessary() {
        // see HttpURLConnection API doc
        if (Integer.parseInt(Build.VERSION.SDK)
                < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }


    @Override
    protected Auto doInBackground(String... strings) {
        String kenteken = strings[0];
        //String typeAuto = strings[1];
        JSONObject json = null;
        JSONObject json2 = null;
        JSONObject json3 = null;

        try {
            json = JSONParser.requestWebService("https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('" + kenteken + "')?$format=json").getJSONObject("d");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String kentteken1 = json.optString("Kenteken");
        String merk = json.optString("Merk");
        String handelsMerk = json.optString("Handelsbenaming");

        String Aantalzitplaatsen = json.optString("Aantalzitplaatsen");
        String test = "test";
        json2 = JSONParser.requestImageFromWebservice("http://api.flickr.com/services/feeds/photos_public.gne?nojsoncallback=?&tags=" + merk + "%20" + handelsMerk + "&format=json");
        //JSONObject objects =  json2.getJSONObject(1);
        //    String url = objects.getString("media");
        Gson gson = new Gson();

        Flickrsearchresult fsr = gson.fromJson(json2.toString(), Flickrsearchresult.class);
        String imageUrl = String.valueOf(fsr.getItems().get(0).getMedia());
        Auto a = new Auto(kentteken1, merk, fsr.getItems().get(0).getMedia().getM());



        return a;
    }


    private String getNodeAttributeByTagName(Node parentNode, String tagNameOfAttr) {
        String nodeValue = "";

        NamedNodeMap questNodeAttr = parentNode.getAttributes();

        if (questNodeAttr.getLength() != 0)
            nodeValue = questNodeAttr.getNamedItem(tagNameOfAttr).getTextContent();

        return nodeValue;
    }


}
