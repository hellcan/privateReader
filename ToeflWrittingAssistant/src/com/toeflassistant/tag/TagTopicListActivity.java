package com.toeflassistant.tag;


import com.toeflassistant.common.TopicDetailActivity;
import com.toeflassistant.common.TopicSampleListActivity;
import com.toeflassistant.database.mDatabase;
import com.toeflassistant.question_pool.QuestionTopicListActivity;
import com.toeflassistant.writting.R;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TagTopicListActivity extends Activity implements LoaderCallbacks<Cursor> {
	private static final int LOADER = 0x01;
	ListView mListView;
    Cursor mCursor;
    CursorLoader mCursorLoader;
    TagTopicListAdapter mAdapter;
    Bundle bundleForCursorLoader;
    String tagName;
    mDatabase db = new mDatabase();

	public void onCreate(Bundle savedInstanceState){
		db.openDatabase();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_listview_activity);
		//获得前面页面传来的参数：id
        Bundle extras = getIntent().getExtras();
        tagName = extras.getString("tag_name");
      //将数据传入CursorLoader
        bundleForCursorLoader = new Bundle();
		bundleForCursorLoader.putString("tag_Name", tagName);
		getLoaderManager().initLoader(0, bundleForCursorLoader, this);
	
	  //设置adapter
	    mListView = (ListView) findViewById (R.id.topic_list);
	    mAdapter = new TagTopicListAdapter(this, null,
	    		CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	    
	    mListView.setAdapter(mAdapter);
		getLoaderManager().restartLoader(LOADER, bundleForCursorLoader, this);
		mListView.setOnItemClickListener(new OnItemClickListenerImpl());
		
//		//分类列表button
//		
//		Button categoryButton = (Button)findViewById(R.id.category_button);
//		categoryButton.setOnClickListener(new OnClickListener() {
//					
//		@Override			
//		public void onClick(View v) {				
//			Intent intent = new Intent(TagTopicListActivity.this,TagMainActivity.class);
//			startActivity(intent);			
//			}
//    	});

	  }
	

	//-----------------------------设置CursorLoader----------------------------------------	
    protected void onRestart() {
	    super.onRestart();
	    getLoaderManager().restartLoader(0, bundleForCursorLoader, this);
	    db.openDatabase();
    }
 
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1){
		String tagName = arg1.getString("tag_Name");
		mCursorLoader = new CursorLoader(
				this, 
				Uri.parse("content://com.toeflassistant.contentprovider.TagTopicListProvider/tag"), 
				null, tagName, null, null);
		return mCursorLoader;
		
	}
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor){
		mCursor = cursor;
		mAdapter.swapCursor(mCursor);
		
	}
	
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);

	}
	
	//---------------------设置CursorAdapter---------------------------------------
	 public class TagTopicListAdapter extends CursorAdapter{
			mDatabase mdb;
		
			public TagTopicListAdapter(Context context, Cursor c, int flag) {
				super(context, c, flag);
				mdb = new mDatabase();
				mdb.openDatabase();
			}
			
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(getBaseContext());
		View view = inflater.inflate(
				R.layout.topic_listview_item_activity,arg2, false);		
		TextView tagTopicId = (TextView)view.findViewById(R.id.order_number);
		TextView tagTopicDescription = (TextView)view.findViewById(R.id.topic_desciption);
		TextView tagTotalSample = (TextView)view.findViewById(R.id.sample_total);
		
		view.setTag(R.id.order_number,tagTopicId);
		view.setTag(R.id.topic_desciption,tagTopicDescription);
		view.setTag(R.id.sample_total,tagTotalSample);
		
		return view;
	}
	
	public void bindView(View view, Context arg1, Cursor cursor) {
		((TextView) view
				.getTag(R.id.order_number))
				.setText(cursor.getString(0));
		((TextView) view
				.getTag(R.id.topic_desciption))
				.setText(cursor.getString(1).trim());
		
		((TextView) view
				.getTag(R.id.sample_total))
				.setText("Total Sample: "+ mdb.sampleTotal(Integer.valueOf(cursor.getString(0))));
		}
	}
	 
		private class OnItemClickListenerImpl implements OnItemClickListener{
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				final TextView topicIdText = (TextView)v.findViewById(R.id.order_number);
				final TextView sample = (TextView)v.findViewById(R.id.sample_total);
				String questionId = topicIdText.getText().toString();
				String sampleTotal = sample.getText().toString();
				Intent intent = new Intent(TagTopicListActivity.this,TopicDetailActivity.class);
				intent.putExtra("question_id", questionId);
				intent.putExtra("sampleTotal", sampleTotal);
				startActivity(intent);
			}
		}
	 
	 
}
