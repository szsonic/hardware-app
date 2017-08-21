package com.hardware.business.exception;

/**
 * 操作硬件设备时的异常信息
 * @author zhongqi
 *
 */
public class HardwareSdkException extends RuntimeException {

	private static final long serialVersionUID = -5419738820354260613L;

	private Integer code;

	public HardwareSdkException() {
		super();
	}

	public HardwareSdkException(Integer code) {
		super();
		this.code = code;
	}

	public HardwareSdkException(String message, Throwable cause) {
		super(message, cause);
	}

	public HardwareSdkException(String message, Throwable cause, Integer code) {
		super(message, cause);
		this.code = code;
	}

	public HardwareSdkException(String message) {
		super(message);
	}

	public HardwareSdkException(String message, Integer code) {
		super(message);
		this.code = code;
	}

	public HardwareSdkException(Throwable cause) {
		super(cause);
	}

	public HardwareSdkException(Throwable cause, Integer code) {
		super(cause);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
