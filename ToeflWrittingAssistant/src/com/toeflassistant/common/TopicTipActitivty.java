package com.toeflassistant.common;

import com.toeflassistant.database.mDatabase;
import com.toeflassistant.entry.MainActivity;
import com.toeflassistant.writting.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TopicTipActitivty extends Activity {
	String questionId, sampleTotal;
	String analysis;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_listview_item_tip_activity);
		initActionBar();
		// 获得前面页面传来的参数：id
	    Bundle extras = getIntent().getExtras();
		questionId = extras.getString("question_id");
		sampleTotal = extras.getString("sampleTotal");
		//打开数据库获得analysis
        mDatabase mdb = new mDatabase();
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
