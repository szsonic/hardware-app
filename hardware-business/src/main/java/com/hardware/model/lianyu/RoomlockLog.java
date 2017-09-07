package com.hardware.model.lianyu;
//联寓门锁操作日志
public class RoomlockLog {

	private String deviceId;
	private String deviceLocation;
	private String operaTypeName;
	private String operatorName;
	private String result;
	private String errorReason;
	private String createTime;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceLocation() {
		return deviceLocation;
	}
	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}
	public String getOperaTypeName() {
		return operaTypeName;
	}
	public void setOperaTypeName(String operaTypeName) {
		this.operaTypeName = operaTypeName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
