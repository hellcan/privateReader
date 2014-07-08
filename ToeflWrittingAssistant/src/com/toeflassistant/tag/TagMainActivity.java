package com.toeflassistant.tag;


import java.util.ArrayList;
import java.util.List;

import com.toeflassistant.common.TopicDetailActivity;
import com.toeflassistant.database.mDatabase;
import com.toeflassistant.writting.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TagMainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTagTitles;


    
    @Override
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_main_activity);

        //actionbar���������
        mTitle = mDrawerTitle = getTitle();
        mTagTitles = getResources().getStringArray(R.array.tag_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //���õ���drawerListʱ�����������Ӱ
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        //����drawerList������
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.tag_drawerlistview_item_activity, mTagTitles));
        //����drawerList�ĵ����¼�
        mDrawerList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
	            selectItem(position);				
			}      	
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            	
                getActionBar().setTitle("Choose Category");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        
        //��������ļ����¼�
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
       //Ĭ�ϴ򿪳���˵�
       mDrawerLayout.openDrawer(Gravity.LEFT);
    }
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.drawer_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
    
    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	protected void selectItem(int position) {
		// ͨ���滻fragment������  main content 
        Fragment fragment = new TopicListFragment();
        Bundle args = new Bundle();
        args.putInt(TopicListFragment.TAG_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mTagTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);		
	}
	
	@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	/**
     * Fragment that appears in the "content_frame", shows a TopicList
     */
    public static class TopicListFragment extends Fragment {
        public static final String TAG_NUMBER = "tag_number";
        private TagTopicListAdapter mAdapter;
        mDatabase mdb = new mDatabase();

        
        public TopicListFragment() {
            // Empty constructor required for fragment subclasses
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {	
        	View v = inflater.inflate(R.layout.tag_fragment_content_activity, container, false);
            //����ڳ���˵�Listѡ��ʱ��ѡ��item��position
        	//��Ϊ��activity��fragment����class����Ҫintent��������
        	int selectedPosition = getArguments().getInt(TAG_NUMBER);
            String[] mTagTitles = getResources().getStringArray(R.array.tag_array);
            String seletedTagTitle = mTagTitles[selectedPosition];
            //���ݿ���Ұ�����tag����Ϣ
            mdb.openDatabase();
            List<String> sampleTotalList = new ArrayList<String>();
            List<List<String>> wholeList = new ArrayList<List<String>>();
            List<List<String>> comboList = mdb.getTagTopicList(seletedTagTitle);
            List<String> topicIdList = comboList.get(0);
            List<String> topicList = comboList.get(1);
            for(int i=0; i < topicIdList.size(); i++){
            	sampleTotalList.add(mdb.sampleTotal(Integer.valueOf(topicIdList.get(i))));
            }
            wholeList.add(topicIdList);
            wholeList.add(topicList);
            wholeList.add(sampleTotalList);

        	ListView tagTopicList = (ListView)v.findViewById(R.id.tag_topic_list);   	
        	mAdapter = new TagTopicListAdapter(getActivity(), wholeList);
        	tagTopicList.setAdapter(mAdapter);
        	mdb.closeDatabase();
        	
        	tagTopicList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					final TextView topicIdText = (TextView)v.findViewById(R.id.order_number);
					final TextView sample = (TextView)v.findViewById(R.id.sample_total); 
					String questionId = topicIdText.getText().toString();
					String sampleTotal = sample.getText().toString();
					Intent intent = new Intent(getActivity(),TopicDetailActivity.class);
					intent.putExtra("question_id", questionId);
					intent.putExtra("sampleTotal", sampleTotal);
					startActivity(intent);
				}     		
        	});
            return v;
        }
    }

		 public static class TagTopicListAdapter extends BaseAdapter{
				private List<String> topicIdList = null;
				private List<String> topicList = null;
				private List<String> sampleTotalList = null;
				private Context mContext;

		    public TagTopicListAdapter(Context mContext, List<List<String>> wholeList) {
					this.mContext = mContext;
					this.topicIdList = wholeList.get(0);
					this.topicList = wholeList.get(1);
					this.sampleTotalList =wholeList.get(2);
			}
		    
			@Override
			public int getCount() {
				return topicIdList.size();
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View v, ViewGroup parent) {
				// position����λ�ô�0��ʼ��c��Spinner,ListView��ÿһ��Ҫ��ʾ��view
				// parent���Ǹ������ˣ�Ҳ����Spinner,ListView,GridView��.
				v = LayoutInflater.from(mContext).inflate
					    (R.layout.topic_listview_item_activity,null);
				TextView tagTopicId = (TextView)v.findViewById(R.id.order_number);
				TextView tagTopicDescription = (TextView)v.findViewById(R.id.topic_desciption);
				TextView tagTotalSample = (TextView)v.findViewById(R.id.sample_total);
				tagTopicId.setText(topicIdList.get(position));		
				tagTopicDescription.setText(topicList.get(position).trim());
				tagTotalSample.setText("Sample: " + sampleTotalList.get(position) );
				return v;
			}            
	}
		 
}