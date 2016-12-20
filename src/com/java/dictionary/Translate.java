package com.java.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * The abstract class for translation, which is extended by YDtrans, BDtrans, BYtrans
 * 
 * @author cheny1231
 *
 */
public abstract class Translate {

	/**
	 * Type of dictionary
	 */
	public static final int YD = 1;
	public static final int BD = 2;
	public static final int BY = 4;

	ExecutorService es;
	Params param;
	ResultHook callBack;

	public Translate() {
	}

	/**
	 * Set the thread pool
	 */
	public Translate setEs(ExecutorService es) {
		this.es = es;
		return this;
	}

	/**
	 * Set the parameters for requesting
	 */
	public Translate setParam(Params param) {
		this.param = param;
		return this;
	}

	/**
	 * Set the params for calling back
	 */
	public Translate setCallBack(ResultHook callBack) {
		this.callBack = callBack;
		return this;
	}

	/**
	 * Get the entrance to Internet translation
	 */
	public final void getTransResult() {
		if (es == null)
			throw new NullPointerException("线程池为空");
		if (param == null)
			throw new NullPointerException("参数不能为空");
		if (callBack == null)
			throw new NullPointerException("回调函数不能为空");
		es.execute(new Runnable() {

			@Override
			public void run(){
				String back = NetUtil.getResult(getUrl(param));
				back = getTrans(back);
				callBack.result(back);
			}
		});
	}

	/** 
	 * Abstract methods to get translation, which is overridden by its children
	 */
	protected abstract String getTrans(String result);

	/**
	 * Abstract method to get interfaces, which is determined by its children
	 * 
	 * @return interface address of different website
	 */
	protected abstract String getBaseUrl();

	/**
	 * Get Urls of different websites
	 * 
	 */
	private String getUrl(Params param) {
		Map<String, String> p = param.getParam();
		StringBuilder sb = new StringBuilder(getBaseUrl());
		sb.append("?");
		int i = 0;
		for (Map.Entry<String, String> e : p.entrySet()) {
			if (i != 0)
				sb.append("&");
			sb.append(e.getKey());
			sb.append("=");
			sb.append(encode(e.getValue()));
			i++;
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * Encoding for Chinese
	 */
	String encode(String input) {
		if (input == null) {
			return "";
		}

		try {
			return URLEncoder.encode(input, NetUtil.DEF_CHATSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return input;
	}

}
