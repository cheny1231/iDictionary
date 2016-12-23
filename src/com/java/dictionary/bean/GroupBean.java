package com.java.dictionary.bean;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

public class GroupBean {
	
	String id;

	TextArea textView;

	ToggleButton btnFavor;
	
	Button btnWordCard;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		if(btnFavor == null||btnWordCard == null)
			throw new NullPointerException("button can not be null");
		btnFavor.setId("LIKE_"+id);
		btnWordCard.setId("SHARE_"+id);
		System.out.println(btnFavor.getId());
	}

	public TextArea getTextView() {
		return textView;
	}

	public void setTextView(TextArea textView) {
		this.textView = textView;
	}

	public ToggleButton getBtnFavor() {
		return btnFavor;
	}

	public void setBtnFavor(ToggleButton btnFavor) {
		this.btnFavor = btnFavor;
	}

	public Button getBtnWordCard() {
		return btnWordCard;
	}

	public void setBtnWordCard(Button btnWordCard) {
		this.btnWordCard = btnWordCard;
	}
	
	
}
