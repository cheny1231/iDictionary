package com.java.dictionary;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Translation class for Bing
 * 
 * @author cheny1231
 *
 */
public class BYTrans extends Translate {

	public static final String BASEBY = "http://xtk.azurewebsites.net/BingDictService.aspx";

	/**
	 * Single Instance
	 */
	private static BYTrans INSTANCE = null;

	private BYTrans() {

	}

	public static BYTrans getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BYTrans();
		}
		return INSTANCE;
	}

	/**
	 * "defs": [ { "pos": "n.", "def": "欢迎；迎接；接待；接受" }, { "pos": "int.", "def":
	 * "欢迎" }, { "pos": "v.", "def": "欣然接受；迎新；（打招呼）欢迎（某人的到来）；欢迎（新来的人）" }, {
	 * "pos": "adj.", "def": "令人愉快的；受欢迎的；受款待的；（表示乐于让某人做某事）可随意" }, { "pos":
	 * "Web", "def": "欢迎光临；非法入境；爱的自由式" } ]
	 */
	@Override
	protected String getTrans(String result) {
		try{
		StringBuilder sb = new StringBuilder();
		JSONObject temp = JSONObject.fromString(result);
		System.out.println(temp);
			JSONArray ja = temp.getJSONArray("defs");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				sb.append(jo.get("pos"));
				sb.append(":");
				sb.append(jo.get("def"));
				sb.append("     ");
			}
			return sb.toString();

		}
		catch(Exception ex){
			return "未找到该单词";
		}
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASEBY;
	}

}
