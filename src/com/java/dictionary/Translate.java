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
 * ��������࣬����ķ����඼Ҫ�̳���
 * 
 * @author
 *
 */
public abstract class Translate {

	/**
	 * �ʵ�����
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
	 * �����̳߳���Դ
	 * 
	 * @param es
	 *            �̳߳���Դ
	 * @return
	 */
	public Translate setEs(ExecutorService es) {
		this.es = es;
		return this;
	}

	/**
	 * �����������
	 * 
	 * @param param
	 *            ��������Ĳ���
	 * @return
	 */
	public Translate setParam(Params param) {
		this.param = param;
		return this;
	}

	/**
	 * ���ûص�����
	 * 
	 * @param callBack
	 *            ����ص�����
	 * @return
	 */
	public Translate setCallBack(ResultHook callBack) {
		this.callBack = callBack;
		return this;
	}

	/**
	 * ��ȡ���緭������
	 */
	public final void getTransResult() {
		if (es == null)
			throw new NullPointerException("�̳߳�Ϊ��");
		if (param == null)
			throw new NullPointerException("��������Ϊ��");
		if (callBack == null)
			throw new NullPointerException("�ص���������Ϊ��");
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
	 * ��ȡ����ĳ��󷽷��������ȡ��ʽ������ʵ��
	 * 
	 * @param result
	 *            �������ȡ��json���
	 * @return ��json�л�ȡ���緭��
	 */
	protected abstract String getTrans(String result);

	/**
	 * ��ȡ�ӿڵĳ��󷽷��������ĸ��ӿ������෵��
	 * 
	 * @return ��ͬ���뷽ʽ�Ľӿڵ�ַ
	 */
	protected abstract String getBaseUrl();

	/**
	 * ��ȡ�����URL
	 * 
	 * @param from
	 *            ��Ҫ������ҳ������
	 * @param param
	 *            ����Ĳ���
	 * @return ����������ҳ���͵�URL
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
	 * �������ĵı���
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
