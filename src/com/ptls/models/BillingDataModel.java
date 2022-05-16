package com.ptls.models;

public class BillingDataModel {
	private String app_num;
	private String name_on_card;
	private String card_num;
	private int total_amount;
	
	public String getApp_num() {
		return app_num;
	}
	public void setApp_num(String app_num) {
		this.app_num = app_num;
	}
	public String getName_on_card() {
		return name_on_card;
	}
	public void setName_on_card(String name_on_card) {
		this.name_on_card = name_on_card;
	}
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	@Override
	public String toString() {
		return "BillingDataModel [app_num=" + app_num + ", name_on_card=" + name_on_card + ", card_num=" + card_num
				+ ", total_amount=" + total_amount + "]";
	}

}
