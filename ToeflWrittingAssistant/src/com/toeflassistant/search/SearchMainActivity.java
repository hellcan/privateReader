package com.toeflassistant.search;

import java.util.ArrayList;
import java.util.List;

import com.toeflassistant.common.TopicDetailActivity;
import com.toeflassistant.common.TopicSampleDetailActivity;
import com.toeflassistant.database.mDatabase;
import com.toeflassistant.entry.MainActivity;
import com.toeflassistant.writting.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class SearchMainActivity extends Activity {
	ActionBar mActionBar;
	ViewPager mViewPager;
	MyPagerAdapter mTabPagerAdapter;
	ArrayList<View> mTabPagerList = new ArrayList<View>();
	ListView topicList, questionList, sampleList;
	SearchResultListAdapter topicAdapter, questionAdapter, sampleAdapter;
	List<String> topicDefaultDisplay, questionDefaultDisplay,
			sampleDefaultDisplay;
	String noResult = "No Result";
	SearchView searchView;
	ProgressDialog dialog;
	Handler mHandler = new Handler();

	mDatabase mdb = new mDatabase();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main_activity);
		InitActionBar();
		InitViewPager();
	}

	private void InitViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		LayoutInflater mInflater = getLayoutInflater();

		// 设置topic TAB包含的内容
		topicDefaultDisplay = new ArrayList<String>();
		topicDefaultDisplay.add(noResult);
		topicList = (ListView) (mInflater.inflate(
				R.layout.search_tab1_activity, null)
				.findViewById(R.id.tab1_list));
		topicAdapter = new SearchResultListAdapter(this, topicDefaultDisplay);
		topicList.setAdapter(topicAdapter);
		// 给控件设置tag，来更新viewpager里面的数据，暂时只有这种方法
		topicList.setTag("list1");
		mTabPagerList.add(topicList);
		// 设置list点击事件
		topicList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获得topic内容
				String topic = topicDefaultDisplay.get(position);
				if (topic.equals(noResult)) {

				} else {
					mdb.openDatabase();
					String questionId = mdb.getIdTopicInput(topic);
					String sampleTotal = "Sample: "
							+ mdb.sampleTotal(Integer.valueOf(questionId));
					mdb.closeDatabase();
					Intent intent = new Intent(SearchMainActivity.this,
							TopicDetailActivity.class);
					intent.putExtra("question_id", questionId);
					intent.putExtra("sampleTotal", sampleTotal);
					startActivity(intent);
				}
			}

		});

		// 设置question TAB包含的内容
		questionDefaultDisplay = new ArrayList<String>();
		questionDefaultDisplay.add(noResult);
		questionList = (ListView) (mInflater.inflate(
				R.layout.search_tab2_activity, null)
				.findViewById(R.id.tab2_list));
		questionAdapter = new SearchResultListAdapter(this,
				questionDefaultDisplay);
		questionList.setAdapter(questionAdapter);
		questionList.setTag("list2");
		mTabPagerList.add(questionList);
		// 设置list点击事件
		questionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获得topic内容
				String question = questionDefaultDisplay.get(position);
				if (question.equals(noResult)) {

				} else {
					mdb.openDatabase();
					String questionId = mdb.getIdQuestionInput(question);
					String sampleTotal = "Sample: "
							+ mdb.sampleTotal(Integer.valueOf(questionId));
					mdb.closeDatabase();
					Intent intent = new Intent(SearchMainActivity.this,
							TopicDetailActivity.class);
					intent.putExtra("question_id", questionId);
					intent.putExtra("sampleTotal", sampleTotal);
					startActivity(intent);
				}
			}

		});

		// 设置sample TAB包含的内容
		sampleDefaultDisplay = new ArrayList<String>();
		sampleDefaultDisplay.add(noResult);
		sampleList = (ListView) (mInflater.inflate(
				R.layout.search_tab3_activity, null)
				.findViewById(R.id.tab3_list));
		sampleAdapter = new SearchResultListAdapter(this, sampleDefaultDisplay);
		sampleList.setAdapter(sampleAdapter);
		sampleList.setTag("list3");
		mTabPagerList.add(sampleList);
		// 设置list点击事件
		sampleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获得topic内容
				String sample = sampleDefaultDisplay.get(position);
				if (sample.equals(noResult)) {

				} else {
					Intent intent = new Intent(SearchMainActivity.this,
							TopicSampleDetailActivity.class);
					intent.putExtra("sample_content", sample);
					startActivity(intent);
				}
			}

		});

		// init viewPager
		mViewPager.setAdapter(new MyPagerAdapter(mTabPagerList));
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mViewPager.setCurrentItem(0);

	}

	private void InitActionBar() {
		MyTabListener mtabListener = new MyTabListener();

		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar.newTab().setText("Topic")
				.setTabListener(mtabListener));
		mActionBar.addTab(mActionBar.newTab().setText("Question")
				.setTabListener(mtabListener));
		mActionBar.addTab(mActionBar.newTab().setText("Sample")
				.setTabListener(mtabListener));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("Search");
	}

	private void UpdateViewPager() {
		LayoutInflater mInflater = getLayoutInflater();

		topicList = (ListView) mInflater.inflate(R.layout.search_tab1_activity,
				null).findViewById(R.id.tab1_list);
		topicAdapter = new SearchResultListAdapter(this, topicDefaultDisplay);
		topicList.setAdapter(topicAdapter);

		topicList.setTag("list1");
		mTabPagerList.add(topicList);

		topicList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {

				String topic = topicDefaultDisplay.get(position);
				if (topic.equals(noResult)) {

				} else {
					mdb.openDatabase();
					String questionId = mdb.getIdTopicInput(topic);
					String sampleTotal = "Sample: "
							+ mdb.sampleTotal(Integer.valueOf(questionId));
					mdb.closeDatabase();

					Intent intent = new Intent(SearchMainActivity.this,
							TopicDetailActivity.class);
					intent.putExtra("question_id", questionId);
					intent.putExtra("sampleTotal", sampleTotal);
					startActivity(intent);
				}
			}
		});

		questionList = (ListView) (mInflater.inflate(
				R.layout.search_tab2_activity, null)
				.findViewById(R.id.tab2_list));
		questionAdapter = new SearchResultListAdapter(this,
				questionDefaultDisplay);
		questionList.setAdapter(questionAdapter);
		questionList.setTag("list2");
		mTabPagerList.add(questionList);

		questionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				String question = questionDefaultDisplay.get(position);
				if (question.equals(noResult)) {

				} else {
					mdb.openDatabase();
					String questionId = mdb.getIdQuestionInput(question);
					String sampleTotal = "Sample: "
							+ mdb.sampleTotal(Integer.valueOf(questionId));
					mdb.closeDatabase();
					Intent intent = new Intent(SearchMainActivity.this,
							TopicDetailActivity.class);
					intent.putExtra("question_id", questionId);
					intent.putExtra("sampleTotal", sampleTotal);
					startActivity(intent);
				}
			}
		});

		sampleList = (ListView) (mInflater.inflate(
				R.layout.search_tab3_activity, null)
				.findViewById(R.id.tab3_list));
		sampleAdapter = new SearchResultListAdapter(this, sampleDefaultDisplay);
		sampleList.setAdapter(sampleAdapter);
		sampleList.setTag("list3");
		mTabPagerList.add(sampleList);

		sampleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				String sample = sampleDefaultDisplay.get(position);
				if (sample.equals(noResult)) {

				} else {
					Intent intent = new Intent(SearchMainActivity.this,
							TopicSampleDetailActivity.class);
					intent.putExtra("sample_content", sample);
					startActivity(intent);
				}
			}
		});

		mViewPager.setAdapter(new MyPagerAdapter(mTabPagerList));
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mViewPager.setCurrentItem(0);
	}

	private class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			try {
				if (mListViews.get(position).getParent() == null)
					((ViewPager) container)
							.addView(mListViews.get(position), 0);
				else {
					((ViewGroup) mListViews.get(position).getParent())
							.removeView(mListViews.get(position));
					((ViewPager) container)
							.addView(mListViews.get(position), 0);
				}
			} catch (Exception e) {
				Log.d("parent=", "" + mListViews.get(position).getParent());
				e.printStackTrace();
			}
			return mListViews.get(position);
		}
	}

	private class MyOnPageChangeListener implements
			ViewPager.OnPageChangeListener {
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
			// Log.d("page", "pos="+position);

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}

	private class MyTabListener implements ActionBar.TabListener {

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {

			for (int i = 0; i < mTabPagerList.size(); i++) {
				if (i == tab.getPosition()) {
					mViewPager.setCurrentItem(i);
				}
			}

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

	}

	// 设置搜索条
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_searchview);

		// 打开，直接显示搜索条
		searchItem.expandActionView();
		searchView = (SearchView) searchItem.getActionView();
		// 设置搜索条提示文字
		searchView.setQueryHint("Please enter keyword");
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(final String newText) {
				final ListView lv1 = (ListView) mViewPager
						.findViewWithTag("lv1");
				final ListView lv2 = (ListView) mViewPager
						.findViewWithTag("lv2");
				final ListView lv3 = (ListView) mViewPager
						.findViewWithTag("lv3");

				// progress dialog + thread
				dialog = ProgressDialog.show(SearchMainActivity.this,
						null, "Searching...", true);

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						processChange(newText);

						mHandler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (lv1 != null) {
									topicAdapter.notifyDataSetChanged();
									UpdateViewPager();
								}
								if (lv2 != null) {
									questionAdapter.notifyDataSetChanged();
									UpdateViewPager();
								}
								if (lv3 != null) {
									sampleAdapter.notifyDataSetChanged();
									UpdateViewPager();
								}
								hideKeyBoard();
							}
						});
						dialog.dismiss();
					}
				}).start();

				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		searchItem.setOnActionExpandListener(new OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				// Toast.makeText(SearchMainActivity.this, "expand",
				// Toast.LENGTH_LONG).show();
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				// Toast.makeText(SearchMainActivity.this,"collapse",Toast.LENGTH_SHORT).show();
				return true;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	protected void processChange(String newText) {

		// 清空之前的内容
		topicDefaultDisplay.clear();
		questionDefaultDisplay.clear();
		sampleDefaultDisplay.clear();
		// 设定新的内容
		if (newText.equals("")) {
			topicDefaultDisplay.add(noResult);
			questionDefaultDisplay.add(noResult);
			sampleDefaultDisplay.add(noResult);
		} else {
			mdb.openDatabase();
			List<String> topic = mdb.searchTopic(newText);
			List<String> question = mdb.searchQuestion(newText);
			List<String> sample = mdb.searchSample(newText);
			mdb.closeDatabase();

			if (topic.size() < 1 || question.size() < 1 || sample.size() < 1) {
				topicDefaultDisplay.add(noResult);
				questionDefaultDisplay.add(noResult);
				sampleDefaultDisplay.add(noResult);
			} else {
				for (int i = 0; i < topic.size(); i++) {
					topicDefaultDisplay.add(i, topic.get(i));
					questionDefaultDisplay.add(i, question.get(i));
					sampleDefaultDisplay.add(i, sample.get(i));

					// runOnUiThread(new Runnable() {
					//
					// @Override
					// public void run() {
					// UpdateViewPager();
					// }
					// });
				}
			}
		}

		// 通知个list的adapter更新内容
		// if (lv1 != null) {
		// topicAdapter.notifyDataSetChanged();
		// }
		// if (lv2 != null) {
		// questionAdapter.notifyDataSetChanged();
		// }
		// if (lv3 != null) {
		// sampleAdapter.notifyDataSetChanged();
		// }
		// hideKeyBoard();
	}

	protected void hideKeyBoard() {
		// TODO Auto-generated method stub
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			View v = SearchMainActivity.this.getCurrentFocus();
			if (v == null) {
				return;
			}

			inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			searchView.clearFocus();
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (dialog != null)
			dialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dialog != null)
			dialog.dismiss();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
