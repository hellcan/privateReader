package com.toeflassistant.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.toeflassistant.notebook.NotebookMainActivity;
import com.toeflassistant.question_pool.QuestionTopicListActivity;
import com.toeflassistant.search.SearchMainActivity;
import com.toeflassistant.standard.StandardMainActivity;
import com.toeflassistant.tag.TagMainActivity;
import com.toeflassistant.writting.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        getActionBar().hide();
        
        //��������
        ImageButton search = (ImageButton)findViewById(R.id.search_button);
        search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,SearchMainActivity.class);
				startActivity(intent);
			}
		});
        
        //�������
        TextView questionPool = (TextView)findViewById(R.id.question_pool_t);
        questionPool.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,QuestionTopicListActivity.class);
				startActivity(intent);
			}
		});
        
        ImageView questionPoolI = (ImageView)findViewById(R.id.question_pool_i);
        questionPoolI.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,QuestionTopicListActivity.class);
				startActivity(intent);
			}
		});
        //���÷���
        TextView category = (TextView)findViewById(R.id.category_t);
        category.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,TagMainActivity.class);
				startActivity(intent);
			}
		});
        
        ImageView categoryI = (ImageView)findViewById(R.id.category_i);
        categoryI.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,TagMainActivity.class);
				startActivity(intent);
			}
		});
        
        //���ñʼǱ�
        TextView notebook = (TextView)findViewById(R.id.notebook_t);
        notebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,NotebookMainActivity.class);
				startActivity(intent);
			}
		});
        
        ImageView notebookI = (ImageView)findViewById(R.id.notebook_i);
        notebookI.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,NotebookMainActivity.class);
				startActivity(intent);
			}
		});
        
        //�������ֱ�׼
        TextView standard = (TextView)findViewById(R.id.standard_t);
        standard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,StandardMainActivity.class);
				startActivity(intent);
			}
		});
        
        ImageView standardI = (ImageView)findViewById(R.id.standard_i);
        standardI.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,StandardMainActivity.class);
				startActivity(intent);
			}
		});
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    
    
}