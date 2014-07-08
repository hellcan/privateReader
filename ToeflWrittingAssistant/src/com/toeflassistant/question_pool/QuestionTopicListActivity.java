package com.toeflassistant.question_pool;

import com.toeflassistant.common.TopicDetailActivity;
import com.toeflassistant.database.mDatabase;
import com.toeflassistant.entry.MainActivity;
import com.toeflassistant.notebook.QuickContactFragment;
import com.toeflassistant.tag.TagMainActivity;
import com.toeflassistant.writting.R;

import android.app.LoaderManager.LoaderCallbacks;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionTopicListActivity extends Activity implements
		LoaderCallbacks<Cursor> {

	private static final int LOADER = 0x01;
	ListView datalist;
	Cursor mCursor;
	CursorLoader mCursorLoader;
	TopicListAdapter mAdapter;
	mDatabase mdb = new mDatabase();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_listview_activity);

		initActionbar();
		// --------------设置list内容------------
		datalist = (ListView) findViewById(R.id.topic_list);
		// 增加一个表头,并设置表头无法点击
		// datalist.addHeaderView(LayoutInflater.from(this).inflate(R.layout.topic_listview_header_activity,
		// null),null,false);

		mAdapter = new TopicListAdapter(this, null,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		datalist.setAdapter(mAdapter);

		getLoaderManager().restartLoader(LOADER, null, this);
		datalist.setOnItemClickListener(new OnItemClickListenerImpl());

		// //分类列表button
		// Button categoryButton = (Button)findViewById(R.id.category_button);
		// categoryButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new
		// Intent(QuestionTopicListActivity.this,TagMainActivity.class);
		// startActivity(intent);
		// }
		// });

	}

	// -----------------------------设置CursorLoader----------------------------------------

	private void initActionbar() {
		// TODO Auto-generated method stub
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Topic List");

	}

	protected void onRestart() {
		super.onRestart();
		getLoaderManager().restartLoader(0, null, this);

	}

	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		mCursorLoader = new CursorLoader(
				this,
				Uri.parse("content://com.toeflassistant.contentprovider.TopicListProvider/topic"),
				null, null, null, null);
		return mCursorLoader;

	}

	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);

	}

	// ---------------------设置CursorAdapter---------------------------------------
	public class TopicListAdapter extends CursorAdapter {

		public TopicListAdapter(Context context, Cursor c, int flag) {
			super(context, c, flag);

		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
			LayoutInflater inflater = LayoutInflater.from(getBaseContext());
			View view = inflater.inflate(R.layout.topic_listview_item_activity,
					arg2, false);
			TextView topicId = (TextView) view.findViewById(R.id.order_number);
			TextView topicDescription = (TextView) view
					.findViewById(R.id.topic_desciption);
			TextView sampleTotal = (TextView) view
					.findViewById(R.id.sample_total);

			view.setTag(R.id.order_number, topicId);
			view.setTag(R.id.topic_desciption, topicDescription);
			view.setTag(R.id.sample_total, sampleTotal);

			return view;
		}

		@Override
		public void bindView(View view, Context arg1, Cursor cursor) {

			mdb.openDatabase();
			((TextView) view.getTag(R.id.order_number)).setText(cursor
					.getString(0));
			((TextView) view.getTag(R.id.topic_desciption)).setText(cursor
					.getString(1).trim());
			((TextView) view.getTag(R.id.sample_total)).setText("Sample: "
					+ mdb.sampleTotal((cursor.getPosition() + 1)));
			mdb.closeDatabase();

		}

	}

	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
			final TextView tagIdText = (TextView) v
					.findViewById(R.id.order_number);
			final TextView sample = (TextView) v
					.findViewById(R.id.sample_total);
			String questionId = tagIdText.getText().toString();
			String sampleTotal = sample.getText().toString();
			Intent intent = new Intent(QuestionTopicListActivity.this,
					TopicDetailActivity.class);
			intent.putExtra("question_id", questionId);
			intent.putExtra("sampleTotal", sampleTotal);
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = new Intent(this, MainActivity.class);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {                   
                TaskStackBuilder.from(this)
                        //如果这里有很多原始的Activity,它们应该被添加在这里
                        .addNextIntent(upIntent)
                        .startActivities();
                finish();
            } else {                   
                NavUtils.navigateUpTo(this, upIntent);
            }
            break;
			
		}
		
			
		return super.onOptionsItemSelected(item);
	}

}
