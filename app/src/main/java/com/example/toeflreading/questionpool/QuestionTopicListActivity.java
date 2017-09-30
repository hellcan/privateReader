package com.example.toeflreading.questionpool;

import android.app.Activity;
import android.os.Bundle;

import com.example.toeflreading.common.TopicDetailActivity;
import com.example.toeflreading.database.mDatabase;
import com.example.toeflreading.myapplication.MainActivity;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toeflreading.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionTopicListActivity extends Activity implements LoaderCallbacks<Cursor> {

    private static final int LOADER = 0x01;
    ListView datalist;
    CursorLoader mCursorLoader;
    TopicListAdapter mAdapter;
    mDatabase mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_topic_list);

        getLoaderManager().initLoader(LOADER, null, this);

        initActionbar();

        // --------------set up list------------
        datalist = (ListView) findViewById(R.id.topic_list);
        // 增加一个表头,并设置表头无法点击
//         datalist.addHeaderView(LayoutInflater.from(this).inflate(R.layout.topic_listview_header_activity,
//         null),null,false);


        mAdapter = new TopicListAdapter(this, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        datalist.setAdapter(mAdapter);

        datalist.setOnItemClickListener(new OnItemClickListenerImpl());

//        test();

    }

    private void initActionbar() {
        // TODO Auto-generated method stub
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Topic List");
    }

    // -----------------------------CursorLoader----------------------------------------

    protected void onRestart() {
        super.onRestart();
        getLoaderManager().restartLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri uri = Uri.parse("content://com.example.toeflreading.provider.TopicListProvider/question");
        mCursorLoader = new CursorLoader(QuestionTopicListActivity.this, uri, null, null,
                null, null);
        return mCursorLoader;

    }

    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {

        mAdapter.swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);

    }

    // ---------------------------------CursorAdapter-------------------------------------------
    public class TopicListAdapter extends CursorAdapter {

        public TopicListAdapter(Context context, Cursor cursor, int flag) {
            super(context, cursor, flag);

        }

        final class ViewHolder {
            TextView topicId;
            TextView topicDescription;
            TextView sampleTotal;
        }


        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            final LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.topic_listview_item_activity, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.topicId = (TextView) view.findViewById(R.id.order_number);
            viewHolder.topicDescription = (TextView) view.findViewById(R.id.topic_desciption);
            viewHolder.sampleTotal = (TextView) view.findViewById(R.id.sample_total);

            view.setTag(viewHolder);

            return view;
        }

        @Override
        public void bindView(View view, Context arg1, Cursor cursor) {

            ViewHolder viewHolder = (ViewHolder) view.getTag();

            mdb = mDatabase.getInstance(getApplicationContext());

            mdb.openDatabase();

            viewHolder.topicId.setText(cursor.getString(0));
            viewHolder.topicDescription.setText(cursor.getString(1).trim());
            viewHolder.sampleTotal.setText("Sample: " + mdb.sampleTotal((cursor.getPosition() + 1)));

            mdb.closeDatabase();

        }

    }

    private void test() {
        List<String> content = new ArrayList<String>();
        content.add("1");
        content.add("2");
        content.add("3");
        content.add("4");

        datalist.setAdapter(new ArrayAdapter<String>(this, R.layout.topic_listview_item_activity, R.id.order_number, content));
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
