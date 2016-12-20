package com.java.dictionary;


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
	public static final String BASEYD = "http://fanyi.youdao.com/openapi.do";
	@Override
	protected String getTrans(String result) {
		try{
		if(JSONObject.fromString(result).has("basic")){
			return (String) JSONObject.fromString(result).getJSONObject("basic").getJSONArray("explains").get(0);
		}
		else
			return "未找到该单词";
		}catch(Exception ex){
			return "未找到该单词";
		}
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASEYD;
	}

}
