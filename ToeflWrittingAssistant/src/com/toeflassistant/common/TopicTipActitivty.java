package com.toeflassistant.common;

import com.toeflassistant.database.mDatabase;
import com.toeflassistant.writting.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TopicTipActitivty extends Activity {
	String questionId;
	String analysis;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_listview_item_tip_activity);
		// ���ǰ��ҳ�洫���Ĳ�����id
	    Bundle extras = getIntent().getExtras();
		questionId = extras.getString("question_id");
		//�����ݿ���analysis
        mDatabase mdb = new mDatabase();
        mdb.openDatabase();
        analysis = mdb.getAnalysis(questionId);
        mdb.closeDatabase();
        //����analysis
        TextView analysisContent = (TextView)this.findViewById(R.id.tip_analysis);
        analysisContent.setText(analysis.replaceAll("//", "\n\n"));
     
	}

}
