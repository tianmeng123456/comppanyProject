package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseUserMenuMapper;
import com.boyo.wuhang.entity.base.BaseUserMenu;
import com.boyo.wuhang.ultity.TreeBulider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserMenuService {
	@Autowired
	private BaseUserMenuMapper userMenuMapper;

	public BaseUserMenu getUserMenuById(Long id){
		return userMenuMapper.selectByPrimaryKey(id);
	}

	public BaseUserMenu getUserMenu(Long userId, Long menuId){
		return userMenuMapper.selectByUnique(userId, menuId);
	}

	public boolean insertUserMenu(BaseUserMenu userMenu){
		return userMenuMapper.insertSelective(userMenu)==1;
	}

	public boolean updateUserMenu(BaseUserMenu userMenu){
		userMenu.setUserId(null);
		userMenu.setMenuId(null);
		return userMenuMapper.updateByPrimaryKeySelective(userMenu)==1;
	}

	public boolean deleteUserMenu(Long id){
		return userMenuMapper.deleteByPrimaryKey(id)==1;
	}

	public List<Map<String,Object>> getUserMenuTree(Long userId){
		List<Map<String,Object>> tree = userMenuMapper.getUserMenuList(userId);
		TreeBulider.Tree("menuId", "parentId", "depth", "item", tree, null);
		return tree;
	}

	public List<Map<String,Object>> getBindMenu(Long userId){
		List<Map<String,Object>> tree = userMenuMapper.getUserMenuTree(userId);
		TreeBulider.Tree("menuId", "parentId", "depth", "item", tree, null);
		return tree;
	}

}
