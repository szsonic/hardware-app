package com.hardware.business.event;

import org.springframework.context.ApplicationEvent;

import java.util.Arrays;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class FlowAlertMailEvent extends ApplicationEvent {

	//ICCID号或后7位
	private String[] accNo;

	public FlowAlertMailEvent(Object source, String[] accNo) {
		super(source);
		this.accNo = accNo;
	}

	public String[] getAccNo() {
		return accNo;
	}

	public void setAccNo(String[] accNo) {
		this.accNo = accNo;
	}

	@Override
	public String toString() {
		return "FlowAlertMailEvent{" +
				"accNo=" + Arrays.toString(accNo) +
				'}';
	}
}
