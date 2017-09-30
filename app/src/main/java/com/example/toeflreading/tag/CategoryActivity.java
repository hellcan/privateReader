package com.example.toeflreading.tag;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.toeflreading.common.TopicDetailActivity;
import com.example.toeflreading.database.mDatabase;
import com.example.toeflreading.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends Activity {

    private DrawerLayout mDrawerLayout; // 设置的是左侧的抽屉菜单
    private ListView mDrawerList;
    private ArrayAdapter<String> adapter;
    private ActionBarDrawerToggle mDrawerToggle;// actionBar打开关闭的
    private CharSequence mTitle;
    private String[] mTag;
    private String tag_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mTitle = getTitle();
        mTag = getResources().getStringArray(R.array.tag_array);

        mDrawerLayout = findViewById(R.id.drawerlayout);
        mDrawerList = findViewById(R.id.left_drawer);

        //侧边栏设置数据
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTag);
        mDrawerList.setAdapter(adapter);

        //设置侧边栏被打开关闭的对象
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                           //Context
                mDrawerLayout,                  //包含侧边栏页面布局
                R.drawable.ic_drawer,           //actionbar侧边栏点击按钮ICON
                R.string.drawer_open,           //Open
                R.string.drawer_close           //Close
        ) {
            //侧边栏打开的时候
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("请选择"); // 设置actionBar的文字
                invalidateOptionsMenu(); // Call onPrepareOptionsMenu()
            }

            // 侧边栏关闭的时候
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();// 重新绘制actionBar上边的菜单项
            }

        };


        // 开启ActionBar上APP ICON的功能：点击打开和点击关闭7
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        //设置滑动菜单 打开/关闭事件
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectItem(position);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tag_name = extras.getString("tag_name");

            for (int i = 0; i < mTag.length; i++) {
                if (tag_name.matches(mTag[i])) {
                    selectItem(i);
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
            }
        } else {
            if (savedInstanceState == null) {
                selectItem(0);
                getActionBar().setTitle("请选择");
                mDrawerLayout.openDrawer(Gravity.START);
            }
        }


    }

    private void selectItem(int position) {
        // 通过替换fragment来更新  main content
        Fragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ContentFragment.TAG_ID, position);
        contentFragment.setArguments(bundle);

        // fragment创建好了之后需要交给fragmentManager来替换到相应的视图中
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, contentFragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mTag[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 菜单项的设置
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 设置actionBar上边图标的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 将ActionBar上的图标与Drawer结合起来
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.action_websearch:
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri uri = Uri.parse("http://blog.csdn.net/xlgen157387");
//                intent.setData(uri);
//                startActivity(intent);
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据官方文档提示的信息
     * <p>
     * 将mDrawerToggle.syncState();放入到onPostCreate中
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // 需要将ActionDrawerToggle与DrawerLayout的状态同步
        // 将ActionBarDrawerToggle中的drawer图标，设置为ActionBar中的Home-Button的Icon
        mDrawerToggle.syncState();
    }

    /**
     * 当屏幕发生选装的时候也需要进行相应的设置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public static class ContentFragment extends Fragment {
        public static final String TAG_ID = "tag_number";
        private TagTopicListAdapter mAdapter;
        mDatabase mdb = new mDatabase();

        public ContentFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.tag_fragment_content_activity, container, false);
            //获得在抽屉菜单List选中时，选中item的position
            //因为是activity和fragment两个class，需要intent传递数据
            int selectedPosition = getArguments().getInt(TAG_ID);
            String[] mTagTitles = getResources().getStringArray(R.array.tag_array);
            String seletedTagTitle = mTagTitles[selectedPosition];
            //数据库查找包含该tag的信息
            mdb.openDatabase();
            List<String> sampleTotalList = new ArrayList<String>();
            List<List<String>> wholeList = new ArrayList<List<String>>();
            List<List<String>> comboList = mdb.getTagTopicList(seletedTagTitle);
            List<String> topicIdList = comboList.get(0);
            List<String> topicList = comboList.get(1);
            for (int i = 0; i < topicIdList.size(); i++) {
                sampleTotalList.add(mdb.sampleTotal(Integer.valueOf(topicIdList.get(i))));
            }
            wholeList.add(topicIdList);
            wholeList.add(topicList);
            wholeList.add(sampleTotalList);

            ListView tagTopicList = (ListView) v.findViewById(R.id.tag_topic_list);
            mAdapter = new CategoryActivity.TagTopicListAdapter(getActivity(), wholeList);
            tagTopicList.setAdapter(mAdapter);
            mdb.closeDatabase();

            tagTopicList.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    final TextView topicIdText = v.findViewById(R.id.order_number);
                    final TextView sample = v.findViewById(R.id.sample_total);
                    String questionId = topicIdText.getText().toString();
                    String sampleTotal = sample.getText().toString();
                    Intent intent = new Intent(getActivity(), TopicDetailActivity.class);
                    intent.putExtra("question_id", questionId);
                    intent.putExtra("sampleTotal", sampleTotal);
                    startActivity(intent);
                }
            });
            return v;
        }
    }

    public static class TagTopicListAdapter extends BaseAdapter {
        private List<String> topicIdList = null;
        private List<String> topicList = null;
        private List<String> sampleTotalList = null;
        private Context mContext;

        public TagTopicListAdapter(Context mContext, List<List<String>> wholeList) {
            this.mContext = mContext;
            this.topicIdList = wholeList.get(0);
            this.topicList = wholeList.get(1);
            this.sampleTotalList = wholeList.get(2);
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
            // position就是位置从0开始，c是Spinner,ListView中每一项要显示的view
            // parent就是父窗体了，也就是Spinner,ListView,GridView了.
            v = LayoutInflater.from(mContext).inflate
                    (R.layout.topic_listview_item_activity, null);
            TextView tagTopicId = v.findViewById(R.id.order_number);
            TextView tagTopicDescription = v.findViewById(R.id.topic_desciption);
            TextView tagTotalSample = v.findViewById(R.id.sample_total);
            tagTopicId.setText(topicIdList.get(position));
            tagTopicDescription.setText(topicList.get(position).trim());
            tagTotalSample.setText("Sample: " + sampleTotalList.get(position));
            return v;
        }
    }
}
