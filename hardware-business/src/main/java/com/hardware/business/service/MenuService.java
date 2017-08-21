package com.hardware.business.service;

import com.hardware.business.model.Access;
import com.hardware.business.model.Menu;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 系统菜单服务接口<br>
 * 
 * @author shenpeng
 * 
 */
public interface MenuService extends IBaseServiceSupport<Menu, String> {

	/**
	 * 根据Session中获取的用户访问权限集合获取具体可操作的菜单集合
	 * 
	 * @param accesses
	 *            用户访问权限集合
	 * @return 返回用户可操作菜单集合
	 */
	public List<Menu> getUserMenuList(List<Access> accesses);
}
