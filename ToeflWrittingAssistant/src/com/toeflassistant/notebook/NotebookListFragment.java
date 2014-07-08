package com.toeflassistant.notebook;


import java.util.ArrayList;
import java.util.List;

import com.toeflassistant.database.mDatabase;
import com.toeflassistant.listview_del_adapter.ListViewDelAdapter;
import com.toeflassistant.writting.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.FrameLayout.LayoutParams;


public class NotebookListFragment extends Fragment {
	private static final String ARG_POSITION = "position";
    private ListViewDelAdapter mAdapter;
	private int position;
	mDatabase mdb = new mDatabase();
	static String[] title;

	public static NotebookListFragment newInstance(int position, String[] titles) {
		NotebookListFragment f = new NotebookListFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		title = titles;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		//����card����
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources()
				.getDisplayMetrics());
	
		View v = inflater.inflate(R.layout.notebook_listview_activity, null);
		ListView mListView = (ListView)v.findViewById(R.id.notebook_list);
		params.setMargins(margin, margin, margin, margin);
		
		List<String> categoryList = new ArrayList<String>();
		List<String> sentenceList = new ArrayList<String>();

		for(int i= 0;i<title.length;i++){
			categoryList.add(title[i]);
		}

		mdb.openDatabase();
		sentenceList = mdb.getSentenceList(categoryList.get(position));
		mdb.closeDatabase();

        mAdapter = new ListViewDelAdapter(getActivity(),sentenceList,categoryList.get(position));
        
        mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAdapter.changeImageVisable(view, position);

			}
        	
        });
        mListView.setAdapter(mAdapter);

		if(mListView.getParent()==null){
			fl.addView(mListView);
		}else{
			((ViewGroup)mListView.getParent()).removeView(mListView);
			fl.addView(mListView);
		}
		return fl;
	}
	


}
