package com.hardware.business.service.impl;

import com.hardware.business.model.Access;
import com.hardware.business.model.Menu;
import com.hardware.business.service.MenuService;
import org.iframework.support.domain.enums.RecordStatus;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统菜单服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceSupport<Menu, String> implements MenuService {

	@Resource(name = "menuDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("menuDaoImpl") BaseHibernateDaoSupport<Menu, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public List<Menu> getUserMenuList(List<Access> accesses) {
		if (accesses.size()<1){
			return null;
		}
		StringBuffer hql = new StringBuffer();
		hql.append("from Menu c where c.status='" + RecordStatus.AVAILABLE.toString() + "' ");
		hql.append(" and (");
		for (int i = 0; i < accesses.size(); i++) {
			hql.append(" c.id='" + accesses.get(i).getMenuId() + "' ");
			if (i < accesses.size() - 1) {
				hql.append(" or ");
			}
		}
		hql.append(") order by c.sort asc");
		List<Menu> lm = this.queryHQL(hql.toString(), null);
		return lm;
	}

}
