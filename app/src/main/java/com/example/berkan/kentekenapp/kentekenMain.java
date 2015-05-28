package com.example.berkan.kentekenapp;

import android.app.Activity;
import android.os.Bundle;
import android.sax.Element;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class kentekenMain extends Activity {

    JSONParser jParser = new JSONParser();


    public static final void leesbaar(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        System.out.println(out.toString());

    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = ((Document) eElement).getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new JSONParser().execute();
        setContentView(R.layout.activity_kenteken_main);
        // getCar("57ZHD2");
        // getCar("9THK73");

        // requestWebService("https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('57ZHD2')?$format=json")

        //https://api.datamarket.azure.com/Data.ashx/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT('57ZHD2')?$format=json
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

    public void getCar(final String kenteken) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.datamarket.azure.com/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT?$filter=Kenteken%20eq%20%27" + kenteken + "%27");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/atom+xml,application/xml; charset=utf-8");
                    con.connect();
                    InputStream xml = con.getInputStream();
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = null;
                    try {
                        db = dbf.newDocumentBuilder();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    }
                    try {
                        Document doc = db.parse(xml);
                        doc.getDocumentElement().normalize();
                        NodeList nList = doc.getElementsByTagName("m:properties").item(0).getChildNodes();
                        HashMap<String, String> values = new HashMap<String, String>();

                        for (int i = 0; i < nList.getLength(); i++) {

                            Node propertiesNode = nList.item(i);
                            System.out.println(propertiesNode.getNodeName());
                            if (propertiesNode.getFirstChild() == null) {
                                values.put(propertiesNode.getNodeName(), "");
                            } else {
                                values.put(propertiesNode.getNodeName(), propertiesNode.getFirstChild().getNodeValue());

                            }

                        }
                        Log.d("Kenteken auto", values.get("d:Kenteken"));
                        Log.d("Voertuigsoort", values.get("d:Voertuigsoort"));
                        Auto a = new Auto(values.get("d:Kenteken"), values.get("d:Merk"));


                        // leesbaar(doc);


                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }
}
