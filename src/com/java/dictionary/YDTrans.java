package com.java.dictionary;


import net.sf.json.JSONObject;

/**
 * �е�������
 * @author 
 *
 */
public class YDTrans extends Translate {
	
	/**
	 * ����ģʽ
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
		// TODO Auto-generated method stub
		return (String) new JSONObject(result).getJSONObject("basic").getJSONArray("explains").get(0);
	}

	@Override
	protected String getBaseUrl() {
		// TODO Auto-generated method stub
		return BASEYD;
	}

}
