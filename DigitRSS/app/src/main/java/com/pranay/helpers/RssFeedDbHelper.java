package com.pranay.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by pranay on 12/11/16.
 */
public class RssFeedDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RssFeed.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = " , ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " ( " + FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_IMAGE_LINK + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_POST_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_NAVIGATION_LINK + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_PUB_DATE + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public RssFeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }

    public class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_POST_AUTHOR = "postAuthor";
        public static final String COLUMN_NAME_PUB_DATE = "pubDate";
        public static final String COLUMN_NAME_IMAGE_LINK = "imageLink";
        public static final String COLUMN_NAME_NAVIGATION_LINK = "navigationLink";
    }
}


