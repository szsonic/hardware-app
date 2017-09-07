package com.hardware.model.lianyu;
import java.util.List;

/*
 * 联寓平台 - 获取客房用电量响应结果
 */
public class LianYuResponseOfGetRoomElecmeter extends LianYuResponse{
	private List<RoomElecmeter> data;

	public List<RoomElecmeter> getData() {
		return data;
	}

	public void setData(List<RoomElecmeter> data) {
		this.data = data;
	}
}
