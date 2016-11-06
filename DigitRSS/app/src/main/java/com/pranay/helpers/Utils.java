package com.pranay.helpers;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.pranay.models.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by pranay on 24/09/16.
 */

public class Utils {

    public static ArrayList<String> titleList = new ArrayList<>();
    public static ArrayList<String> authorList = new ArrayList<>();
    public static JSONObject xmlJSONObj;
    public static ArrayList<FeedItem> feedItemArrayList = new ArrayList<>();

    public static ArrayList<FeedItem> getFeedList(){



        String response = null;
        try {
            response = getResponseText();

            xmlJSONObj = XML.toJSONObject(response);
            parseJSON(xmlJSONObj);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return feedItemArrayList;
    }

    public static InputStream getInputStream(URL url){

        try {

            return url.openConnection().getInputStream();
        }
        catch (IOException e){
            return null;
        }
    }

    private static String getResponseText() throws IOException
    {
        StringBuilder response  = new StringBuilder();
        String stringUrl = "http://feeds.feedburner.com/digit/latest-from-digit";
        URL url = new URL(stringUrl);
        HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
            String strLine = null;
            while ((strLine = input.readLine()) != null)
            {
                response.append(strLine);
            }
            input.close();
        }
        return response.toString();
    }

    public static void parseJSON(JSONObject jSONObj){

        try {
            JSONObject rssJsonObject= jSONObj.getJSONObject("rss");
            JSONObject channelJsonObject = rssJsonObject.getJSONObject("channel");
            JSONArray itemJsonArray = channelJsonObject.getJSONArray("item");

            for(int i = 0 ;i< itemJsonArray.length() ; i++){
                FeedItem feedItem = new FeedItem();
                feedItem.setTitle(itemJsonArray.getJSONObject(i).getString("title"));
                feedItem.setPostAuthor(itemJsonArray.getJSONObject(i).getString("author"));
                feedItem.setImageLink(itemJsonArray.getJSONObject(i).getJSONObject("media:thumbnail").getString("url"));
                feedItem.setNavigationLink(itemJsonArray.getJSONObject(i).getString("link"));

                feedItemArrayList.add(feedItem);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getImage(){



    }
}

