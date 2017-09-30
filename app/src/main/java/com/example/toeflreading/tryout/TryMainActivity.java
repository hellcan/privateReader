package com.example.toeflreading.tryout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.toeflreading.database.mDatabase;
import com.example.toeflreading.myapplication.MainActivity;
import com.example.toeflreading.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Random;

public class TryMainActivity extends Activity {
    mDatabase mdb;
    int randId;
    String topic;
    String question;
    TextView topicTv;
    TextView questionTv;
    TextView countTv;
    TextView idTv;
    Button controlBtn;
    TextView midTv;
    Boolean isCancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Let's Try");

        findView();


        //Set up timer
        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set up time  +100 to let onTick will be called after 0s
                long time = 1800000 + 100;

                CountDownTimer timer = counterDown(time, 1000);

                if (controlBtn.getText().toString().matches("Start")) {
                    timer.start();

                    randId = getRandId();
                    mdb = mDatabase.getInstance(getApplicationContext());
                    mdb.openDatabase();
                    topic = mdb.getOneTopic(Integer.toString(randId));
                    question = mdb.getOneQuestion(Integer.toString(randId));
                    mdb.closeDatabase();

                    questionTv.setVisibility(View.VISIBLE);
                    midTv.setVisibility(View.VISIBLE);

                    idTv.setText(Integer.toString(randId).trim() + ". ");
                    topicTv.setText(topic.trim());
                    questionTv.setText(question.trim());

                    controlBtn.setText("Reset");
                    //enable cancel() in onTick()
                    isCancel = false;

                } else if (controlBtn.getText().toString().matches("Reset")) {
                    isCancel = true;

                    questionTv.setVisibility(View.INVISIBLE);
                    midTv.setVisibility(View.GONE);

                    idTv.setText("Requirement");
                    questionTv.setText("");
                    topicTv.setText(getString(R.string.try_desc));

                    controlBtn.setText("Start");

                }
            }
        });


    }

    private int getRandId() {
        Random random = new Random();
        return random.nextInt(185) + 1;
    }

    private CountDownTimer counterDown(long a, long interval) {

        CountDownTimer timer = new CountDownTimer(a, interval) {

            public void onTick(long millisUntilFinished) {

                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                String hms = format.format(millisUntilFinished);
                countTv.setText("00:" + hms);

                if (isCancel) {
                    countTv.setText("00:00:00");
                    cancel();
                }


            }

            public void onFinish() {

                countTv.setText("Time Over!");

                controlBtn.setText("Start");

            }

        };
        return timer;
    }

    private void findView() {
        topicTv = findViewById(R.id.topic_content);
        questionTv = findViewById(R.id.question_content);
        countTv = findViewById(R.id.timer);
        idTv = findViewById(R.id.id);
        midTv = findViewById(R.id.item_mid_line);
        controlBtn = findViewById(R.id.controlBtn);
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
