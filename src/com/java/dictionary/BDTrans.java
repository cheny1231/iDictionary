package com.java.dictionary;

import net.sf.json.JSONObject;

/**
 * 百度翻译类
 * 
 * @author
 *
 */
public class BDTrans extends Translate {

	public static final String BASE_BD = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	public static final String BDKEY = "20161127000032806";
	public static final String BDSECURITY = "5uMwjl5FMHh0YrKKd4LH";

	/**
	 * 单例模式
	 */
	private static BDTrans INSTANCE = null;

	private BDTrans() {

	}

	public static BDTrans getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BDTrans();
		}
		return INSTANCE;
	}

	@Override
	protected String getTrans(String result) {
		return ((JSONObject) new JSONObject(result).getJSONArray("trans_result").get(0)).getString("dst");
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASE_BD;
	}

}
