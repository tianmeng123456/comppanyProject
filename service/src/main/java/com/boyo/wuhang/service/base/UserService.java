package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseMenuMapper;
import com.boyo.wuhang.dao.base.BaseUserMapper;
import com.boyo.wuhang.dao.base.BaseUserMenuMapper;
import com.boyo.wuhang.entity.base.BaseMenu;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.entity.base.BaseUserMenu;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
	@Autowired
	private BaseUserMapper userMapper;
	@Autowired
	private BaseMenuMapper menuMapper;
	@Autowired
	private BaseUserMenuMapper userMenuMapper;

	public BaseUser getUserById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public BaseUser getUserByNo(String userNo) {
		return userMapper.getUserByNo(userNo);
	}

	public boolean insertUser(BaseUser user, String system) {
		if (!this.checkNo(user)) {
			return false;
		}
		if (userMapper.insertSelective(user) == 0){
			return false;
		}
		BaseMenu param = new BaseMenu();
		param.setSystem(system);
		List<Map<String,Object>> menuList = menuMapper.getMenuList(param);
		for (Map<String,Object> menu : menuList){
			BaseUserMenu userMenu = new BaseUserMenu();
			userMenu.setUserId(user.getId());
			userMenu.setMenuId(Integer.parseInt(menu.get("id").toString()));
			if (userMenuMapper.insertSelective(userMenu)==0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
		}
		return true;
	}

	public boolean updateUser(BaseUser user) {
		if (!this.checkNo(user)) {
			return false;
		}
		return userMapper.updateByPrimaryKeySelective(user) == 1;
	}

	public boolean updateUserBase(BaseUser user){
		return userMapper.updateByPrimaryKeySelective(user) == 1;
	}

	public boolean deleteUser(BaseUser user) {
		return userMapper.deleteByPrimaryKey(user.getId()) == 1;
	}

	public boolean checkNo(BaseUser user) {
		if (StringUtils.isBlank(user.getUserNo())) {
			return false;
		}
		return userMapper.checkNo(user) == null;
	}

	public Pager getUserList(BaseUser user, String targetSystem, Pager page) {
		page.setList(userMapper.getUserList(user, targetSystem, page));
		page.setTotalRow(userMapper.getUserListCount(user, targetSystem));
		return page;
	}
}
