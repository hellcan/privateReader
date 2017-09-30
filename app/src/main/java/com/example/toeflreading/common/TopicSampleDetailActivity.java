package com.example.toeflreading.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.toeflreading.myapplication.MainActivity;
import com.example.toeflreading.myapplication.R;

public class TopicSampleDetailActivity extends Activity {
    private String sampleContent;
    private String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_sample_content);

        initActionBar();

        // get sample content
        Bundle extras = getIntent().getExtras();
        sampleContent = extras.getString("sample_content");
        // get questionID
        questionId = extras.getString("questionId");

        TextView sampleTv = findViewById(R.id.sample_content);

        if (sampleContent.contains("////")) {
            sampleTv.setText(sampleContent.replaceAll("/////", "\n\n"));
        } else {
            sampleTv.setText(sampleContent);
        }
    }

    private void initActionBar() {
        // TODO Auto-generated method stub
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Sample");
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
