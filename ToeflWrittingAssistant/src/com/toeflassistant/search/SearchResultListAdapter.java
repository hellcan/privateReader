package com.toeflassistant.search;

import java.util.List;

import com.toeflassistant.database.mDatabase;
import com.toeflassistant.writting.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchResultListAdapter extends BaseAdapter {
	private List<String> resultList = null;
	private Context mContext;

	public SearchResultListAdapter(Context mContext, List<String> resultList) {
		this.mContext = mContext;
		this.resultList = resultList;
}
	
	@Override
	public int getCount() {
		return resultList.size();

	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		//�����������baseAdapter �޷�������
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		v = LayoutInflater.from(mContext).inflate
			    (R.layout.search_listview_item_activity,null);
		TextView result = (TextView)v.findViewById(R.id.search_result);
		try{
			if(resultList.get(0).equals("��ʱû���������")){
				result.setText(resultList.get(position));		
			}else{
					result.setText((position+1) + ". " + resultList.get(position).
							replaceAll("/////", "\n\n").trim());
				}
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();		
		}
	
		return v;
	}

}