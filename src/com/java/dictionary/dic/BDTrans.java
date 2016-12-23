package com.java.dictionary.dic;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Translation class for Baidu
 * 
 * @author cheny1231
 *
 */
public class BDTrans extends Translate {

	public static final String BASE_BD = "http://fanyi.baidu.com/v2transapi";

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
		JSONObject json = JSONObject.fromString(result);
		if (json.has("dict_result") & json.getJSONObject("dict_result")!= null) {
			JSONObject simpleMeans = json.getJSONObject("dict_result").getJSONObject("simple_means");
			JSONObject symbols = simpleMeans.getJSONArray("symbols").getJSONObject(0);
			JSONArray parts = symbols.getJSONArray("parts");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < parts.length(); i++) {
				JSONObject part = parts.getJSONObject(i);
				sb.append(part.get("part"));
				JSONArray means = part.getJSONArray("means");
				for (int j = 0; j < means.length(); j++) {
					sb.append(means.get(j));
					if (j == means.length() - 1) {
						sb.append("£»");
					} else
						sb.append("¡¢");
				}
			}
			return sb.toString();
		} else
			return "";
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASE_BD;
	}

}
