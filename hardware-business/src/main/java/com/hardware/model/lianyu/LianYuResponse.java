package com.hardware.model.lianyu;

/*
 * 联寓平台响应结果
 */
public class LianYuResponse {
	protected String resultCode; //接口相应结果：SUCCESS成功
	protected String resultMessage; //响应描述
	protected String subCode; //业务响应编码
	protected String subMessage; //业务响应描述
	protected String serialNumber; //开放平台流水
	protected String outSerialNumber; //第三方系统流水
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubMessage() {
		return subMessage;
	}
	public void setSubMessage(String subMessage) {
		this.subMessage = subMessage;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getOutSerialNumber() {
		return outSerialNumber;
	}
	public void setOutSerialNumber(String outSerialNumber) {
		this.outSerialNumber = outSerialNumber;
	}
	
	//用于日志输出
	public String result(){
		return new StringBuilder("resultCode:").append(resultCode)
		.append(";resultMessage:").append(resultMessage)
		.append(";subCode:").append(subCode)
		.append(";subMessage:").append(subMessage).toString();
	} 
}
