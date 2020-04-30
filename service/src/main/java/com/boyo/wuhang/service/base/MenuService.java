package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseMenuMapper;
import com.boyo.wuhang.dao.base.BaseUserMapper;
import com.boyo.wuhang.dao.base.BaseUserMenuMapper;
import com.boyo.wuhang.entity.base.BaseMenu;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.entity.base.BaseUserMenu;
import com.boyo.wuhang.ultity.TreeBulider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuService {
	@Autowired
	private BaseMenuMapper menuMapper;
	@Autowired
	private BaseUserMapper userMapper;
	@Autowired
	private BaseUserMenuMapper userMenuMapper;

	public BaseMenu getMenuById(Integer id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public boolean insertMenu(BaseMenu menu) {
		if (!this.checkCode(menu)) {
			return false;
		}
		if (menuMapper.insertSelective(menu) == 0){
			return false;
		}
		List<BaseUser> userList = userMapper.getUserListAll(menu.getSystem());
		for (BaseUser user : userList){
			BaseUserMenu userMenu = new BaseUserMenu();
			userMenu.setUserId(user.getId());
			userMenu.setMenuId(menu.getId());
			if (userMenuMapper.insertSelective(userMenu)==0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
		}
		return true;
	}

	public boolean updateMenu(BaseMenu menu) {
		if (menu.getParentId() != null) {
			if (!this.updateMenuDepth(menu)) {
				return false;
			}
		}
		return menuMapper.updateByPrimaryKeySelective(menu) == 1;
	}

	public boolean deleteMenu(BaseMenu menu) {
		List<BaseMenu> childMenu = menuMapper.getMenuChild(menu.getId());
		if (childMenu.size() == 1){
			userMenuMapper.deleteByMenuId(menu.getId());
			return menuMapper.deleteByPrimaryKey(menu.getId()) == 1;
		}else {
			childMenu.sort(new Comparator<BaseMenu>() {
				@Override
				public int compare(BaseMenu o1, BaseMenu o2) {
					return o1.getDepth() < o2.getDepth() ? 1 : -1;
				}
			});
			for (BaseMenu item : childMenu){
				userMenuMapper.deleteByMenuId(item.getId());
				if (menuMapper.deleteByPrimaryKey(item.getId())==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
			return true;
		}
	}

	public boolean checkCode(BaseMenu menu) {
		if (StringUtils.isBlank(menu.getMenuCode())) {
			return false;
		}
		return menuMapper.checkCode(menu) == null;
	}

	public List<BaseMenu> getMenuChild(Integer id) {
		return menuMapper.getMenuChild(id);
	}

	private boolean updateMenuDepth(BaseMenu menu) {
		Integer oldDepth = menuMapper.selectByPrimaryKey(menu.getId()).getDepth();
		Integer newDepth = menu.getDepth();
		Integer diff = oldDepth - newDepth;
		if (diff != 0) {
			List<BaseMenu> childs = menuMapper.getMenuChild(menu.getId());
			for (BaseMenu child : childs) {
				child.setDepth(child.getDepth() + diff);
				if (menuMapper.updateByPrimaryKeySelective(child) == 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}
		return true;
	}

	public List<Map<String, Object>> getMenuList(BaseMenu menu) {
		return menuMapper.getMenuList(menu);
	}

	public List<Map<String, Object>> getMenuTree(String system) {
		BaseMenu menu = new BaseMenu();
		menu.setSystem(system);
		List<Map<String, Object>> result = menuMapper.getMenuList(null);
		TreeBulider.Tree("id", "parentId", "depth", "item", result, null);
		return result;
	}
}
