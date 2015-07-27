package com.hrd.clientserverdemo.tx;

import org.json.JSONException;

import android.app.Activity;

import com.webcash.sws.comm.tx.TxField;
import com.webcash.sws.comm.tx.TxMessage;
import com.webcash.sws.comm.tx.TxRecord;



public class FriendCreate_REQ extends TxMessage{

	
	private static int IDX_NAME;//index of user password in TxRecord
	private static int IDX_TEL;////index of user name in TxRecord
	private static int IDX_ADDR;////index of user addr in TxRecord
	
	
	public FriendCreate_REQ(Activity activity,String trand_cd)throws Exception{

		mLayout=new TxRecord();
		
		/*
		 * ***The number of field use according to number request data in API document
		 * NUM,NAME,TEl,ADDR must the same name and type as in API Document
		 */
		
		IDX_NAME=mLayout.addField(new TxField("names", "USER_PASS"));
		IDX_TEL=mLayout.addField(new TxField("tel", "USER_NAME"));
		IDX_ADDR=mLayout.addField(new TxField("addr", "USER_NAME"));
		
		//get json object 
		super.initSendMessage(activity, trand_cd);
	}
	
	public void setFriendName(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX_NAME).getId(), value);
	}
	
	public void setFriendTel(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX_TEL).getId(), value);
	}
	
	public void setFriendAddr(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX_ADDR).getId(), value);
	}
	
}
