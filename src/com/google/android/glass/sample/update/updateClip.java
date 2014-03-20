package com.google.android.glass.sample.update;

import android.app.Activity;
import android.util.Log;
import org.apache.http.HttpResponse;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.client.*;

/**
 * Created by Jesper Persson on 22/01/14.
 */
public class updateClip extends Activity {

    public String getXmlFromUrl(String url) {
        String xml = null;
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            xml = httpClient.execute(httpget, responseHandler);

            Log.v("UpdateApp xml",xml);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }
    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    public String requestLatest( ) {
        // All static variables
        String URL = "http://www.dr.dk/Forms/Published/rssNewsFeed.aspx?config=687f7dc1-8631-42a2-a09d-4ffa3ff323bc&rss=No&rssTitle=&overskrift=&Url=&PriorityPlacement=Yes&PubDate=Yes&ImageUri=Yes&AllImages=Yes&ImageText=Yes&Medialinks=Yes&Chapters=Yes&Flashlinks=Yes&BroadcastTime=Yes&escaping=UTF8";

        // XML node keys
        String KEY_ITEM = "item"; // parent node
        String KEY_NAME = "DR:Link";
        Log.v("UpdateApp xml",URL);
        String xml = getXmlFromUrl(URL); // getting XML
        Log.v("UpdateApp xml",xml);
        Document doc = getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_ITEM);
        String name = "";
        // looping through all item nodes <item>     
        //for (int i = 0; i < nl.getLength(); i++) {

            Element e = (Element) nl.item(0);
            name = getValue(e, KEY_NAME); // name child value

        //}
        Log.v("UpdateApp",name);
        return "http://www.dr.dk"+name;
    }


}

