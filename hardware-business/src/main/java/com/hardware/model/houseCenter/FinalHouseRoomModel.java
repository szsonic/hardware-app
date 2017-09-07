package com.hardware.model.houseCenter;

import java.io.Serializable;

/**
 * 房源房间数据
 * @author zhouyu
 * @date 2017-4-10
 *
 */

public class FinalHouseRoomModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 282217239127692299L;
	
	private String rowKey;
	/**
	 * 房间id
	 */
	public String roomId;	
	/**
	 * 房源id
	 */
	public String houseId;	
	/**
	 * 房间名称
	 */
	public String roomName;
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}	
	
}
