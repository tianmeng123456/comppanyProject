package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseDept;
import com.boyo.wuhang.service.base.DepartmentService;
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
@RequestMapping("/api/base/department")
public class BaseDeptController {
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "tree", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDepartmentTree() {
		List<Map<String, Object>> res = departmentService.getDeptTree();
		return JsonBuilder.build(0, "", res);
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDepartmentList(@RequestBody BaseDept dept) {
		List<Map<String, Object>> res = departmentService.getDeptList(dept);
		return JsonBuilder.build(0, "", res);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertDepartment(@RequestBody BaseDept dept) {
		if (StringUtils.isBlank(dept.getDeptNo())) {
			return JsonBuilder.build(1, "部门编号为空", null);
		}
		if (StringUtils.isBlank(dept.getDeptName())) {
			return JsonBuilder.build(1, "部门名称为空", null);
		}
		if (!departmentService.checkNo(dept)) {
			return JsonBuilder.build(1, "部门编号重复", null);
		}
		if (dept.getParentId() != null) {
			BaseDept parent = departmentService.getDepartmentById(dept.getParentId());
			if (parent == null) {
				return JsonBuilder.build(1, "上级部门不存在", null);
			}
			dept.setDeptLevel(parent.getDeptLevel() + 1);
		} else {
			dept.setDeptLevel(1);
		}
		dept.setId(null);
		dept.setFlag(0);
		dept.setMark(0);
		if (departmentService.insertDepartment(dept)) {
			return JsonBuilder.build(0, "创建部门成功", dept);
		} else {
			return JsonBuilder.build(1, "创建部门失败", null);
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDepartment(@RequestBody BaseDept dept) {
		BaseDept baseDept = departmentService.getDepartmentById(dept.getId());
		if (baseDept == null) {
			return JsonBuilder.build(1, "修改的部门不存在", null);
		}
		if (StringUtils.isNotBlank(dept.getDeptNo())) {
			if (!departmentService.checkNo(dept)) {
				return JsonBuilder.build(1, "修改部门编号已重复", null);
			}
		}
		if (dept.getParentId() != null && !dept.getParentId().equals(baseDept.getParentId())) {
			//修改父部门
			BaseDept parent = departmentService.getDepartmentById(dept.getParentId());
			if (parent == null) {
				return JsonBuilder.build(1, "修改的上级部门不存在", null);
			}
			List<BaseDept> child = departmentService.getDeptChild(dept.getId());
			for (BaseDept childDept : child) {
				if (childDept.getId().equals(dept.getParentId())) {
					return JsonBuilder.build(1, "不能将上级部门修改为自己的子部门", null);
				}
			}
			dept.setDeptLevel(parent.getDeptLevel() + 1);
		}else {
			dept.setParentId(null);
		}
		dept.setFlag(null);
		dept.setMark(null);
		if (departmentService.updateDepartment(dept)) {
			return JsonBuilder.build(0, "修改部门成功", dept);
		} else {
			return JsonBuilder.build(1, "修改部门失败", null);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteDepartment(@RequestBody JSONObject jsonObject) {
		Long id = Long.parseLong(jsonObject.get("id").toString());
		Integer r = Integer.parseInt(jsonObject.get("r").toString());
		BaseDept baseDept = departmentService.getDepartmentById(id);
		if (baseDept == null) {
			return JsonBuilder.build(1, "要删除的部门不存在", null);
		}
		boolean param = (r == 0);
		if (departmentService.deleteDepartment(baseDept, param)) {
			return JsonBuilder.build(0, "删除部门成功", null);
		} else {
			return JsonBuilder.build(1, "删除部门失败", null);
		}
	}
}
