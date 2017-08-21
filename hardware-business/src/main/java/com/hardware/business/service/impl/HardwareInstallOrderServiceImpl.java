package com.hardware.business.service.impl;

import com.hardware.business.dao.AmmeterInstallOrderDao;
import com.hardware.business.dao.DoorLockInstallOrderDao;
import com.hardware.business.dao.RouterInstallOrderDao;
import com.hardware.business.model.*;
import com.hardware.business.service.HardwareInstallOrderService;
import com.utils.ValidatorUtils;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 硬件安装状态业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("hardwareInstallOrderService")
public class HardwareInstallOrderServiceImpl extends BaseServiceSupport<HardwareInstallOrder, String> implements HardwareInstallOrderService {

	@Resource(name = "ammeterInstallOrderDao")
	private AmmeterInstallOrderDao ammeterInstallOrderDao;
	@Resource(name = "doorLockInstallOrderDao")
	private DoorLockInstallOrderDao doorLockInstallOrderDao;
	@Resource(name = "routerInstallOrderDao")
	private RouterInstallOrderDao routerInstallOrderDao;

	@Resource(name = "hardwareInstallOrderDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("hardwareInstallOrderDaoImpl") BaseHibernateDaoSupport<HardwareInstallOrder, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	/*
	 * 保存硬件安装订单的时候, 同时保存相关的硬件类型订单(门锁、电表、路由 等等)
	 */
	@Override
	public String save(HardwareInstallOrder order){

		if(order.getAmmeterInstallOrder() != null
				&& ValidatorUtils.isEmpty(order.getAmmeterInstallOrder().getId())){
			ammeterInstallOrderDao.save(order.getAmmeterInstallOrder());
		}

		if(order.getDoorLockInstallOrder() != null
				&& ValidatorUtils.isEmpty(order.getDoorLockInstallOrder().getId())){
			doorLockInstallOrderDao.save(order.getDoorLockInstallOrder());
		}

		if(order.getRouterInstallOrder() != null
				&& ValidatorUtils.isEmpty(order.getRouterInstallOrder().getId())){
			routerInstallOrderDao.save(order.getRouterInstallOrder());
		}

		return super.save(order);
	}

	@Override
	public void saveHardwareInstallOrder(HardwareInstallOrder hardwareInstallOrder) {
		AmmeterInstallOrder ammeterInstallOrder = hardwareInstallOrder.getAmmeterInstallOrder();
		DoorLockInstallOrder doorLockInstallOrder = hardwareInstallOrder.getDoorLockInstallOrder();
		RouterInstallOrder routerInstallOrder = hardwareInstallOrder.getRouterInstallOrder();
		if (!ValidatorUtils.isNotEmpty(ammeterInstallOrder.getSupplierProduct().getId())){
			hardwareInstallOrder.setAmmeterInstallOrder(null);
		}else{
			List<HardwareFittings> fittings = ammeterInstallOrder.getHardwareFittings();
			Map <String,Object> result = getProductAndamount(fittings);
			ammeterInstallOrder.setSupplierProduct((SupplierProduct)result.get("supplierProduct"));
			Object amount = result.get("amount");
			ammeterInstallOrder.setAmount(Integer.parseInt(amount==null?"":amount.toString()));
			hardwareInstallOrder.setAmmeterInstallOrder(ammeterInstallOrder);
		}
		if(!ValidatorUtils.isNotEmpty(doorLockInstallOrder.getSupplierProduct().getId())){
			hardwareInstallOrder.setDoorLockInstallOrder(null);
		}else{
			List<HardwareFittings> fittings = doorLockInstallOrder.getHardwareFittings();
			Map <String,Object> result = getProductAndamount(fittings);
			doorLockInstallOrder.setSupplierProduct((SupplierProduct)result.get("supplierProduct"));
			Object amount = result.get("amount");
			doorLockInstallOrder.setAmount(Integer.parseInt(amount==null?"":amount.toString()));
			hardwareInstallOrder.setDoorLockInstallOrder(doorLockInstallOrder);
		}
		if(!ValidatorUtils.isNotEmpty(routerInstallOrder.getSupplierProduct().getId())){
			hardwareInstallOrder.setRouterInstallOrder(null);
		}else{
			List<HardwareFittings> fittings = routerInstallOrder.getHardwareFittings();
			Map <String,Object> result = getProductAndamount(fittings);
			routerInstallOrder.setSupplierProduct((SupplierProduct)result.get("supplierProduct"));
			Object amount = result.get("amount");
			routerInstallOrder.setAmount(Integer.parseInt(amount==null?"":amount.toString()));
			hardwareInstallOrder.setRouterInstallOrder(routerInstallOrder);
		}
		this.save(hardwareInstallOrder);
	}

	private Map<String ,Object> getProductAndamount(List<HardwareFittings> fittings){
		Map <String,Object> result = new HashMap<>();
		if(fittings.size()>0){
			int count = 0;
			result.put("supplierProduct",fittings.get(0).getSupplierProductDetail().getSupplierProduct());
			for(HardwareFittings fitting:fittings){
				count+=fitting.getAmount();
			}
			result.put("amount",count);
			return result;
		}else{
			return null;
		}


	}
}
