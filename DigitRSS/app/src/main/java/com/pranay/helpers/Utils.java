package com.pranay.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pranay.models.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pranay on 24/09/16.
 */

public class Utils {

    public static ArrayList<String> titleList = new ArrayList<>();
    public static ArrayList<String> authorList = new ArrayList<>();
    public static JSONObject xmlJSONObj;
    public static ArrayList<FeedItem> feedItemArrayList = new ArrayList<>();
    public static Context mContext;

    public static ArrayList<FeedItem> getFeedList(Context context){

        mContext = context;

        String response = null;
        try {
            response = getResponseText();

            xmlJSONObj = XML.toJSONObject(response);
            parseJSON(xmlJSONObj);
            insertFeedItemsInDB(feedItemArrayList,mContext);


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

    public void getImage(){    }

    public static void insertFeedItemsInDB(ArrayList<FeedItem> feedItems, Context mContext){

        RssFeedDbHelper mDbHelper = new RssFeedDbHelper(mContext);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+ RssFeedDbHelper.FeedEntry.TABLE_NAME);

        for(FeedItem feedItem : feedItems){
            ContentValues values = new ContentValues();
            values.put(RssFeedDbHelper.FeedEntry.COLUMN_NAME_TITLE,feedItem.getTitle());
            values.put(RssFeedDbHelper.FeedEntry.COLUMN_NAME_DESCRIPTION,feedItem.getDescription());
            values.put(RssFeedDbHelper.FeedEntry.COLUMN_NAME_IMAGE_LINK,feedItem.getImageLink());
            values.put(RssFeedDbHelper.FeedEntry.COLUMN_NAME_POST_AUTHOR,feedItem.getPostAuthor());
            values.put(RssFeedDbHelper.FeedEntry.COLUMN_NAME_PUB_DATE,feedItem.getPubDate());
            values.put(RssFeedDbHelper.FeedEntry.COLUMN_NAME_NAVIGATION_LINK,feedItem.getNavigationLink());
            db.insert(RssFeedDbHelper.FeedEntry.TABLE_NAME,null,values);

        }

        //db.close();
    }

    public static ArrayList<FeedItem> getFeedItemsFromDB(Context mContext){
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        RssFeedDbHelper mDbHelper = new RssFeedDbHelper(mContext);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
            RssFeedDbHelper.FeedEntry.COLUMN_NAME_TITLE,
            RssFeedDbHelper.FeedEntry.COLUMN_NAME_DESCRIPTION,
            RssFeedDbHelper.FeedEntry.COLUMN_NAME_POST_AUTHOR,
            RssFeedDbHelper.FeedEntry.COLUMN_NAME_PUB_DATE,
            RssFeedDbHelper.FeedEntry.COLUMN_NAME_IMAGE_LINK,
            RssFeedDbHelper.FeedEntry.COLUMN_NAME_NAVIGATION_LINK

        };


        Cursor c = db.query(RssFeedDbHelper.FeedEntry.TABLE_NAME,projection,null,null,null,null,null);
        c.moveToFirst();
        do{
            FeedItem feedItem = new FeedItem();
            feedItem.setTitle(c.getString(c.getColumnIndexOrThrow(RssFeedDbHelper.FeedEntry.COLUMN_NAME_TITLE)));
            feedItem.setDescription(c.getString(c.getColumnIndexOrThrow(RssFeedDbHelper.FeedEntry.COLUMN_NAME_TITLE)));
            feedItem.setImageLink(c.getString(c.getColumnIndexOrThrow(RssFeedDbHelper.FeedEntry.COLUMN_NAME_IMAGE_LINK)));
            feedItem.setPostAuthor(c.getString(c.getColumnIndexOrThrow(RssFeedDbHelper.FeedEntry.COLUMN_NAME_POST_AUTHOR)));
            feedItem.setPubDate(c.getString(c.getColumnIndexOrThrow(RssFeedDbHelper.FeedEntry.COLUMN_NAME_PUB_DATE)));
            feedItem.setNavigationLink(c.getString(c.getColumnIndexOrThrow(RssFeedDbHelper.FeedEntry.COLUMN_NAME_NAVIGATION_LINK)));

            feedItems.add(feedItem);
            c.moveToNext();

        }while (!c.isLast());

        return feedItems;

    }
}

