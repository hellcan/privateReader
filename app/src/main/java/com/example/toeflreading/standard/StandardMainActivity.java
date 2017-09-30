package com.example.toeflreading.standard;

import android.os.Bundle;

import com.example.toeflreading.myapplication.MainActivity;
//import com.example.toeflreading.notebook.QuickContactFragment;
import com.example.toeflreading.myapplication.R;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.toeflreading.database.mDatabase;

public class StandardMainActivity extends Activity {

    mDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_main);

        mdb = mDatabase.getInstance(getApplicationContext());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Score Table");


        mdb.openDatabase();

        TextView standard1 = (TextView) findViewById(R.id.standard_1);
        TextView standard2 = (TextView) findViewById(R.id.standard_2);
        mdb.closeDatabase();
        standard1.setText(getResources().getString(R.string.standard_1)
                .replaceAll("////", "\n\n"));

        standard2.setText(getResources().getString(R.string.standard_3)
                .replaceAll("////", "\n\n"));

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
