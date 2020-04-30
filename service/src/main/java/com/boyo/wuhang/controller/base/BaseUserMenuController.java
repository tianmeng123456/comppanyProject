package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.entity.base.BaseUserMenu;
import com.boyo.wuhang.service.base.UserMenuService;
import com.boyo.wuhang.service.base.UserService;
import com.boyo.wuhang.ultity.JsonBuilder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class BaseUserMenuController {
	@Autowired
	private UserMenuService userMenuService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/api/menu/tree",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getUserMenuTree(){
		BaseUser user = LoginInterceptor.user.get();
		List<Map<String,Object>> tree = userMenuService.getBindMenu(user.getId());
		return JsonBuilder.build(0,"",tree);
	}

	@RequestMapping(value = "/api/base/user_menu/tree",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getUserMenuTree(@RequestBody JSONObject jsonObject){
		Long userId = jsonObject.getLong("userId");
		BaseUser user = userService.getUserById(userId);
		if (user == null){
			return JsonBuilder.build(1,"用户不存在",null);
		}
		List<Map<String,Object>> tree = userMenuService.getUserMenuTree(userId);
		return JsonBuilder.build(0,"",tree);
	}

	//添加用户和菜单后自动添加用户菜单权限记录（分多系统后也需注意）

	//管理员可以看到用户的所有菜单（包括未绑定的树结构）
	@RequestMapping(value = "/api/base/user_menu/edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editUserMenu(@RequestBody BaseUserMenu userMenu){
		BaseUserMenu t_userMenu = userMenuService.getUserMenuById(userMenu.getId());
		if (t_userMenu == null){
			return JsonBuilder.build(1,"用户菜单权限不存在",null);
		}
		BaseUser user = userService.getUserById(userMenu.getUserId());
		if (user.getId() == 1){
			return JsonBuilder.build(1,"不能修改管理员的权限",null);
		}
		userMenu.setUserId(null);
		userMenu.setMenuId(null);
		if (userMenuService.updateUserMenu(userMenu)){
			return JsonBuilder.build(0, "修改菜单权限成功", null);
		} else {
			return JsonBuilder.build(1, "修改菜单权限失败", null);
		}
	}
}
