package com.hardware.model.houseCenter;

import java.io.Serializable;
import java.util.List;


/**
 * 智能硬件房源数据模型
 * @author zhouyu
 * @date 2017-5-4
 *
 */
public class IntelligentHouseData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4800947075649634013L;
	/**
	  * 房源
	*/
	private FinalHouseSourceModel finalHouseSource;
	/**
	 *街道
	 */
	private StreetModel streetModel; 
	/**
	 * 小区
	 */
	private VillageModel villageModel;
	/**
	 * 楼栋uuid
	 */
	private FinalBuildingsIDModel finalBuildingsIDModel;
	/**
	 * 单元uuid
	 */
	private FinalUnitsIDModel finalUnitsIDModel;
	/**
   * 房间
   */
	private List<FinalHouseRoomModel> rooms;
	
	
	public StreetModel getStreetModel() {
		return streetModel;
	}
	public void setStreetModel(StreetModel streetModel) {
		this.streetModel = streetModel;
	}
	public VillageModel getVillageModel() {
		return villageModel;
	}
	public void setVillageModel(VillageModel villageModel) {
		this.villageModel = villageModel;
	}
	public FinalHouseSourceModel getFinalHouseSource() {
		return finalHouseSource;
	}
	public void setFinalHouseSource(FinalHouseSourceModel finalHouseSource) {
		this.finalHouseSource = finalHouseSource;
	}
	public List<FinalHouseRoomModel> getRooms() {
		return rooms;
	}
	public void setRooms(List<FinalHouseRoomModel> rooms) {
		this.rooms = rooms;
	}
	public FinalBuildingsIDModel getFinalBuildingsIDModel() {
		return finalBuildingsIDModel;
	}
	public void setFinalBuildingsIDModel(FinalBuildingsIDModel finalBuildingsIDModel) {
		this.finalBuildingsIDModel = finalBuildingsIDModel;
	}
	public FinalUnitsIDModel getFinalUnitsIDModel() {
		return finalUnitsIDModel;
	}
	public void setFinalUnitsIDModel(FinalUnitsIDModel finalUnitsIDModel) {
		this.finalUnitsIDModel = finalUnitsIDModel;
	}
	
}
