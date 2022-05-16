package com.ptls.models;

public class LLApplication {

	private String aadhar;
	private String applicationNumber;
	private String applicationStatus;
	private int feePaid;
	private String typeOfLicenses;
	public String getAadhar() {
		return aadhar;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public int getFeePaid() {
		return feePaid;
	}
	public void setFeePaid(int feePaid) {
		this.feePaid = feePaid;
	}
	public String getTypeOfLicenses() {
		return typeOfLicenses;
	}
	public void setTypeOfLicenses(String typeOfLicenses) {
		this.typeOfLicenses = typeOfLicenses;
	}
	@Override
	public String toString() {
		return "LLApplication [aadhar=" + aadhar + ", applicationNumber=" + applicationNumber + ", applicationStatus="
				+ applicationStatus + ", feePaid=" + feePaid + ", typeOfLicenses=" + typeOfLicenses + "]";
	}
}
