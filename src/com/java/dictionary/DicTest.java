package com.java.dictionary;

import java.util.HashMap;
import java.util.Map;
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
	 * Use multiple threads for different dictionary, StringBuffer is more safe than StringBuilder
	 */
	static StringBuffer text = new StringBuffer();

	public DicTest() {
		/**
		 * Thread pool of 6
		 */
		es = Executors.newFixedThreadPool(6);

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
	 * Translation
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
		BDtext.setText(str);
		BDtext.appendText("\n\n————百度翻译");

	}

	private void setDataBY(String str) {
		String[] strs = str.split("     ");
		BYtext.setText(strs[0] + "\n");
		for (int i = 1; i < strs.length; i++)
			BYtext.appendText(strs[i] + "\n");
		BYtext.appendText("\n————必应翻译");
	}

	private void setDataYD(String str) {
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
