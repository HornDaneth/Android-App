/**
 * <pre>
 * ì „ë¬¸ í�´ëž˜ìŠ¤
 * @COPYRIGHT (c) 2010 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  : 
 * @History      : 
 *
 * </pre>
 **/
package com.webcash.sws.comm.tx;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;


public class TxMessage {
	
	protected Context mContext;
	protected TxRecord mLayout;
	protected HashMap<String, Object> mSendMessage;
	protected JSONArray mRecvMessage;
	protected int mIdx = 0;
	protected String mTxNo;
	
	public TxMessage() {}
	
	public void initSendMessage(Context context, String txNo) throws Exception {
		mContext = context;
		mSendMessage = new HashMap<String, Object>();
		
	}
	
	public HashMap<String, Object> getSendMessage() {
		return mSendMessage;
	}
	
	public void initRecvMessage(Context ctx, Object obj, String txNo) throws JSONException, Exception {
		initRecvMessage(ctx, obj, txNo, null);
	}
	
	public void initRecvMessage(Context ctx, Object obj, String txNo, String className) throws JSONException, Exception {
		mContext = ctx;
		if(obj.equals(""))
			mRecvMessage = new JSONArray("[]");
		else
			mRecvMessage = (JSONArray)obj;
		
	}
	
	public boolean isEOR() throws JSONException {
		if(mIdx == getLength())
			return true;
		return false;
	}
	
	public void moveFirst() {
		mIdx = 0;
	}
	
	public void moveNext() {
		mIdx++;
	}
	
	public void moveLast() throws JSONException {
		mIdx = getLength() - 1;
	}
	
	public void movePrev() {
		mIdx--;
	}
	
	public void move(int pos) {
		mIdx = pos;
	}
	
	public boolean has(String key) throws JSONException {
		return mRecvMessage.getJSONObject(mIdx).has(key);
	}
	
	public boolean getBoolean(String key) throws JSONException {
		if(has(key) == false) return false;
		return mRecvMessage.getJSONObject(mIdx).getBoolean(key);
	}
	
	public Object getRecord(String key) throws JSONException {
		if(has(key) == false) return new JSONArray("[]");
		return mRecvMessage.getJSONObject(mIdx).get(key);
	}
	
	
	public boolean has(String key, int objDepth) throws JSONException {
		if(mRecvMessage.length() <= objDepth) return false;
		return mRecvMessage.getJSONObject(objDepth).has(key);
	}
	
	public Object getRecord(String key, int objDepth) throws JSONException {
		if(has(key, objDepth) == false) return new JSONArray("[]");
		return mRecvMessage.getJSONObject(objDepth).get(key);
	}
	
	public JSONArray getArray(String key) throws JSONException {
		if(has(key) == false) return new JSONArray("[]");
		return mRecvMessage.getJSONObject(mIdx).getJSONArray(key);
	}	
	
	public String getString(String key) throws JSONException {
		if(has(key) == false) return "";		
		return mRecvMessage.getJSONObject(mIdx).getString(key);
	}
	
	public String getStringIdx(String key, int idx) throws JSONException {
		if(has(key) == false) return "";		
		return mRecvMessage.getJSONObject(idx).getString(key);		
	}
	
	public int getInt(String key) throws JSONException {
		if(has(key) == false) return 0;
		return mRecvMessage.getJSONObject(mIdx).getInt(key);
	}
	
	public int getLength() throws JSONException {
		return mRecvMessage.length();
	}
	
	public String getMessageToString() throws JSONException {
		return mRecvMessage.toString();
	}
}