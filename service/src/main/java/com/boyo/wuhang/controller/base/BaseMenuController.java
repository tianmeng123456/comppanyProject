package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseMenu;
import com.boyo.wuhang.service.base.MenuService;
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
import java.util.Map;

@Controller
@RequestMapping("/api/base/menu")
public class BaseMenuController {
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getMenuList(@RequestBody BaseMenu menu) {
		List<Map<String, Object>> res = menuService.getMenuList(menu);
		return JsonBuilder.build(0, "", res);
	}

	@RequestMapping(value = "tree", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getSystemMenuTree(@RequestBody String system) {
		if (system.isEmpty()) {
			return JsonBuilder.build(1, "菜单所属系统为空", null);
		}
		List<Map<String, Object>> tree = menuService.getMenuTree(system);
		return JsonBuilder.build(0, "", tree);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertMenu(@RequestBody BaseMenu menu) {
		if (StringUtils.isBlank(menu.getMenuCode())) {
			return JsonBuilder.build(1, "菜单编码为空", null);
		}
		if (StringUtils.isBlank(menu.getMenuName())) {
			return JsonBuilder.build(1, "菜单名称为空", null);
		}
		if (!menuService.checkCode(menu)) {
			return JsonBuilder.build(1, "菜单编码重复", null);
		}
		if (menu.getParentId() != null) {
			BaseMenu parent = menuService.getMenuById(menu.getParentId());
			if (parent == null) {
				return JsonBuilder.build(1, "父菜单不存在", null);
			}
			menu.setDepth(parent.getDepth() + 1);
			menu.setSystem(parent.getSystem());
		} else {
			menu.setDepth(1);
		}
		if (StringUtils.isBlank(menu.getSystem())) {
			return JsonBuilder.build(1, "菜单所属系统为空", null);
		}
		menu.setId(null);
		menu.setMark(0);
		if (menuService.insertMenu(menu)) {
			return JsonBuilder.build(0, "创建菜单成功", menu);
		} else {
			return JsonBuilder.build(1, "创建菜单失败", null);
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateMenu(@RequestBody BaseMenu menu) {
		BaseMenu baseMenu = menuService.getMenuById(menu.getId());
		if (baseMenu == null) {
			return JsonBuilder.build(1, "修改的菜单不存在", null);
		}
		if (StringUtils.isNotBlank(menu.getMenuCode())) {
			if (!menuService.checkCode(menu)) {
				return JsonBuilder.build(1, "修改菜单编号已重复", null);
			}
		}
		if (menu.getParentId() != null && !menu.getParentId().equals(baseMenu.getParentId())) {
			//修改父菜单
			BaseMenu parent = menuService.getMenuById(menu.getParentId());
			if (parent == null) {
				return JsonBuilder.build(1, "修改的父菜单不存在", null);
			}
			List<BaseMenu> childs = menuService.getMenuChild(menu.getId());
			for (BaseMenu child : childs) {
				if (child.getId().equals(menu.getParentId())) {
					return JsonBuilder.build(1, "不能将上级菜单修改为自己的子菜单", null);
				}
			}
			menu.setDepth(parent.getDepth() + 1);
			menu.setSystem(parent.getSystem());
		}else {
			menu.setParentId(null);
		}
		menu.setDepth(baseMenu.getDepth());
		if (menuService.updateMenu(menu)) {
			return JsonBuilder.build(0, "修改菜单成功", menu);
		} else {
			return JsonBuilder.build(1, "修改菜单失败", null);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteMenu(@RequestBody JSONObject jsonObject) {
		Integer id = null;
		if (jsonObject.get("id") != null){
			id = Integer.parseInt(jsonObject.get("id").toString());
		}
		BaseMenu menu = menuService.getMenuById(id);
		if (menu == null) {
			return JsonBuilder.build(1, "要删除的菜单不存在", null);
		}
		List<BaseMenu> childs = menuService.getMenuChild(id);
		if (childs.size() > 1) {
			return JsonBuilder.build(1, "请先删除子菜单", null);
		}
		if (menuService.deleteMenu(menu)) {
			return JsonBuilder.build(0, "删除菜单成功", null);
		} else {
			return JsonBuilder.build(1, "删除菜单失败", null);
		}
	}

}
