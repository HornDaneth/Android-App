package com.hrd.clientserverdemo.tran;

public interface BizInterface {
	void msgTrSend(String trand_cd);
	void msgTrRecv(String trand_cd,Object resData);
}
