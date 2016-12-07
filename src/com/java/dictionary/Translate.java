package com.java.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.sf.json.JSONObject;

/**
 * 翻译抽象类，具体的翻译类都要继承它
 * 
 * @author
 *
 */
public abstract class Translate {

	/**
	 * 词典类型
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
	 * 设置线程池资源
	 * 
	 * @param es
	 *            线程池资源
	 * @return
	 */
	public Translate setEs(ExecutorService es) {
		this.es = es;
		return this;
	}

	/**
	 * 设置请求参数
	 * 
	 * @param param
	 *            网络请求的参数
	 * @return
	 */
	public Translate setParam(Params param) {
		this.param = param;
		return this;
	}

	/**
	 * 设置回调函数
	 * 
	 * @param callBack
	 *            请求回调函数
	 * @return
	 */
	public Translate setCallBack(ResultHook callBack) {
		this.callBack = callBack;
		return this;
	}

	/**
	 * 获取网络翻译的入口
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
			public void run() {
				String back = NetUtil.getResult(getUrl(param));
				back = getTrans(back);
				callBack.result(back);
			}
		});
	}

	/**
	 * 获取翻译的抽象方法，具体获取方式由子类实现
	 * 
	 * @param result
	 *            从网络获取的json结果
	 * @return 从json中获取网络翻译
	 */
	protected abstract String getTrans(String result);

	/**
	 * 获取接口的抽象方法，具体哪个接口由子类返回
	 * 
	 * @return 不同翻译方式的接口地址
	 */
	protected abstract String getBaseUrl();

	/**
	 * 获取请求的URL
	 * 
	 * @param from
	 *            需要请求网页的类型
	 * @param param
	 *            请求的参数
	 * @return 返回请求网页类型的URL
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
	 * 用于中文的编码
	 * 
	 * @param input
	 * @return
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
