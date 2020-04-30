package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseRoleMapper;
import com.boyo.wuhang.entity.base.BaseRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
	@Autowired
	private BaseRoleMapper roleMapper;

	public BaseRole getRoleById(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	public boolean insertRole(BaseRole role) {
		return roleMapper.insertSelective(role) == 1;
	}

	public boolean deleteRole(BaseRole role) {
		return roleMapper.deleteByPrimaryKey(role.getId()) == 1;
	}

	public boolean checkName(BaseRole role) {
		if (StringUtils.isBlank(role.getRoleName())) {
			return false;
		}
		return roleMapper.checkName(role) == null;
	}

	public List<BaseRole> getRoleList(BaseRole role) {
		return roleMapper.getRoleList(role);
	}
}
