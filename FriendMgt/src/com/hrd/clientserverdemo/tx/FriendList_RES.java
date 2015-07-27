package com.hrd.clientserverdemo.tx;

import org.json.JSONException;

import android.app.Activity;

import com.webcash.sws.comm.tx.TxField;
import com.webcash.sws.comm.tx.TxMessage;
import com.webcash.sws.comm.tx.TxRecord;



public class FriendList_RES extends TxMessage{

	private static int IDX_NUM;//index of user id in TxRecord
	private static int IDX_NAME;//index of user password in TxRecord
	private static int IDX_TEL;////index of user name in TxRecord
	private static int IDX_ADDR;////index of user addr in TxRecord
	
	
	public FriendList_RES(Activity activity,Object obj)throws Exception{

		mLayout=new TxRecord();
		
		/*
		 * ***The number of field use according to number request data in API document
		 * NUM,NAME,TEl,ADDR must the same name and type as in API Document
		 */
		
		IDX_NUM=mLayout.addField(new TxField("num","USER_ID"));
		IDX_NAME=mLayout.addField(new TxField("names", "USER_PASS"));
		IDX_TEL=mLayout.addField(new TxField("tel", "USER_NAME"));
		IDX_ADDR=mLayout.addField(new TxField("addr", "USER_NAME"));
		
		//get json object 
		super.initRecvMessage(activity, obj, "");
	}
	
	//get data from Json object
	
	public String getNum()throws JSONException,Exception{
		return getString(mLayout.getField(IDX_NUM).getId());
	}
	
	public String getName()throws JSONException,Exception{
		return getString(mLayout.getField(IDX_NAME).getId());
	}
	
	public String getTel()throws JSONException,Exception{
		return getString(mLayout.getField(IDX_TEL).getId());
	}
	
	public String getAddr()throws JSONException,Exception{
		return getString(mLayout.getField(IDX_ADDR).getId());
	}	

}
