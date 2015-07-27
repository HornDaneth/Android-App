package com.hrd.clientserverdemo.tran;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class BizComtran {
		
	
		private Activity mActivity;
		private BizInterface mBizInterface;
		private ArrayList<NameValuePair> mDataRequest=new ArrayList<>();
		private ConnectivityManager mConnectivity;
		private NetworkInfo mInfo;
		public static final int CONNECTION_TIMEOUT=60*1000;
		public static final String mUserAgent = "Mozilla/5.0 (Linux; U; Android 2.1; en-us; sdk Build/ERD79) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17";
		private String mOutput;
		private JSONObject request;
		
		public static final String KEY_REQ_DATA 				= "RESP_DATA";	
		public static final String KEY_RES_DATA 				= "RESP_DATA";	
		public static final String KEY_RSLT_CD 					= "RSLT_CD";	
		public static final String KEY_RSLT_MSG					= "RSLT_MSG";	
		
		private DefaultHttpClient mHttpClient;
		private String trand_cd;
		
		public static final String WEB_URL="http://192.168.178.32:8080/FriendMgt";
			
		public BizComtran(){}
		
		public BizComtran(Activity activity,BizInterface bizInterface){
			this.mActivity=activity;    this.mBizInterface=bizInterface;
		}
	
		public void msgTrSend(String trand_cd,HashMap<String, Object> data)throws Exception{
			
			this.trand_cd=trand_cd;
				makeJsonData(data);
				
				  /**
				   * You should not perform long operation in a main thread or UI thread.
				   * 	 such as network access or query database 
				   * example : this case i load image from web url so i need to implements a Background process 
				   * 	
				   * 
				   * 	*** Note : 
				   * 		don't perform long operation in UI thread because if the performance over 5 seconds 
				   * 		Android Not Response(ANR) will block the process by throwing exception
				   */
				
				new AsyncTask<Object, Object, Void>() {
					@Override
					protected Void doInBackground(Object... params) {
						sendData();
						return null;
					}
				}.execute();	
			
		}
		
		
		private void sendData(){
			Log.d("sendData", "Start >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			/*
			 * -this class monitor network connection
			 * -send broacast intent when network connectivity change
			 * - attemp to "fail over" when another network lost
			 */
			
			try{
				mConnectivity=(ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
				mInfo=mConnectivity.getActiveNetworkInfo();
				
				if(mInfo==null){
					Log.d("Network fail : ","Internet connection fails, please try again later.");
				}else{
					
					Log.d("Network TRUE : ","Internet connection successfully.");
					
					
					HttpParams httpParam=new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParam, CONNECTION_TIMEOUT);
					HttpConnectionParams.setSoTimeout(httpParam, CONNECTION_TIMEOUT);
					
					
					if (WEB_URL.toLowerCase().startsWith("https")) {
						SchemeRegistry registry = new SchemeRegistry();
						registry.register(new Scheme("http", new PlainSocketFactory(), 80));
						//registry.register(new Scheme("https", new FakeSocketFactory(), 443));
						mHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParam, registry), httpParam);
					}else{
					
					mHttpClient=new DefaultHttpClient(httpParam);
					}
	
					
					String gateUrl = "";
					gateUrl = WEB_URL + "/" + trand_cd;		
					
					
					Log.d("url request",gateUrl);
					
					HttpPost http=new HttpPost(gateUrl);
					
					
					http.setHeader("User-Agent", mUserAgent);
			
					//http.setEntity(new UrlEncodedFormEntity(mDataRequest,"UTF-8"));
					
					StringEntity entity=new StringEntity(request.toString(),HTTP.UTF_8);
					entity.setContentType("application/json");
					http.setEntity(entity);
					
					ResponseHandler<String> mResponeHandler=new BasicResponseHandler();
					mOutput=mHttpClient.execute(http,mResponeHandler);
					
					Log.d("Output:", mOutput);
					
					mDataRequest.clear();
				}
					
			}catch(Exception e){e.printStackTrace();}
			
			try{
				if(null == mOutput || "".equals(mOutput))
					Log.d("Parse Data","JSON Data Error ");
					
			parseJsonData(mOutput);	
			}catch(Exception e){e.printStackTrace();}
			Log.d("End Send","Finish Send Data>>>>>>>>>>>>>>>>");
		}
		
		public void makeJsonData(HashMap<String, Object> data)throws JSONException{
			
			JSONObject jObject=new JSONObject();
			JSONObject inputObject=new JSONObject();
			
			Iterator<String> i=data.keySet().iterator();
			while(i.hasNext()){
				String key=i.next();
				if(data.get(key) instanceof List){
					JSONArray jarray=new JSONArray();
					List<HashMap<String, String>> arr=(List<HashMap<String, String>>)data.get(key);
					
					for(HashMap<String, String> map : arr){
						Iterator<String> im=map.keySet().iterator();
						JSONObject jobject=new JSONObject();
						while(im.hasNext()){
							String k=im.next();
							jobject.put(k, map.get(k));
						}
						jarray.put(jobject);
					}
					jObject.put(key,jarray);
				}
				else{
					jObject.put(key, data.get(key));
				}
			}
		
			//inputObject.put("friend", jObject);
			request=jObject;
			mDataRequest.add(new BasicNameValuePair("friend",jObject.toString()));
			Log.d("input data: ", jObject.toString());
			
		}
		
		public void msgTrRecv(Object jResData){
			Log.d("msgTrRecv ","Start>>>>>>>>>>>>>>>>>>>>>>");
			
			
			mBizInterface.msgTrRecv(trand_cd,jResData);
		} 
		
		public void parseJsonData(String output)throws JSONException{
			JSONObject jobjectOutput 	= null;
			JSONArray  jarrayResData 	= new JSONArray();
		
			
			try {
				jobjectOutput = new JSONObject(output.toString());	
					
				if (!jobjectOutput.isNull(KEY_RES_DATA)) {
					try{
						jarrayResData = jobjectOutput.getJSONArray(KEY_RES_DATA);	
					}catch(Exception e){
						jarrayResData.put(jobjectOutput);
					}
					
					
				}
				
				msgTrRecv(jarrayResData);				
				
		

			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
}
