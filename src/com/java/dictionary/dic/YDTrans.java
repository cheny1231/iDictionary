package com.java.dictionary.dic;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Translation class for Youdao
 * @author cheny1231
 *
 */
public class YDTrans extends Translate {
	
	/**
	 * Single-Instance 
	 */
	private static YDTrans INSTANCE = null;
	
	private YDTrans(){
		
	}
	
	public static YDTrans getInstance(){
		if(INSTANCE == null){
			INSTANCE = new YDTrans();
		}
		return INSTANCE;
	}
	
	

	public static final String YDKEY = "1991109319";
	public static final String BASEYD = "http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=null";
	@Override
	protected String getTrans(String result) {
		System.out.println(result);
		try{
			JSONObject jo = JSONObject.fromString(result);
			if (jo.has("smartResult")) {
				JSONArray ja = jo.getJSONObject("smartResult").getJSONArray("entries");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < ja.length(); i++) {
					sb.append(ja.get(i));
					if (!"".equals(ja.get(i)) && i != ja.length() - 1)
						sb.append(";");
				}
				return sb.toString();
			}else
				return "";
		}catch(Exception ex){
			return "Î´ÕÒµ½¸Ãµ¥´Ê";
		}
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASEYD;
	}

}
