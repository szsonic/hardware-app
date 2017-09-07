package com.hardware.model.lianyu;
//门锁
public class RoomLock {
//"id":"000000000500000009","name":"2201门锁","keepOpen":false,"canBeLocked":true,"leafRoom":false,"lockModel":"01"

	private String id;  //门锁ID
	private String name; //门锁名称
	private Boolean keepOpen; //是否常开。true：常开，false：不常开
	private Boolean canBeLocked;//是否可以被锁上。true：可被锁上，false：不可被锁上
	private Boolean leafRoom;//是否为子房间。true：子房间，false：非子房间
	private String lockModel;//门锁型号，默认联永锁
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getKeepOpen() {
		return keepOpen;
	}
	public void setKeepOpen(Boolean keepOpen) {
		this.keepOpen = keepOpen;
	}
	public Boolean getCanBeLocked() {
		return canBeLocked;
	}
	public void setCanBeLocked(Boolean canBeLocked) {
		this.canBeLocked = canBeLocked;
	}
	public Boolean getLeafRoom() {
		return leafRoom;
	}
	public void setLeafRoom(Boolean leafRoom) {
		this.leafRoom = leafRoom;
	}
	public String getLockModel() {
		return lockModel;
	}
	public void setLockModel(String lockModel) {
		this.lockModel = lockModel;
	}
	
}
