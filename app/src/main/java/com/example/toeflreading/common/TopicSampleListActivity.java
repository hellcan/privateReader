package com.example.toeflreading.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.CursorAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.LoaderManager.LoaderCallbacks;


import com.example.toeflreading.database.mDatabase;
import com.example.toeflreading.myapplication.MainActivity;
import com.example.toeflreading.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TopicSampleListActivity extends Activity implements LoaderCallbacks<Cursor> {

    private static final int LOADER = 0x01;
    ListView mListView;
    CursorLoader mCursorLoader;
    SampleListAdapter mAdapter;
    Bundle bundleForCursorLoader;
    String questionId;
    List<String> tagNameList = new ArrayList<String>();
//  SelectableTextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_sample_list);

        initActionBar();

        // 获得前面页面传来的参数：id
        Bundle extras = getIntent().getExtras();
        questionId = extras.getString("question_id");

        // 将数据传入CursorLoader
        bundleForCursorLoader = new Bundle();
        bundleForCursorLoader.putString("questionId", questionId);
        getLoaderManager().initLoader(LOADER, bundleForCursorLoader, this);

        // 设置header(灰色部分) :topic 和 question
        mListView = (ListView) findViewById(R.id.sample_list);
        mAdapter = new SampleListAdapter(this, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        mDatabase mdb = mDatabase.getInstance(getApplicationContext());
        mdb.openDatabase();
        String oneTopic = mdb.getOneTopic(questionId);
        String oneQuestion = mdb.getOneQuestion(questionId);
        tagNameList = mdb.getTag(Integer.valueOf(questionId));
        mdb.closeDatabase();

        mListView.setAdapter(mAdapter);

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

    // -----------------------------设置CursorLoader----------------------------------------

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        String questionId = arg1.getString("questionId");
        mCursorLoader = new CursorLoader(
                this,
                Uri.parse("content://com.example.toeflreading.provider.SampleListProvider/sample"),
                null, questionId, null, null);
        return mCursorLoader;

    }

    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);

    }

    // ---------------------设置CursorAdapter---------------------------------------
    public class SampleListAdapter extends CursorAdapter {

        public SampleListAdapter(Context context, Cursor c, int flag) {
            super(context, c, flag);

        }

        final class ViewHolder {
            TextView sampleId;
            TextView sampleDescription;
        }

        @Override
        public View newView(Context mContext, Cursor mCursor,
                            ViewGroup mViewGroup) {

            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.activity_topic_sample_detail, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.sampleId = (TextView) view.findViewById(R.id.sample_id);
            viewHolder.sampleDescription = (TextView) view.findViewById(R.id.sample_description);

            view.setTag(viewHolder);

            return view;
        }

        @Override
        public void bindView(View view, Context arg1, Cursor cursor) {

            ViewHolder viewHolder = (ViewHolder) view.getTag();

            viewHolder.sampleId.setText((1 + cursor.getPosition()) + ". ");
            viewHolder.sampleDescription.setText(cursor.getString(1).replaceAll("/////", "\n\n").trim());
        }

    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
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
                            // 如果这里有很多原始的Activity,它们应该被添加在这里
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
