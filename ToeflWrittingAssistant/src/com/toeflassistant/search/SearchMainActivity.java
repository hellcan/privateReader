package com.toeflassistant.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import java.util.Map;

import com.toeflassistant.common.TopicDetailActivity;
import com.toeflassistant.common.TopicSampleDetailActivity;
import com.toeflassistant.database.mDatabase;
import com.toeflassistant.writting.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
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
	ListView topicList;
	ListView questionList;
	ListView sampleList;
    SearchResultListAdapter topicAdapter;
    SearchResultListAdapter questionAdapter;
    SearchResultListAdapter sampleAdapter;
    List<String> topicDefaultDisplay;
    List<String> questionDefaultDisplay;
    List<String> sampleDefaultDisplay;
    String noResult = "暂时没有搜索结果";

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

		//设置topic TAB包含的内容
	    topicDefaultDisplay = new ArrayList<String>();
		topicDefaultDisplay.add(noResult);
		topicList = (ListView)(mInflater.inflate(R.layout.search_tab1_activity, null).findViewById(R.id.tab1_list));		
		topicAdapter = new SearchResultListAdapter(this, topicDefaultDisplay);
		topicList.setAdapter(topicAdapter);
	     //给控件设置tag，来更新viewpager里面的数据，暂时只有这种方法
		topicList.setTag("list1");
		mTabPagerList.add(topicList);
		//设置list点击事件
		topicList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//获得topic内容
				String topic = topicDefaultDisplay.get(position);
				if(topic.equals(noResult)){
					
				}else{
					mdb.openDatabase();
					String questionId = mdb.getIdTopicInput(topic);
					String sampleTotal = "Sample: " + mdb.sampleTotal(Integer.valueOf(questionId));
					mdb.closeDatabase();
					Intent intent = new Intent(SearchMainActivity.this, TopicDetailActivity.class);
					intent.putExtra("question_id", questionId);
					intent.putExtra("sampleTotal", sampleTotal);
				    startActivity(intent);
				}
			}
			
		});
		
		//设置question TAB包含的内容
		questionDefaultDisplay = new ArrayList<String>();
		questionDefaultDisplay.add(noResult);
		questionList = (ListView)(mInflater.inflate(R.layout.search_tab2_activity, null).findViewById(R.id.tab2_list));		
		questionAdapter = new SearchResultListAdapter(this, questionDefaultDisplay);
		questionList.setAdapter(questionAdapter);
		questionList.setTag("list2");
		mTabPagerList.add(questionList);
        //设置list点击事件	
		questionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//获得topic内容
				String question = questionDefaultDisplay.get(position);
				if(question.equals(noResult)){
					
				}else{
					mdb.openDatabase();
					String questionId = mdb.getIdQuestionInput(question);
					String sampleTotal = "Sample: " + mdb.sampleTotal(Integer.valueOf(questionId));
					mdb.closeDatabase();
					Intent intent = new Intent(SearchMainActivity.this, TopicDetailActivity.class);
					intent.putExtra("question_id", questionId);
					intent.putExtra("sampleTotal", sampleTotal);
				    startActivity(intent);
				}
			}
			
		});
		
		//设置sample TAB包含的内容
		sampleDefaultDisplay = new ArrayList<String>();
		sampleDefaultDisplay.add(noResult);
		sampleList = (ListView)(mInflater.inflate(R.layout.search_tab3_activity, null).findViewById(R.id.tab3_list));		
		sampleAdapter = new SearchResultListAdapter(this, sampleDefaultDisplay);
		sampleList.setAdapter(sampleAdapter);
		sampleList.setTag("list3");
		mTabPagerList.add(sampleList);
        //设置list点击事件
		sampleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//获得topic内容
				String sample = sampleDefaultDisplay.get(position);
				if(sample.equals(noResult)){
					
				}else{					
					Intent intent = new Intent(SearchMainActivity.this, TopicSampleDetailActivity.class);
					intent.putExtra("sample_content", sample);
				    startActivity(intent);
				}
			}
			
		});

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
	}
	
	
	private class MyPagerAdapter extends PagerAdapter{
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
			try{
				if(mListViews.get(position).getParent()==null)
					((ViewPager) container).addView(mListViews.get(position), 0);
				else{
                    ((ViewGroup)mListViews.get(position).getParent()).removeView(mListViews.get(position));    
                    ((ViewPager) container).addView(mListViews.get(position), 0); 
				}
			}catch(Exception e){
				Log.d("parent=",""+mListViews.get(position).getParent());
				e.printStackTrace();
			}
			return mListViews.get(position);
		}
	}
	
	private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
		@Override
        public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
			
		}

        @Override
        public void onPageSelected(int position) {
        	mActionBar.setSelectedNavigationItem(position);
//        	Log.d("page",  "pos="+position);

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

    //设置搜索条
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_searchview);


		//打开，直接显示搜索条
		searchItem.expandActionView();
		SearchView searchView = (SearchView) searchItem.getActionView();	
		//设置搜索条提示文字
		searchView.setQueryHint("请输入关键字");
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {		
			    ListView lv1 = (ListView) mViewPager.findViewWithTag("lv1");   
			    ListView lv2 = (ListView) mViewPager.findViewWithTag("lv2");   
			    ListView lv3 = (ListView) mViewPager.findViewWithTag("lv3"); 


			    List<String> topic = new ArrayList<String>();
			    List<String> question = new ArrayList<String>();
			    List<String> sample = new ArrayList<String>();

			    //清空之前的内容
			    topicDefaultDisplay.clear();
			    questionDefaultDisplay.clear();
			    sampleDefaultDisplay.clear();
			    //设定新的内容
			    if(newText.equals("")){
			    	topicDefaultDisplay.add(noResult);
			    	questionDefaultDisplay.add(noResult);
			    	sampleDefaultDisplay.add(noResult);
			    }else{
			    	mdb.openDatabase();
				    topic = mdb.searchTopic(newText);
				    question = mdb.searchQuestion(newText);
				    sample = mdb.searchSample(newText);
				    mdb.closeDatabase();
				    
				    if(topic.size()==0||question.size()==0||sample.size()==0){
				    	topicDefaultDisplay.add(noResult);
				     	questionDefaultDisplay.add(noResult);
				    	sampleDefaultDisplay.add(noResult);
				    }else{
				    	for(int i = 0;i<topic.size();i++){
					    	topicDefaultDisplay.add(i,topic.get(i));
					    	questionDefaultDisplay.add(i,question.get(i));
					    	sampleDefaultDisplay.add(i,sample.get(i));
					    }
				    }
				    
			    }
			    
			   //通知个list的adapter更新内容
		       if(lv1!=null){
			      topicAdapter.notifyDataSetChanged(); 			      
		       }
		       if(lv2!=null){
			      questionAdapter.notifyDataSetChanged();  
		       }
		       if(lv3!=null){
			      sampleAdapter.notifyDataSetChanged();  
		       }


				
	
				return true;
			}
		});
		
		searchItem.setOnActionExpandListener(new OnActionExpandListener() {
			
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
//				Toast.makeText(SearchMainActivity.this, "expand", Toast.LENGTH_LONG).show();
				return true;
			}
			
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
//                Toast.makeText(SearchMainActivity.this,"collapse",Toast.LENGTH_SHORT).show();				
                return true;
			}
		});
			
		
		return super.onCreateOptionsMenu(menu);
	}
	
	


}
