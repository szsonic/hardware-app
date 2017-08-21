package com.hardware.business.dao;//package com.hardware.business.dao;
//
//import java.util.List;
//
//import org.apache.commons.lang.time.DateFormatUtils;
//import com.utils.ValidatorUtils;
//import org.iframework.support.domain.order.Order;
//import org.iframework.support.domain.pager.Pager;
//import com.support.BaseHibernateDaoSupport;
//import org.springframework.stereotype.Repository;
//
//import com.hardware.business.model.HouseFloor;
//
///**
// * 房源楼栋类
// * 
// * @author zhongqi
// * 
// */
//@Repository("houseFloorDao")
//public class HouseFloorDao extends BaseHibernateDaoSupport<HouseFloor, String> {
//
//	@Override
//	public List<HouseFloor> findByModel(HouseFloor model, Order order, Pager pager) {
//		model = model == null ? new HouseFloor() : model;
//		StringBuilder hql = new StringBuilder();
//		hql.append("from House c where 1=1");
//		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
//		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
//		hql.append(order != null ? order.toString() : "");
//		return this.findByQueryString(hql.toString(), model, pager);
//	}
//
//	public HouseFloor getBySign(String sign) {
//		List<HouseFloor> houseFloors = this.queryHQL("from HouseFloor a where a.status='AVAILABLE' and a.sign='" + sign + "'");
//		return houseFloors != null && houseFloors.size() > 0 ? houseFloors.get(0) : null;
//	}
//}
