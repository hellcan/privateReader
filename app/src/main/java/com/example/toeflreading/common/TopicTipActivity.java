package com.example.toeflreading.common;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.toeflreading.database.mDatabase;
import com.example.toeflreading.myapplication.R;

public class TopicTipActivity extends Activity {

    String questionId, sampleTotal;
    String analysis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_tip);

        initActionBar();
        // 获得前面页面传来的参数：id
        Bundle extras = getIntent().getExtras();
        questionId = extras.getString("question_id");
        sampleTotal = extras.getString("sampleTotal");
        //打开数据库获得analysis
        mDatabase mdb = mDatabase.getInstance(getApplicationContext());
        mdb.openDatabase();
        analysis = mdb.getAnalysis(questionId);
        mdb.closeDatabase();
        //设置analysis
        TextView analysisContent = (TextView)this.findViewById(R.id.tip_analysis);
        analysisContent.setText(analysis.replaceAll("//", "\n\n"));
    }

    private void initActionBar() {
        // TODO Auto-generated method stub
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Tip");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = new Intent(this, TopicDetailActivity.class);
                upIntent.putExtra("question_id", questionId);
                upIntent.putExtra("sampleTotal", sampleTotal);
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
