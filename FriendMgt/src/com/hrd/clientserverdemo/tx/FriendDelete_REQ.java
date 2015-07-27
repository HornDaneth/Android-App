package com.hrd.clientserverdemo.tx;

import org.json.JSONException;

import android.app.Activity;

import com.webcash.sws.comm.tx.TxField;
import com.webcash.sws.comm.tx.TxMessage;
import com.webcash.sws.comm.tx.TxRecord;



public class FriendDelete_REQ extends TxMessage{

	
	private static int IDX_NUM;
	
	
	public FriendDelete_REQ(Activity activity,String trand_cd)throws Exception{

		mLayout=new TxRecord();
		
		/*
		 * ***The number of field use according to number request data in API document
		 * NUM,NAME,TEl,ADDR must the same name and type as in API Document
		 */
		
		IDX_NUM=mLayout.addField(new TxField("num", "Id"));
	
		
		//get json object 
		super.initSendMessage(activity, trand_cd);
	}
	
	public void setFriendNum(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX_NUM).getId(), value);
	}
	
	
}
