package com.ptls.models;

import java.io.File;
import java.util.Date;

public class LicenseHolderModel {
	private long aadhar;
	private String emailId;
	
	//Should be encryted
	private String password;
	
	
	//Obtained from Aadhar DB
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
	
	//Other fields on Application
	private String placeOfBirth;
	private String bloodGroup;
	private String emergencyMobNo;
	private String identificationMark;
	
	//Documents
	private File addressProof;
	private File dobDoc;
	private File signature;

	public long getAadhar() {
		return aadhar;
	}

	public void setAadhar(long aadhar) {
		this.aadhar = aadhar;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getEmergencyMobNo() {
		return emergencyMobNo;
	}

	public void setEmergencyMobNo(String emergencyMobNo) {
		this.emergencyMobNo = emergencyMobNo;
	}

	public String getIdentificationMark() {
		return identificationMark;
	}

	public void setIdentificationMark(String identificationMark) {
		this.identificationMark = identificationMark;
	}

	public File getAddressProof() {
		return addressProof;
	}

	public void setAddressProof(File addressProof) {
		this.addressProof = addressProof;
	}

	public File getDobDoc() {
		return dobDoc;
	}

	public void setDobDoc(File dobDoc) {
		this.dobDoc = dobDoc;
	}

	public File getSignature() {
		return signature;
	}

	public void setSignature(File signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "LicenseHolderModel [aadhar=" + aadhar + ", emailId=" + emailId + ", password=" + password
				+ ", full_name=" + full_name + ", gender=" + gender + ", dob=" + dob + ", address=" + address
				+ ", postal_code=" + postal_code + ", district=" + district + ", state=" + state + ", country="
				+ country + ", fathers_name=" + fathers_name + ", photo_url=" + photo_url + ", ph_n0=" + ph_n0
				+ ", age=" + age + ", placeOfBirth=" + placeOfBirth + ", bloodGroup=" + bloodGroup + ", emergencyMobNo="
				+ emergencyMobNo + ", identificationMark=" + identificationMark + "]";
	}
	
}
