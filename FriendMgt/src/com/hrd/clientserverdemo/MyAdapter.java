package com.hrd.clientserverdemo;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class MyAdapter extends ArrayAdapter<Item> {
		
        private final ListFriendActivity mActivity;
        private final ArrayList<Item> itemsArrayList;
        private Item item;
        
        public MyAdapter(ListFriendActivity context, ArrayList<Item> itemsArrayList,Item item) {
 
            super(context, R.layout.row, itemsArrayList);
 
            this.mActivity = context;
            this.itemsArrayList = itemsArrayList;
            this.item=item;
        }
        
         class MyViewHolder{
        	TextView labelView;
        	TextView valueView;
        	ImageView imgView;
        	Button delete;
        	Button edit;
        	public MyViewHolder(View v) {
        		//imgView=(ImageView)v.findViewById(R.id.imgView);
                 labelView = (TextView) v.findViewById(R.id.txtCity);
                 valueView = (TextView) v.findViewById(R.id.txtTime);
                 delete=(Button) v.findViewById(R.id.btn_delete);
                 edit=(Button) v.findViewById(R.id.btn_edit);
			}
        	
        }
        
       
       
    	
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
        	View row=convertView;
        	 MyViewHolder holder=null;
        	if(row==null){
        		// 1. Create inflater
                LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
                // 2. Get rowView from inflater
                row = inflater.inflate(R.layout.row, parent, false);
                
                holder=new MyViewHolder(row);
                row.setTag(holder);     
        	}else{
        		holder=(MyViewHolder) row.getTag();
        	}
        	
			holder.labelView.setText(itemsArrayList.get(position).getArticle_title());
            holder.valueView.setText(itemsArrayList.get(position).getArticle_id());
            
            
            holder.delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					item.setArticle_id(itemsArrayList.get(position).getArticle_id());
					item.setArticle_title(itemsArrayList.get(position).getArticle_title());
		            item.setArticle_image(itemsArrayList.get(position).getArticle_image());
		            
					mActivity.msgTrSend("deleteFriend");
					
				}
			});
            holder.edit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mActivity,CreateFriendActivity.class);
					intent.putExtra("name",item.getArticle_title());
					intent.putExtra("addr",item.getArticle_image());
					mActivity.startActivity(intent);
				}
			});
            
            return row;
        }
        
       
}