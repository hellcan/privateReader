package com.example.toeflreading.provider;

import com.example.toeflreading.database.mDatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


public class TopicListProvider extends ContentProvider {

    //database
    mDatabase mdb;

    //uriMatcher
    private static final UriMatcher mUriMatcher = buildUriMatcher();

    //uri
    static final String CONTENT_AUTHORITY = "com.example.toeflreading.provider.TopicListProvider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    static final String TABLE_NAME = "question";

    //Set up ID_Mark
    private static final int MAIN = 1;

    //Add append uri with ID_Mark
    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //CONTENT_AUTHORITY， question = TABLE_NAME, MAIN = ID MARK
        matcher.addURI(CONTENT_AUTHORITY,TABLE_NAME, MAIN);

        return matcher;
    }


    @Override
    public boolean onCreate() {

        mdb = mDatabase.getInstance(getContext());

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        return 0;
    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        return null;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor mCursor = null;

        final int match = mUriMatcher.match(uri);

        switch(match){
            case MAIN:

                mdb.openDatabase();
                mCursor = mdb.getTopicList();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return mCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
