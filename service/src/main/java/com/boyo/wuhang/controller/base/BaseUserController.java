package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseRole;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.base.RoleService;
import com.boyo.wuhang.service.base.UserService;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/base/user")
public class BaseUserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getUserList(@RequestBody JSONObject jsonObject) {
		JSONObject userJson = JSONObject.fromObject(jsonObject.get("user"));
		BaseUser user = (BaseUser) JSONObject.toBean(userJson, BaseUser.class);
		String targetSystem = jsonObject.getString("targetSystem");
		Pager page = new Pager();
		page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
		page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
		page = userService.getUserList(user, targetSystem, page);
		return JsonBuilder.build(0, "", page);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertUser(@RequestBody BaseUser user) {
		if (StringUtils.isBlank(user.getUserNo())) {
			return JsonBuilder.build(1, "用户名为空", null);
		}
		if (!userService.checkNo(user)) {
			return JsonBuilder.build(1, "用户名重复", null);
		}
		if (StringUtils.isBlank(user.getUserPassword())) {
			return JsonBuilder.build(1, "用户密码为空", null);
		}
		BaseRole role = roleService.getRoleById(user.getRoleId());
		if (role == null) {
			return JsonBuilder.build(1, "指定的用户角色不存在", null);
		}
		if (role.getReferTable() != null){
			return JsonBuilder.build(1, "不能指定系统自带角色", null);
		}
		if (user.getUserStatus() == null) {
			user.setUserStatus(0);
		}
		String password = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
		user.setUserPassword(password);
		user.setLastLoginTime(null);
		user.setLoginIP(null);

		if (userService.insertUser(user, role.getTargetSystem())) {
			return JsonBuilder.build(0, "创建用户成功", user);
		} else {
			return JsonBuilder.build(1, "创建用户失败", null);
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateUser(@RequestBody BaseUser user) {
		BaseUser t_user = userService.getUserById(user.getId());
		if (t_user == null){
			return JsonBuilder.build(1,"修改的用户不存在",null);
		}
		if (StringUtils.isBlank(user.getUserNo())){
			if (!userService.checkNo(user)){
				return JsonBuilder.build(1,"修改的用户名已重复",null);
			}
		}
		user.setRoleId(null);
		if (t_user.getId() == 1){
			user.setUserNo(null);
			user.setUserName(null);
		}
		if (StringUtils.isNotBlank(user.getUserPassword())) {
			String password = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
			user.setUserPassword(password);
		}
		user.setLastLoginTime(null);
		user.setLoginIP(null);
		if (userService.updateUser(user)){
			return JsonBuilder.build(0, "更新用户成功", null);
		} else {
			return JsonBuilder.build(1, "更新用户失败", null);
		}
	}
}
