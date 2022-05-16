package com.ptls.models;

public class LearnersLicenseApplication {
	private int appNum;
	private String applicationStatus;
	private String licenseType;
	private String addressFileName;
	private String dobFileName;
	private String signatureFileName;
	private String healthStatus;
	private String croStatus;
	private String notifiedStatus;
	public int getAppNum() {
		return appNum;
	}
	public void setAppNum(int appNum) {
		this.appNum = appNum;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public String getAddressFileName() {
		return addressFileName;
	}
	public void setAddressFileName(String addressFileName) {
		this.addressFileName = addressFileName;
	}
	public String getDobFileName() {
		return dobFileName;
	}
	public void setDobFileName(String dobFileName) {
		this.dobFileName = dobFileName;
	}
	public String getSignatureFileName() {
		return signatureFileName;
	}
	public void setSignatureFileName(String signatureFileName) {
		this.signatureFileName = signatureFileName;
	}
	public String getHealthStatus() {
		return healthStatus;
	}
	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}
	public String getCroStatus() {
		return croStatus;
	}
	public void setCroStatus(String croStatus) {
		this.croStatus = croStatus;
	}
	public String getNotifiedStatus() {
		return notifiedStatus;
	}
	public void setNotifiedStatus(String notifiedStatus) {
		this.notifiedStatus = notifiedStatus;
	}
	@Override
	public String toString() {
		return "LearnersLicenseApplication [appNum=" + appNum + ", applicationStatus=" + applicationStatus
				+ ", licenseType=" + licenseType + ", addressFileName=" + addressFileName + ", dobFileName="
				+ dobFileName + ", signatureFileName=" + signatureFileName + ", healthStatus=" + healthStatus
				+ ", croStatus=" + croStatus + ", notifiedStatus=" + notifiedStatus + "]";
	}
	
	
}
