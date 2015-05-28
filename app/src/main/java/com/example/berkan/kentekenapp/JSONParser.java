package com.example.berkan.kentekenapp;

import android.os.AsyncTask;
import android.os.Build;

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

        JSONObject json = null;
        try {
            json = JSONParser.requestWebService("https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('" + kenteken + "')?$format=json").getJSONObject("d");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String kentteken1 = json.optString("Kenteken");
        String merk = json.optString("Merk");
        String Aantalzitplaatsen = json.optString("Aantalzitplaatsen");
        String test = "test";
        Auto a = new Auto(kentteken1, merk);
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
