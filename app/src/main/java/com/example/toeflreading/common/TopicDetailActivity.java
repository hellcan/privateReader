package com.example.toeflreading.common;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toeflreading.database.mDatabase;
import com.example.toeflreading.myapplication.MainActivity;
import com.example.toeflreading.myapplication.R;
import com.example.toeflreading.tag.CategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class TopicDetailActivity extends Activity {

    String questionId;
    String topicContent;
    String questionContent;
    List<String> tagNameList = new ArrayList<String>();
    int tagLayoutWide;
    String sampleTotal;
//    private ToolTipRelativeLayout mToolTipFrameLayout1;
//    private ToolTipRelativeLayout mToolTipFrameLayout2;
//
//    private ToolTipView mGreenToolTipView = null;
//    private ToolTipView mOrangeToolTipView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        initActionBar();
        // 获得传入的questionId
        Bundle extras = getIntent().getExtras();
        questionId = extras.getString("question_id");
        sampleTotal = extras.getString("sampleTotal");
        mDatabase mdb = mDatabase.getInstance(getApplicationContext());
        mdb.openDatabase();
        topicContent = mdb.getOneTopic(questionId);
        questionContent = mdb.getOneQuestion(questionId);
        mdb.closeDatabase();
        // 获得topic和question的内容
        TextView topic = (TextView) this
                .findViewById(R.id.topic_content);
        TextView question = (TextView) this
                .findViewById(R.id.question_content);
        TextView tip = (TextView) this.findViewById(R.id.item_detail_tip);
        TextView sample = (TextView) this
                .findViewById(R.id.item_detail_sample_total);

        // 设置topic和question的内容
        topic.setText(topicContent.trim());
        question.setText(questionContent.trim());
        sample.setText(sampleTotal);

        // 添加tag
        final TextView tag1 = (TextView) findViewById(R.id.tag1);
        final TextView tag2 = (TextView) findViewById(R.id.tag2);
        final TextView tag3 = (TextView) findViewById(R.id.tag3);
        final TextView tag4 = (TextView) findViewById(R.id.tag4);
        final TextView tag5 = (TextView) findViewById(R.id.tag5);
        final TextView tag6 = (TextView) findViewById(R.id.tag6);
        final TextView tag7 = (TextView) findViewById(R.id.tag7);
        final TextView tag8 = (TextView) findViewById(R.id.tag8);
        final TextView tag9 = (TextView) findViewById(R.id.tag9);

        // 将tag设定为不可见
        tag1.setVisibility(TextView.INVISIBLE);
        tag2.setVisibility(TextView.INVISIBLE);
        tag3.setVisibility(TextView.INVISIBLE);
        tag4.setVisibility(TextView.INVISIBLE);
        tag5.setVisibility(TextView.INVISIBLE);
        tag6.setVisibility(TextView.INVISIBLE);
        tag7.setVisibility(TextView.INVISIBLE);
        tag8.setVisibility(TextView.INVISIBLE);
        tag9.setVisibility(TextView.INVISIBLE);
        // 获得预先设定好的RelativeLayout，以备下面用于删除用不到的tag
        RelativeLayout rl = (RelativeLayout) this.findViewById(R.id.item_detail_tag_area);
        // 获得layout宽度
        tagLayoutWide = rl.getLayoutParams().width;

        // 打开数据库，根据id获得tag
        mdb.openDatabase();
        tagNameList = mdb.getTag(Integer.valueOf(questionId));

        // 只有一个tag的情况
        if (tagNameList.size() == 1) {
            // 设置获得的tag信息 并将tag设定为可见
            tag1.setText(tagNameList.get(0));
            tag1.setVisibility(TextView.VISIBLE);
            // 删除用不到的tag
            rl.removeView(tag2);
            rl.removeView(tag3);
            rl.removeView(tag4);
            rl.removeView(tag5);
            rl.removeView(tag6);
            rl.removeView(tag7);
            rl.removeView(tag8);
            rl.removeView(tag9);

            tag1.setOnClickListener(new OnClickListenerTag1());
        }

            // 2个tag的情况
            if (tagNameList.size() == 2) {
                tag1.setText(tagNameList.get(0));
                tag1.setVisibility(TextView.VISIBLE);

                tag2.setText(tagNameList.get(1));
                tag2.setVisibility(TextView.VISIBLE);

                rl.removeView(tag3);
                rl.removeView(tag4);
                rl.removeView(tag5);
                rl.removeView(tag6);
                rl.removeView(tag7);
                rl.removeView(tag8);
                rl.removeView(tag9);

                tag1.setOnClickListener(new OnClickListenerTag1());
                tag2.setOnClickListener(new OnClickListenerTag2());

            }
            // 3个tag的情况
            if (tagNameList.size() == 3) {

                tag1.setText(tagNameList.get(0));
                tag1.setVisibility(TextView.VISIBLE);

                tag2.setText(tagNameList.get(1));
                tag2.setVisibility(TextView.VISIBLE);

                tag3.setText(tagNameList.get(2));
                tag3.setVisibility(TextView.VISIBLE);

                rl.removeView(tag4);
                rl.removeView(tag5);
                rl.removeView(tag6);
                rl.removeView(tag7);
                rl.removeView(tag8);
                rl.removeView(tag9);

                tag1.setOnClickListener(new OnClickListenerTag1());
                tag2.setOnClickListener(new OnClickListenerTag2());
                tag3.setOnClickListener(new OnClickListenerTag3());

            }
            // 4个tag的情况
            if (tagNameList.size() == 4) {

                tag1.setText(tagNameList.get(0));
                tag1.setVisibility(TextView.VISIBLE);

                tag2.setText(tagNameList.get(1));
                tag2.setVisibility(TextView.VISIBLE);

                tag3.setText(tagNameList.get(2));
                tag3.setVisibility(TextView.VISIBLE);

                tag4.setText(tagNameList.get(3));
                tag4.setVisibility(TextView.VISIBLE);

                rl.removeView(tag5);
                rl.removeView(tag6);
                rl.removeView(tag7);
                rl.removeView(tag8);
                rl.removeView(tag9);

                tag1.setOnClickListener(new OnClickListenerTag1());
                tag2.setOnClickListener(new OnClickListenerTag2());
                tag3.setOnClickListener(new OnClickListenerTag3());
                tag4.setOnClickListener(new OnClickListenerTag4());

            }
            // 5个tag的情况
            if (tagNameList.size() == 5) {

                tag1.setText(tagNameList.get(0));
                tag1.setVisibility(TextView.VISIBLE);

                tag2.setText(tagNameList.get(1));
                tag2.setVisibility(TextView.VISIBLE);

                tag3.setText(tagNameList.get(2));
                tag3.setVisibility(TextView.VISIBLE);

                tag4.setText(tagNameList.get(3));
                tag4.setVisibility(TextView.VISIBLE);

                tag5.setText(tagNameList.get(4));
                tag5.setVisibility(TextView.VISIBLE);

                rl.removeView(tag6);
                rl.removeView(tag7);
                rl.removeView(tag8);
                rl.removeView(tag9);

                tag1.setOnClickListener(new OnClickListenerTag1());
                tag2.setOnClickListener(new OnClickListenerTag2());
                tag3.setOnClickListener(new OnClickListenerTag3());
                tag4.setOnClickListener(new OnClickListenerTag4());
                tag5.setOnClickListener(new OnClickListenerTag5());

            }
            // 6个tag的情况，目测最多6个
            if (tagNameList.size() == 6) {

                tag1.setText(tagNameList.get(0));
                tag1.setVisibility(TextView.VISIBLE);

                tag2.setText(tagNameList.get(1));
                tag2.setVisibility(TextView.VISIBLE);

                tag3.setText(tagNameList.get(2));
                tag3.setVisibility(TextView.VISIBLE);

                tag4.setText(tagNameList.get(3));
                tag4.setVisibility(TextView.VISIBLE);

                tag5.setText(tagNameList.get(4));
                tag5.setVisibility(TextView.VISIBLE);

                tag6.setText(tagNameList.get(5));
                tag6.setVisibility(TextView.VISIBLE);

                rl.removeView(tag7);
                rl.removeView(tag8);
                rl.removeView(tag9);
                tag1.setOnClickListener(new OnClickListenerTag1());
                tag2.setOnClickListener(new OnClickListenerTag2());
                tag3.setOnClickListener(new OnClickListenerTag3());
                tag4.setOnClickListener(new OnClickListenerTag4());
                tag5.setOnClickListener(new OnClickListenerTag5());
                tag6.setOnClickListener(new OnClickListenerTag6());

            }

            // 设置帮助按钮
//            mToolTipFrameLayout1 = (ToolTipRelativeLayout) findViewById(R.id.topic_tooltipframelayout);
//            ImageButton btn1 = (ImageButton) findViewById(R.id.item_detail_button1);
//            mToolTipFrameLayout2 = (ToolTipRelativeLayout) findViewById(R.id.tag_tooltipframelayout);
//            ImageButton btn3 = (ImageButton) findViewById(R.id.item_detail_button3);
//
//            btn1.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if (mGreenToolTipView == null) {
//                        addGreenToolTipView();
//                    } else {
//                        mGreenToolTipView.remove();
//                        mGreenToolTipView = null;
//                    }
//                }
//            });
//
//            btn3.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if (mOrangeToolTipView == null) {
//                        addOrangeToolTipView();
//                    } else {
//                        mOrangeToolTipView.remove();
//                        mOrangeToolTipView = null;
//                    }
//                }
//            });
//
            sample.setOnClickListener(new OnClickListenerImplSample());
            tip.setOnClickListener(new OnClickListenerImplTip());


    }

    private void initActionBar() {
        // TODO Auto-generated method stub
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Topic Detail");
    }

    private class OnClickListenerImplSample implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TopicDetailActivity.this,
                    TopicSampleListActivity.class);
            intent.putExtra("question_id", questionId);
            startActivity(intent);
        }
    }

    private class OnClickListenerImplTip implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TopicDetailActivity.this,
                    TopicTipActivity.class);
            intent.putExtra("question_id", questionId);
            intent.putExtra("sampleTotal", sampleTotal);
            startActivity(intent);
        }
    }

    private class OnClickListenerTag1 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final TextView tagIdText = (TextView) v.findViewById(R.id.tag1);
            String tagName = tagIdText.getText().toString();
            String newTagName = "\"" + tagName + "\"";
            Intent intent = new Intent(TopicDetailActivity.this,
                    CategoryActivity.class);
            intent.putExtra("tag_name", tagName);
            startActivity(intent);

        }

    }

    private class OnClickListenerTag2 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final TextView tagIdText = (TextView) v.findViewById(R.id.tag2);
            String tagName = tagIdText.getText().toString();
            String newTagName = "\"" + tagName + "\"";
            Intent intent = new Intent(TopicDetailActivity.this,
                    CategoryActivity.class);
            intent.putExtra("tag_name", tagName);
            startActivity(intent);

        }

    }

    private class OnClickListenerTag3 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final TextView tagIdText = (TextView) v.findViewById(R.id.tag3);
            String tagName = tagIdText.getText().toString();
            String newTagName = "\"" + tagName + "\"";
            Intent intent = new Intent(TopicDetailActivity.this,
                    CategoryActivity.class);
            intent.putExtra("tag_name", tagName);
            startActivity(intent);

        }

    }

    private class OnClickListenerTag4 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final TextView tagIdText = (TextView) v.findViewById(R.id.tag4);
            String tagName = tagIdText.getText().toString();
//            String newTagName = "\"" + tagName + "\"";
            Intent intent = new Intent(TopicDetailActivity.this,
                    CategoryActivity.class);
            intent.putExtra("tag_name", tagName);
            startActivity(intent);
        }

    }

    private class OnClickListenerTag5 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final TextView tagIdText = (TextView) v.findViewById(R.id.tag5);
            String tagName = tagIdText.getText().toString();
//            String newTagName = "\"" + tagName + "\"";
            Intent intent = new Intent(TopicDetailActivity.this,
                    CategoryActivity.class);
            intent.putExtra("tag_name", tagName);
            startActivity(intent);
        }

    }

    private class OnClickListenerTag6 implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final TextView tagIdText = (TextView) v.findViewById(R.id.tag6);
            String tagName = tagIdText.getText().toString();
//            String newTagName = "\"" + tagName + "\"";
            Intent intent = new Intent(TopicDetailActivity.this,
                    CategoryActivity.class);
            intent.putExtra("tag_name", tagName);
            startActivity(intent);

        }

    }

//    @Override
//    public void onToolTipViewClicked(ToolTipView toolTipView) {
//        mGreenToolTipView = null;
//        mOrangeToolTipView = null;
//
//    }

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
