package com.java.dictionary;

import net.sf.json.JSONObject;

/**
 * Translation class for Baidu
 * 
 * @author cheny1231
 *
 */
public class BDTrans extends Translate {

	public static final String BASE_BD = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	public static final String BDKEY = "20161127000032806";
	public static final String BDSECURITY = "5uMwjl5FMHh0YrKKd4LH";

	/**
	 * Single Instance
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
		try{
		return ((JSONObject) JSONObject.fromString(result).getJSONArray("trans_result").get(0)).getString("dst");
		}catch(Exception ex){
			return "Œ¥’“µΩ∏√µ•¥ ";
		}
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASE_BD;
	}

}
