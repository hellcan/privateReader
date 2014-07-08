package com.toeflassistant.common;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.offers.OffersAdSize;
import net.youmi.android.offers.OffersBanner;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.toeflassistant.database.mDatabase;
import com.toeflassistant.entry.MainActivity;
import com.toeflassistant.mTextView.SelectableTextView;
import com.toeflassistant.tag.TagTopicListActivity;
import com.toeflassistant.writting.R;

public class TopicSampleListActivity extends Activity implements
		LoaderCallbacks<Cursor> {

	private static final int LOADER = 0x01;
	ListView mListView;
	Cursor mCursor;
	CursorLoader mCursorLoader;
	SampleListAdapter mAdapter;
	Bundle bundleForCursorLoader;
	String questionId;
	List<String> tagNameList = new ArrayList<String>();
	String mContent;
	SelectableTextView mTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_sample_listview_activity);
		initActionBar();
		// �������׻��ֹ����
		// if (!((MyApplication) this.getApplication()).isAdRemoved()) {
		// ���ø���Ч���Ļ��ֹ����
		// FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
		// FrameLayout.LayoutParams.MATCH_PARENT,
		// FrameLayout.LayoutParams.WRAP_CONTENT);
		// ���û���Banner������λ��
		// layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // ����ʾ��Ϊ���½�
		// ʵ��������Banner
		// OffersBanner banner = new OffersBanner(this,
		// OffersAdSize.SIZE_MATCH_SCREENx60);//
		// ����߶�Ϊ60dp��OffersAdSize���������Banner
		// ����Activity��addContentView����
		// this.addContentView(banner, layoutParams);
		// ���ǰ��ҳ�洫���Ĳ�����id
		Bundle extras = getIntent().getExtras();
		questionId = extras.getString("question_id");
		// �����ݴ���CursorLoader
		bundleForCursorLoader = new Bundle();
		bundleForCursorLoader.putString("questionId", questionId);
		getLoaderManager().initLoader(0, bundleForCursorLoader, this);
		// ����header(��ɫ����) :topic �� question
		mListView = (ListView) findViewById(R.id.sample_list);
		mAdapter = new SampleListAdapter(this, null,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		mDatabase mdb = new mDatabase();
		mdb.openDatabase();
		String oneTopic = mdb.getOneTopic(questionId);
		String oneQuestion = mdb.getOneQuestion(questionId);
		tagNameList = mdb.getTag(Integer.valueOf(questionId));
		mdb.closeDatabase();

		mListView.setAdapter(mAdapter);

		getLoaderManager().restartLoader(LOADER, bundleForCursorLoader, this);
		mListView.setOnItemClickListener(new OnItemClickListenerImpl());
	}

	private void initActionBar() {
		// TODO Auto-generated method stub
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Sample List");
	}

	protected void onResume() {
		super.onResume();

	}

	// -----------------------------����CursorLoader----------------------------------------

	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String questionId = arg1.getString("questionId");
		mCursorLoader = new CursorLoader(
				this,
				Uri.parse("content://com.toeflassistant.contentprovider.SampleListProvider/sample"),
				null, questionId, null, null);
		return mCursorLoader;

	}

	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);

	}

	// ---------------------����CursorAdapter---------------------------------------
	public class SampleListAdapter extends CursorAdapter {

		public SampleListAdapter(Context context, Cursor c, int flag) {
			super(context, c, flag);

		}

		@Override
		public View newView(Context mContext, Cursor mCursor,
				ViewGroup mViewGroup) {

			LayoutInflater inflater = LayoutInflater.from(mContext);
			View view = inflater.inflate(
					R.layout.topic_sample_listview_item_activity, mViewGroup,
					false);

			TextView sampleId = (TextView) view.findViewById(R.id.sample_id);
			TextView sampleDescription = (TextView) view
					.findViewById(R.id.sample_description);

			view.setTag(R.id.sample_id, sampleId);
			view.setTag(R.id.sample_description, sampleDescription);

			return view;
		}

		@Override
		public void bindView(View view, Context arg1, Cursor cursor) {

			((TextView) view.getTag(R.id.sample_id)).setText("sample #"
					+ (1 + cursor.getPosition()) + " ");
			((TextView) view.getTag(R.id.sample_description)).setText(cursor
					.getString(1).replaceAll("/////", "\n\n").trim());
		}

	}

	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
			final TextView sample = (TextView) v
					.findViewById(R.id.sample_description);
			String sampleContent = sample.getText().toString();
			// Intent intent = new Intent(TopicSampleListActivity.this,
			// SampleDetailActivitySimplified.class);
			Intent intent = new Intent(TopicSampleListActivity.this,
					TopicSampleDetailActivity.class);
			intent.putExtra("sample_content", sampleContent);
			intent.putExtra("questionId", questionId);
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
				// ��������кܶ�ԭʼ��Activity,����Ӧ�ñ����������
						.addNextIntent(upIntent).startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			break;

		}

		return super.onOptionsItemSelected(item);
	}

}
