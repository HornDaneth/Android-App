package com.hrd.clientserverdemo;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hrd.clientserverdemo.tran.BizComtran;
import com.hrd.clientserverdemo.tran.BizInterface;
import com.hrd.clientserverdemo.tx.FriendCreate_REQ;

public class CreateFriendActivity extends Activity implements BizInterface{

	private BizComtran mComTran;
	private ImageButton btn_Create;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_friend);
		mComTran =new BizComtran(this, this);
		
		btn_Create=(ImageButton) findViewById(R.id.btn_register);
		
		btn_Create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				msgTrSend("addFriend");
			}
		});
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void msgTrSend(String trand_cd) {
		try{
			FriendCreate_REQ req=new FriendCreate_REQ(this, trand_cd);
			EditText uname=(EditText)findViewById(R.id.txt_username);
			EditText uaddr=((EditText)findViewById(R.id.txtAddr));
			EditText utel=((EditText)findViewById(R.id.txtTel));
			
			req.setFriendAddr(uaddr.getText().toString());	
			req.setFriendName(uname.getText().toString());
			req.setFriendTel(utel.getText().toString());
			
			mComTran.msgTrSend(trand_cd, req.getSendMessage());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void msgTrRecv(String trand_cd,Object resData) {
		try{
			setResult(RESULT_OK);
			finish();
			
			
		}catch(Exception e){e.printStackTrace();}
	}
	  
}
