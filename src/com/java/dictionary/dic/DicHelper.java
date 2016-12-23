package com.java.dictionary.dic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.java.dictionary.common.IMessage;
import com.java.dictionary.common.ResultHook;
import com.java.dictionary.net.NetUtil;

import javafx.scene.control.TextArea;

public class DicHelper {
	
	ResultHook<IMessage> BDCallback;
	ResultHook<IMessage> BYCallback;
	ResultHook<IMessage> YDCallback;
	
	Translate tlBY;
	Translate tlYD;
	Translate tlBD;
	
	boolean isBDEnable;
	boolean isBYEnable;
	boolean isYDEnable;
	
	private String qName;
	/**
	 * Use multiple threads for different dictionary, StringBuffer is more safe than StringBuilder
	 */
	static StringBuffer text = new StringBuffer();

	public DicHelper() {
		
		ExecutorService es = Executors.newFixedThreadPool(3);

		/**
		 * Get the result through factory method
		 */
		tlBY = TransFactory.getTranslate(Translate.BY);
		tlYD = TransFactory.getTranslate(Translate.YD);
		tlBD = TransFactory.getTranslate(Translate.BD);

		/**
		 * Set params for different dictionary
		 */
		tlBY.setEs(es).setParam(() -> {
			Map<String, String> param = new HashMap<>();
			param.put("Word", qName);
			return param;
		}).setCallBack(BYCallback/*(callback) -> {
			setDataBY(new StringBuffer(callback.getMsg()).toString());
		}*/);

		tlYD.setEs(es).setParam(() -> {
			Map<String, String> param = new HashMap<>();
			/************** ¹Ì¶¨ ******************/
			param.put("keyfrom", "fanyi.web");
			param.put("type", "AUTO");
			param.put("doctype", "json");
			param.put("callback", "show");
			param.put("xmlVersion", "1.8");
			param.put("ue", "UTF-8");
			param.put("action", "FY_BY_CLICKBUTTON");
			param.put("typoResult", "true");
			/***********************************/
			param.put("i", qName);
			return param;
		}).setCallBack(YDCallback/*(callback) -> {
			setDataYD(new StringBuffer(callback.getMsg()).toString());
		}*/);

		tlBD.setEs(es).setParam(() -> {
			Map<String, String> param = new HashMap<>();
			param.put("transtype", "translang");
			param.put("from", "en");
			param.put("to", "zh");
			param.put("simple_means_flag", "3");
			param.put("query", qName);
			return param;
		}).setCallBack(BDCallback/*(callback) -> {
			setDataBD(new StringBuffer(callback.getMsg()).toString());
		}*/);

	}

	/*static public ExecutorService getEs() {
		return es;
	}*/
	
	public void setBDEnable(boolean isBDEnable) {
		this.isBDEnable = isBDEnable;
	}
	
	public void setBYEnable(boolean isBYEnable) {
		this.isBYEnable = isBYEnable;
	}
	
	public void setYDEnable(boolean isYDEnable) {
		this.isYDEnable = isYDEnable;
	}
	
	public void setAll(boolean enable){
		this.isBDEnable = enable;
		this.isBYEnable = enable;
		this.isYDEnable = enable;
	}
	
	public void setBDCallback(ResultHook<IMessage> bDCallback) {
		BDCallback = bDCallback;
		tlBD.setCallBack(BDCallback);
	}
	public void setBYCallback(ResultHook<IMessage> bYCallback) {
		BYCallback = bYCallback;
		tlBY.setCallBack(BYCallback);
	}
	public void setYDCallback(ResultHook<IMessage> yDCallback) {
		YDCallback = yDCallback;
		tlYD.setCallBack(YDCallback);
	}
	

/*	public static void setqName(String str) {
		qName = str;
	}

	public static String getqName() {
		return qName;
	}*/

	/**
	 * Translation
	 */
	public void getTrans(String qName){
		this.qName = qName;
		if(isBDEnable)
			tlBD.getTransResult(NetUtil.METHOD_POST);
		if(isBYEnable)
			tlBY.getTransResult(NetUtil.METHOD_GET);
		if(isYDEnable)
			tlYD.getTransResult(NetUtil.METHOD_POST);
			
	}



}
