package com.java.dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.TextArea;

public class DicTest {
	Translate tlBY;
	Translate tlYD;
	Translate tlBD;
	TextArea BDtext;
	TextArea BYtext;
	TextArea YDtext;
	static ExecutorService es;
	static String qName;
	/**
	 * 多个词典查询的时候使用多线程，StringBuffer较StringBuilder多线程安全，使用StringBuffer
	 */
	static StringBuffer text = new StringBuffer();

	public DicTest() {
		/**
		 * 1、创建size为3的线程池
		 */
		es = Executors.newFixedThreadPool(5);

		/**
		 * 2、通过工厂方法，获取词典翻译的对象
		 */
		tlBY = TransFactory.getTranslate(Translate.BY);
		tlYD = TransFactory.getTranslate(Translate.YD);
		tlBD = TransFactory.getTranslate(Translate.BD);

		/**
		 * 3、每类词典设置参数，初始化的时候设置即可
		 */
		tlBY.setEs(es).setParam(() -> {
			Map<String, String> param = new HashMap<>();
			param.put("Word", qName);
			return param;
		}).setCallBack((str) -> {
			setDataBY(new StringBuffer(str).toString());
		});

		tlYD.setEs(es).setParam(() -> {
			Map<String, String> param = new HashMap<>();
			/************** 固定 ******************/
			param.put("keyfrom", "CDictionary");
			param.put("type", "data");
			param.put("doctype", "json");
			param.put("callback", "show");
			param.put("version", "1.1");
			/***********************************/
			param.put("key", YDTrans.YDKEY);
			param.put("q", qName);
			return param;
		}).setCallBack((str) -> {
			setDataYD(new StringBuffer(str).toString());
		});

		tlBD.setEs(es).setParam(() -> {
			Map<String, String> param = new HashMap<>();
			param.put("appid", BDTrans.BDKEY);
			param.put("q", qName);
			param.put("from", "auto");
			param.put("to", "zh");
			String salt = String.valueOf(System.currentTimeMillis());
			param.put("salt", salt);
			String src = param.get("appid") + param.get("q") + param.get("salt") + BDTrans.BDSECURITY;
			param.put("sign", MD5.md5(src));
			return param;
		}).setCallBack((str) -> {
			setDataBD(new StringBuffer(str).toString());
		});

	}

	static public ExecutorService getEs() {
		return es;
	}

	public static void setqName(String str) {
		qName = str;
	}

	public static String getqName() {
		return qName;
	}

	/**
	 * 4、运行翻译
	 */
	public void transBD() {
		Translate tlBD = TransFactory.getTranslate(Translate.BD);
		tlBD.getTransResult();
	}

	public void transBY() {
		Translate tlBY = TransFactory.getTranslate(Translate.BY);
		tlBY.getTransResult();
	}

	public void transYD() {
		Translate tlYD = TransFactory.getTranslate(Translate.YD);
		tlYD.getTransResult();
	}

	private void setDataBD(String str) {
		// text.append(str).append("\n");
		BDtext.setText(str);
		BDtext.appendText("\n\n————百度翻译");

	}

	private void setDataBY(String str) {
		// text.append(str).append("\n");
		String[] strs = str.split("     ");
		BYtext.setText(strs[0] + "\n");
		for (int i = 1; i < strs.length; i++)
			BYtext.appendText(strs[i] + "\n");
		BYtext.appendText("\n————必应翻译");
	}

	private void setDataYD(String str) {
		// text.append(str).append("\n");
		YDtext.setText(str);
		YDtext.appendText("\n\n————有道翻译");
	}

	public void setBDtext(TextArea bDtext) {
		BDtext = bDtext;
	}

	public void setBYtext(TextArea bYtext) {
		BYtext = bYtext;
	}

	public void setYDtext(TextArea yDtext) {
		YDtext = yDtext;
	}

	public TextArea getBDtext() {
		return BDtext;
	}

	public TextArea getBYtext() {
		return BYtext;
	}

	public TextArea getYDtext() {
		return YDtext;
	}


}
