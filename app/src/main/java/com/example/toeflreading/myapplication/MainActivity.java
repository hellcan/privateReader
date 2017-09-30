package com.example.toeflreading.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.toeflreading.questionpool.QuestionTopicListActivity;
import com.example.toeflreading.tryout.AboutActivity;
import com.example.toeflreading.tryout.TryMainActivity;
import com.example.toeflreading.standard.StandardMainActivity;
import com.example.toeflreading.tag.CategoryActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().hide();


        //set up question pool 设置题库
        final TextView questionPool = (TextView)findViewById(R.id.question_pool_t);
        questionPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuestionTopicListActivity.class);
                startActivity(intent);
            }
        });

        ImageView questionPoolI = (ImageView)findViewById(R.id.question_pool_i);
        questionPoolI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuestionTopicListActivity.class);
                startActivity(intent);
            }
        });

        //set up category 设置分类
        TextView category = (TextView)findViewById(R.id.category_t);
        category.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });


        ImageView categoryI = (ImageView)findViewById(R.id.category_i);
        categoryI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });

        //set up notebook 设置笔记本
        TextView notebook = (TextView)findViewById(R.id.notebook_t);
        notebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TryMainActivity.class);
                startActivity(intent);
            }
        });

        ImageView notebookI = (ImageView)findViewById(R.id.notebook_i);
        notebookI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TryMainActivity.class);
                startActivity(intent);
            }
        });

        //set up standard 设置评分标准
        TextView standard = (TextView)findViewById(R.id.standard_t);
        standard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StandardMainActivity.class);
                startActivity(intent);
            }
        });

        ImageView standardI = (ImageView)findViewById(R.id.standard_i);
        standardI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StandardMainActivity.class);
                startActivity(intent);
            }
        });

        //set up about
        TextView about = (TextView)findViewById(R.id.about_t);
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        ImageView aboutI = (ImageView)findViewById(R.id.about_i);
        aboutI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });



    }

}
