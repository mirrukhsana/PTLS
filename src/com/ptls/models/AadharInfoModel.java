package com.ptls.models;

import java.util.Date;

public class AadharInfoModel {

	private String aadhar;
	private String full_name;
	private String gender;
	private Date dob;
	private String address;
	private String postal_code;
	private String district;
	private String state;
	private String country;
	private String fathers_name;
	private String photo_url;
	private String ph_n0;
	private String age;
	private String emailAddress;
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
		System.out.println("aadhar here="+aadhar);
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFathers_name() {
		return fathers_name;
	}
	public void setFathers_name(String fathers_name) {
		this.fathers_name = fathers_name;
	}
	public String getPhoto_url() {
		return photo_url;
	}
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	public String getPh_n0() {
		return ph_n0;
	}
	public void setPh_n0(String ph_n0) {
		this.ph_n0 = ph_n0;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	@Override
	public String toString() {
		return "AadharInfoModel [aadhar=" + aadhar + ", full_name=" + full_name + ", gender=" + gender + ", dob=" + dob
				+ ", address=" + address + ", postal_code=" + postal_code + ", district=" + district + ", state="
				+ state + ", country=" + country + ", fathers_name=" + fathers_name + ", photo_url=" + photo_url
				+ ", ph_n0=" + ph_n0 + "]";
	}
	
}
