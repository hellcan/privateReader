package com.toeflassistant.contentprovider;

import com.toeflassistant.database.mDatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class TopicListProvider extends ContentProvider {
//	final static String DB_NAME = "writepractice";
//	private static final String AUTH = "com.toeflassistant.contentprovider.TopicListProvider";
//	public static final Uri TOPICLIST_URI = Uri.parse("content://" + AUTH + "/" + DB_NAME);
//	final static int TOPICLISTS = 1;
//	private final static UriMatcher uriMatcher;
	
//	static{
//		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//		uriMatcher.addURI(AUTH, DB_NAME, TOPICLISTS);
//	}
	
	@Override
	public boolean onCreate() {

		return false;
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
	    mDatabase db = new mDatabase();
	    db.openDatabase();
		Cursor mCursor = db.getTopicList();	

		return mCursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
