package com.hardware.business.event;

import com.hardware.business.model.HardwareOnlineLog;
import org.springframework.context.ApplicationEvent;

/**
 * 硬件在线离线事件
 */
public class HardWareNerworkEvent extends ApplicationEvent {
	private HardwareOnlineLog onlineLog;

	public HardWareNerworkEvent(Object source, HardwareOnlineLog onlineLog) {
		super(source);
		this.onlineLog = onlineLog;
	}

	public HardwareOnlineLog getOnlineLog() {
		return onlineLog;
	}

	public void setOnlineLog(HardwareOnlineLog onlineLog) {
		this.onlineLog = onlineLog;
	}
}
