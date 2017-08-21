package com.hardware.service.sdk.fengdian;

import com.hardware.service.sdk.IAmmeter;

import java.util.Map;

/**
 * 电表的SDK接口
 * 
 * @author zhongqi
 *
 */
public interface FDAmmeter extends IAmmeter {

	/**
	 * 绑定集中器到蜂电帐号下面
	 * 
	 * @param params
	 *          绑定集中器需要的参数{"cid":"xxx","macId":"yyy"}
	 * @return 返回电表的编号
	 */
	public Map<String, String> bindTerminal(Map<String, Object> params);

	/**
	 * 绑定节点到蜂电帐号下面
	 * 
	 * @param params
	 *          绑定节点需要的参数
	 * @return 返回电表的编号
	 */
	public Map<String, String> bindNode(Map<String, Object> params);

	/**
	 * 电表退房
	 * 
	 * @param devId
	 * @return
	 */
	public String recedeRoom(String devId);

	/**
	 * 电表入住
	 * 
	 * @param devId
	 * @return
	 */
	public String stayRoom(String devId);
	
	/**
	 * 蜂电获取设备号
	 * 
	 * @param pid
	 * @return
	 */
	public String getUuid(String pid);

}
