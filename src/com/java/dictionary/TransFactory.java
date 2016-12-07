package com.java.dictionary;

public class TransFactory {
	
	public static Translate getTranslate(int Type){
		Translate tl = null;
		switch (Type) {
		case Translate.YD:
			tl = YDTrans.getInstance();
			break;
		case Translate.BD:
			tl = BDTrans.getInstance();
			break;
		case Translate.BY:
			tl = BYTrans.getInstance();
			break;
		default:
			break;
		}
		return tl;
	}

}
