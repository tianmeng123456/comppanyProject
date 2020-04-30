package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseRole;
import com.boyo.wuhang.service.base.RoleService;
import com.boyo.wuhang.ultity.JsonBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/base/role")
public class BaseRoleController {
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getRoleList(@RequestBody BaseRole role) {
		List<BaseRole> list = roleService.getRoleList(role);
		return JsonBuilder.build(0, "", list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertRole(@RequestBody BaseRole role) {
		if (StringUtils.isBlank(role.getRoleName())) {
			return JsonBuilder.build(1, "角色名为空", null);
		}
		if (!roleService.checkName(role)) {
			return JsonBuilder.build(1, "角色名重复", null);
		}
		role.setIsAdmin(1);
		//todo:设置目标系统
		if (roleService.insertRole(role)) {
			return JsonBuilder.build(0, "创建角色成功", null);
		} else {
			return JsonBuilder.build(1, "创建角色失败", null);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteRole(@RequestBody Long id) {
		BaseRole role = roleService.getRoleById(id);
		if (role == null) {
			return JsonBuilder.build(1, "角色不存在", null);
		}
		if (roleService.deleteRole(role)) {
			return JsonBuilder.build(0, "删除角色成功", null);
		} else {
			return JsonBuilder.build(1, "删除角色失败", null);
		}
	}
}
