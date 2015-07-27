package com.hrd.clientserverdemo;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.hrd.clientserverdemo.tran.BizComtran;
import com.hrd.clientserverdemo.tran.BizInterface;
import com.hrd.clientserverdemo.tx.FriendDelete_REQ;
import com.hrd.clientserverdemo.tx.FriendList_RES;

public class ListFriendActivity extends Activity implements BizInterface{

	private BizComtran mComTran;
	   ListView list;
	  MyAdapter adapter;
	  private Item item=new Item();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_activity);
		
		mComTran=new BizComtran(this,this);
				try{
					mComTran.msgTrSend("listFriends",new HashMap<String,Object>());
				}catch(Exception e){e.printStackTrace();}
				
				
	
				findViewById(R.id.btn_register).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ListFriendActivity.this,CreateFriendActivity.class);
						startActivityForResult(intent,1);
					}
				});
				
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	
			if(resultCode==RESULT_OK){
				try {
					mComTran.msgTrSend("listFriends",new HashMap<String,Object>());
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	

	@Override
	public void msgTrSend(String trand_cd) {
		if(trand_cd.equalsIgnoreCase("deleteFriend")){
			try {
				FriendDelete_REQ req= new FriendDelete_REQ(this, trand_cd);
				req.setFriendNum(item.getArticle_id());
				
				Log.d("id","result"+item.getArticle_id());
				
				mComTran.msgTrSend(trand_cd, req.getSendMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<Item> arr;
	@Override
	public void msgTrRecv(String trand_cd,Object resData) {
		try{	
			if(trand_cd.equalsIgnoreCase("listFriends")){
				FriendList_RES res=new FriendList_RES(this, resData);
				 
				arr=new ArrayList<>();
				
			
				for(int i=0;i<res.getLength();i++){
					res.move(i);
					Item item=new Item();
					item.setArticle_id(res.getNum());
					item.setArticle_title(res.getName());
					item.setArticle_image(res.getAddr());
					arr.add(item);
				}
					
				 list=(ListView) findViewById(R.id.listView);
				 adapter=new MyAdapter(this, arr,item);
				 
				 Log.d("Size","Size: "+arr.size()+arr.get(0).getArticle_title());
				 
						ListFriendActivity.this.runOnUiThread(new Runnable() {	
							@Override
							public void run() {
								list.setAdapter(adapter);
							}
						});
	
			}
			
			if(trand_cd=="deleteFriend"){
				try {
					mComTran.msgTrSend("listFriends",new HashMap<String,Object>());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
	}catch(Exception e){e.printStackTrace();}
	}



	 
	  
}
