package com.toeflassistant.common;

import java.util.ArrayList;
import java.util.List;

import com.haarman.supertooltips.ToolTip;
import com.haarman.supertooltips.ToolTipRelativeLayout;
import com.haarman.supertooltips.ToolTipView;
import com.toeflassistant.database.mDatabase;
import com.toeflassistant.entry.MainActivity;
import com.toeflassistant.tag.TagTopicListActivity;
import com.toeflassistant.writting.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopicDetailActivity extends Activity implements
		ToolTipView.OnToolTipViewClickedListener {
	String questionId;
	String topicContent;
	String questionContent;
	List<String> tagNameList = new ArrayList<String>();
	int tagLayoutWide;
	String sampleTotal;
	private ToolTipRelativeLayout mToolTipFrameLayout1;
	private ToolTipRelativeLayout mToolTipFrameLayout2;

	private ToolTipView mGreenToolTipView = null;
	private ToolTipView mOrangeToolTipView = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_listview_item_detail_activity);
		initActionBar();
		// ��ô����questionId
		Bundle extras = getIntent().getExtras();
		questionId = extras.getString("question_id");
		sampleTotal = extras.getString("sampleTotal");
		mDatabase mdb = new mDatabase();
		mdb.openDatabase();
		topicContent = mdb.getOneTopic(questionId);
		questionContent = mdb.getOneQuestion(questionId);
		mdb.closeDatabase();
		// ���topic��question������
		TextView topic = (TextView) this
				.findViewById(R.id.item_detail_topic_description);
		TextView question = (TextView) this
				.findViewById(R.id.item_detail_question_description);
		TextView tip = (TextView) this.findViewById(R.id.item_detail_tip);
		TextView sample = (TextView) this
				.findViewById(R.id.item_detail_sample_total);

		// ����topic��question������
		topic.setText(topicContent.trim());
		question.setText(questionContent.trim());
		sample.setText(sampleTotal);

		// ����tag
		final TextView tag1 = (TextView) this.findViewById(R.id.tag1);
		final TextView tag2 = (TextView) this.findViewById(R.id.tag2);
		final TextView tag3 = (TextView) this.findViewById(R.id.tag3);
		final TextView tag4 = (TextView) this.findViewById(R.id.tag4);
		final TextView tag5 = (TextView) this.findViewById(R.id.tag5);
		final TextView tag6 = (TextView) this.findViewById(R.id.tag6);
		final TextView tag7 = (TextView) this.findViewById(R.id.tag7);
		final TextView tag8 = (TextView) this.findViewById(R.id.tag8);
		final TextView tag9 = (TextView) this.findViewById(R.id.tag9);

		// ��tag�趨Ϊ���ɼ�
		tag1.setVisibility(TextView.INVISIBLE);
		tag2.setVisibility(TextView.INVISIBLE);
		tag3.setVisibility(TextView.INVISIBLE);
		tag4.setVisibility(TextView.INVISIBLE);
		tag5.setVisibility(TextView.INVISIBLE);
		tag6.setVisibility(TextView.INVISIBLE);
		tag7.setVisibility(TextView.INVISIBLE);
		tag8.setVisibility(TextView.INVISIBLE);
		tag9.setVisibility(TextView.INVISIBLE);
		// ���Ԥ���趨�õ�RelativeLayout���Ա���������ɾ���ò�����tag
		RelativeLayout rl = (RelativeLayout) this
				.findViewById(R.id.item_detail_tag_area);
		// ���layout����
		tagLayoutWide = rl.getLayoutParams().width;

		// �����ݿ⣬����id���tag
		mdb.openDatabase();
		tagNameList = mdb.getTag(Integer.valueOf(questionId));
		// ֻ��һ��tag�����
		if (tagNameList.size() == 1) {
			// ���û�õ�tag��Ϣ ����tag�趨Ϊ�ɼ�
			tag1.setText(tagNameList.get(0));
			tag1.setVisibility(TextView.VISIBLE);
			// ɾ���ò�����tag
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
		// 2��tag�����
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
		// 3��tag�����
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
		// 4��tag�����
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
		// 5��tag�����
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
		// 6��tag�������Ŀ�����6��
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

		// ���ð�����ť
		mToolTipFrameLayout1 = (ToolTipRelativeLayout) findViewById(R.id.topic_tooltipframelayout);
		ImageButton btn1 = (ImageButton) findViewById(R.id.item_detail_button1);
		mToolTipFrameLayout2 = (ToolTipRelativeLayout) findViewById(R.id.tag_tooltipframelayout);
		ImageButton btn3 = (ImageButton) findViewById(R.id.item_detail_button3);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mGreenToolTipView == null) {
					addGreenToolTipView();
				} else {
					mGreenToolTipView.remove();
					mGreenToolTipView = null;
				}
			}
		});

		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOrangeToolTipView == null) {
					addOrangeToolTipView();
				} else {
					mOrangeToolTipView.remove();
					mOrangeToolTipView = null;
				}
			}
		});

		sample.setOnClickListener(new OnClickListenerImplSample());
		tip.setOnClickListener(new OnClickListenerImplTip());
	}

	private void initActionBar() {
		// TODO Auto-generated method stub
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Topic Detail");
	}

	private void addGreenToolTipView() {
		mGreenToolTipView = mToolTipFrameLayout1.showToolTipForView(
				new ToolTip().withText("this is button1!").withColor(
						getResources().getColor(R.color.green_4)),
				findViewById(R.id.item_detail_button1));
		mGreenToolTipView
				.setOnToolTipViewClickedListener(TopicDetailActivity.this);
	}

	private void addOrangeToolTipView() {
		mOrangeToolTipView = mToolTipFrameLayout2.showToolTipForView(
				new ToolTip().withText("this is button3!").withColor(
						getResources().getColor(R.color.yellow_4)),
				findViewById(R.id.item_detail_button3));
		mOrangeToolTipView
				.setOnToolTipViewClickedListener(TopicDetailActivity.this);
	}

	private class OnClickListenerImplSample implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TopicDetailActivity.this,
					TopicSampleListActivity.class);
			intent.putExtra("question_id", questionId);
			startActivity(intent);
		}
	}

	private class OnClickListenerImplTip implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TopicDetailActivity.this,
					TopicTipActitivty.class);
			intent.putExtra("question_id", questionId);
			intent.putExtra("sampleTotal", sampleTotal);
			startActivity(intent);
		}
	}

	private class OnClickListenerTag1 implements OnClickListener {

		@Override
		public void onClick(View v) {
			final TextView tagIdText = (TextView) v.findViewById(R.id.tag1);
			String tagName = tagIdText.getText().toString();
			String newTagName = "\"" + tagName + "\"";
			Intent intent = new Intent(TopicDetailActivity.this,
					TagTopicListActivity.class);
			intent.putExtra("tag_name", newTagName);
			startActivity(intent);

		}

	}

	private class OnClickListenerTag2 implements OnClickListener {

		@Override
		public void onClick(View v) {
			final TextView tagIdText = (TextView) v.findViewById(R.id.tag2);
			String tagName = tagIdText.getText().toString();
			String newTagName = "\"" + tagName + "\"";
			Intent intent = new Intent(TopicDetailActivity.this,
					TagTopicListActivity.class);
			intent.putExtra("tag_name", newTagName);
			startActivity(intent);

		}

	}

	private class OnClickListenerTag3 implements OnClickListener {

		@Override
		public void onClick(View v) {
			final TextView tagIdText = (TextView) v.findViewById(R.id.tag3);
			String tagName = tagIdText.getText().toString();
			String newTagName = "\"" + tagName + "\"";
			Intent intent = new Intent(TopicDetailActivity.this,
					TagTopicListActivity.class);
			intent.putExtra("tag_name", newTagName);
			startActivity(intent);

		}

	}

	private class OnClickListenerTag4 implements OnClickListener {

		@Override
		public void onClick(View v) {
			final TextView tagIdText = (TextView) v.findViewById(R.id.tag4);
			String tagName = tagIdText.getText().toString();
			String newTagName = "\"" + tagName + "\"";
			Intent intent = new Intent(TopicDetailActivity.this,
					TagTopicListActivity.class);
			intent.putExtra("tag_name", newTagName);
			startActivity(intent);
		}

	}

	private class OnClickListenerTag5 implements OnClickListener {

		@Override
		public void onClick(View v) {
			final TextView tagIdText = (TextView) v.findViewById(R.id.tag5);
			String tagName = tagIdText.getText().toString();
			String newTagName = "\"" + tagName + "\"";
			Intent intent = new Intent(TopicDetailActivity.this,
					TagTopicListActivity.class);
			intent.putExtra("tag_name", newTagName);
			startActivity(intent);
		}

	}

	private class OnClickListenerTag6 implements OnClickListener {

		@Override
		public void onClick(View v) {
			final TextView tagIdText = (TextView) v.findViewById(R.id.tag6);
			String tagName = tagIdText.getText().toString();
			String newTagName = "\"" + tagName + "\"";
			Intent intent = new Intent(TopicDetailActivity.this,
					TagTopicListActivity.class);
			intent.putExtra("tag_name", newTagName);
			startActivity(intent);

		}

	}

	@Override
	public void onToolTipViewClicked(ToolTipView toolTipView) {
		mGreenToolTipView = null;
		mOrangeToolTipView = null;

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = new Intent(this, MainActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.from(this)
				// ��������кܶ�ԭʼ��Activity,����Ӧ�ñ�����������
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