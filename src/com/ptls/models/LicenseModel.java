package com.ptls.models;

import java.util.Date;

public class LicenseModel {

	private String licID;
	private String aadhar;
	private Date issueDate;
	private Date expiryDate;
	public String getLicID() {
		return licID;
	}
	public void setLicID(String licID) {
		this.licID = licID;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
}
