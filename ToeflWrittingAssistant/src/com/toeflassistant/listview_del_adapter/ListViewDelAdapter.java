package com.toeflassistant.listview_del_adapter;

import java.util.List;

import com.toeflassistant.writting.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewDelAdapter extends BaseAdapter {
	
	private List<String> listItems = null;
	private Context mContext;
	private Button curDel_btn;
	private int chang_index = -1;

	public ListViewDelAdapter(Context mContext, List<String> inputListItems) {
		this.mContext = mContext;
		this.listItems = inputListItems;
		
	}
	
	@Override
	public int getCount() {
		return this.listItems.size();
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
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.notebook_listview_item_activity, null);
			viewHolder.tvInfo = (TextView) view.findViewById(R.id.info);
			viewHolder.btnDel = (Button) view.findViewById(R.id.delete);
			viewHolder.detail_description = (TextView) view.findViewById(R.id.detail_description);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tvInfo.setText((position+1) + ". " + this.listItems.get(position));
		viewHolder.detail_description.setText((position+1) + ". " + this.listItems.get(position));
		if (chang_index == position)  {
			viewHolder.detail_description.setVisibility(View.VISIBLE);
			viewHolder.tvInfo.setText(" ");
	        viewHolder.btnDel.setVisibility(View.VISIBLE);  
		}
        else {
        	viewHolder.detail_description.setVisibility(View.GONE); 
    		viewHolder.tvInfo.setText((position+1) + ". " + this.listItems.get(position));
	        viewHolder.btnDel.setVisibility(View.GONE);  
        }
				//-----为删除按钮添加监听事件，实现点击删除按钮时删除该项
				viewHolder.btnDel.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
					
//					    final ViewHolder holder = (ViewHolder) v.getTag();

						if(curDel_btn!=null){
							curDel_btn.setVisibility(View.GONE);
////			        	    viewHolder.detail_description.setVisibility(View.GONE); 
						}
						listItems.remove(position);
						notifyDataSetChanged();				
					}
				});	
				return view;
	}

	//------这个方法用于更改子item的状态  
    public void changeImageVisable(View view, int position) {  
        // 隐藏提示  
        if (chang_index == position) {  
        	ViewHolder viewHolder = (ViewHolder) view.getTag();  
            if (viewHolder.detail_description.getVisibility() == View.VISIBLE||
            		viewHolder.btnDel.getVisibility() == View.VISIBLE)  {
            	viewHolder.detail_description.setVisibility(View.GONE);  
            	viewHolder.btnDel.setVisibility(View.GONE);  
            }      	
            else{
            	viewHolder.detail_description.setVisibility(View.VISIBLE);  
        	    viewHolder.btnDel.setVisibility(View.GONE);  
            }
            	

        } else {  
        	chang_index = position;  
            notifyDataSetChanged();// restart getview  
        }  

    }  
    
	final static class ViewHolder {
		TextView tvInfo;
		Button btnDel;
		TextView detail_description;
		ImageView imageView;

	}

}
