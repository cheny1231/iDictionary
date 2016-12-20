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
	 * "defs": [ { "pos": "n.", "def": "��ӭ��ӭ�ӣ��Ӵ�������" }, { "pos": "int.", "def":
	 * "��ӭ" }, { "pos": "v.", "def": "��Ȼ���ܣ�ӭ�£������к�����ӭ��ĳ�˵ĵ���������ӭ���������ˣ�" }, {
	 * "pos": "adj.", "def": "�������ģ��ܻ�ӭ�ģ��ܿ���ģ�����ʾ������ĳ����ĳ�£�������" }, { "pos":
	 * "Web", "def": "��ӭ���٣��Ƿ��뾳����������ʽ" } ]
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
			return "δ�ҵ��õ���";
		}
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASEBY;
	}

}
