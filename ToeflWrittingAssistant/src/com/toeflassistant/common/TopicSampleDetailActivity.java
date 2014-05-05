package com.toeflassistant.common;

import net.youmi.android.spot.SpotManager;

import com.toeflassistant.mTextView.SelectableTextView;
import com.toeflassistant.writting.R;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class TopicSampleDetailActivity extends Activity {
	private String sampleContent;
	SelectableTextView mTextView;
	private int mTouchX;
	private int mTouchY;
	private final static int DEFAULT_SELECTION_LEN = 1;
    private String questionId;	
    private String mTextViewWide;
    static Point size;
    static float density;
    String text;
    int start;
    int end;
    

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_sample_samplecontent_activity);
        //插播广告
		SpotManager.getInstance(this).setSpotTimeout(5000);
		SpotManager.getInstance(this).showSpotAds(this);
		// make sure the TextView's BufferType is Spannable, see the main.xml
		mTextView = (SelectableTextView) findViewById(R.id.sample_content);
		mTextView.setDefaultSelectionColor(0x40FE9494);
		
		// get sample content
		Bundle extras = getIntent().getExtras();
		sampleContent = extras.getString("sample_content");
		//get questionID
		questionId = extras.getString("questionId");
		// set sample content
		if(sampleContent.contains("////")){
			mTextView.setText(sampleContent.replaceAll("/////", "\n\n"));
		}else{
			mTextView.setText(sampleContent);
		}
		mTextView.setTextColor(android.graphics.Color.BLACK);
		//传入questionID(topicId)
		mTextView.setQuestionId(questionId);
		
	
        //获得textview宽度
		ViewTreeObserver vto = mTextView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){
			@Override   
	        public void onGlobalLayout() {  
				mTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);   
				mTextViewWide = mTextView.getWidth()+"";
				mTextView.setTextViewWidth(mTextViewWide);
			}
		});
		

		mTextView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				mTextView.hideCursor();
				showSelectionCursors(mTouchX, mTouchY);
				return true;
			}
		});

		mTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTextView.hideCursor();		

			}
		});

		mTextView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mTouchX = (int) event.getX();
				mTouchY = (int) event.getY();
				return false;
			}
		});
		
	}

	
	private void showSelectionCursors(int x, int y) {
		 start = mTextView.getPreciseOffset(x, y);
		if (start > -1) {
			 end = start + DEFAULT_SELECTION_LEN;
			if (end >= mTextView.getText().length()) {
				end = mTextView.getText().length() - 1;
			}
			mTextView.showSelectionControls(start, end);
			mTextView.hideCursor();
			text = mTextView.getSelection().getSelectedText().toString();
			autoSelectWord(text);
		}
	}
	
	//点击自动选择一个单词
	private void autoSelectWord(String s){
		while(!s.contains(" ")&&!s.contains(",")&&!s.contains("!")&&!s.contains(".")
        		&&!s.contains("?")&&!text.contains(":")&&!s.contains("\"")&&!s.contains("\'")
        		&&!s.contains(";")){	
        	end = end+1;
			mTextView.showSelectionControls(start, end);
			mTextView.hideCursor();
			s = mTextView.getSelection().getSelectedText().toString();
        };
        end--;
        mTextView.showSelectionControls(start, end);
		mTextView.hideCursor();
		s = mTextView.getSelection().getSelectedText().toString();
		while(!s.contains(" ")&&!s.contains(",")&&!s.contains("!")&&!s.contains(".")
				&&!s.contains("?")&&!s.contains(":")&&!text.contains("\"")&&!s.contains("\'")
				&&!s.contains(";")){
			start--;
			mTextView.showSelectionControls(start, end);
			mTextView.hideCursor();
			s = mTextView.getSelection().getSelectedText().toString();
		}
        start++;
		mTextView.showSelectionControls(start, end);
		
	}

	// 获得PopupWindow
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		//防止popupwindow和dialog导致的leak
		@Override 
        public void onPause(){
			super.onPause();
			if(mTextView.mPop()!=null){
				mTextView.mPop().dismiss();
				mTextView.hideCursor();
				}
			}     
		
}
