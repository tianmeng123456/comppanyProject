package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseDept;
import com.boyo.wuhang.entity.base.BaseEmployee;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.base.DepartmentService;
import com.boyo.wuhang.service.base.EmployeeService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/base/employee")
public class BaseEmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getEmployeeList(@RequestBody JSONObject jsonObject) {
		Pager page = new Pager();
		page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
		page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
		jsonObject.remove("pageNumber");
		jsonObject.remove("pageSize");
		BaseEmployee employee = JSONTool.getObject(jsonObject.toString(),BaseEmployee.class);
		page = employeeService.getEmployeeList(employee, page);
		return JsonBuilder.build(0, "", page);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertEmployee(@RequestBody BaseEmployee employee) {
		if (StringUtils.isBlank(employee.getEmployeeNo())) {
			return JsonBuilder.build(1, "员工编号为空", null);
		}
		if (StringUtils.isBlank(employee.getEmployeeName())) {
			return JsonBuilder.build(1, "员工姓名为空", null);
		}
		if (!employeeService.checkNo(employee)) {
			return JsonBuilder.build(1, "员工编号重复", null);
		}
		if (employee.getDepartmentId() != null) {
			BaseDept dept = departmentService.getDepartmentById(employee.getDepartmentId());
			if (dept == null) {
				return JsonBuilder.build(1, "指定的部门不存在", null);
			}
		}

		if (employeeService.insertEmployee(employee)) {
			return JsonBuilder.build(0, "创建员工成功", employee);
		} else {
			return JsonBuilder.build(1, "创建员工失败", null);
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateEmployee(@RequestBody BaseEmployee employee) {
		BaseEmployee baseEmployee = employeeService.getEmployeeById(employee.getId());
		if (baseEmployee == null) {
			return JsonBuilder.build(1, "修改的员工不存在", null);
		}
		if (StringUtils.isNotBlank(employee.getEmployeeNo())) {
			if (!employeeService.checkNo(employee)) {
				return JsonBuilder.build(1, "修改的员工编号已重复", null);
			}
		}
		if (employeeService.updateEmployee(employee)) {
			return JsonBuilder.build(0, "修改员工成功", employee);
		} else {
			return JsonBuilder.build(1, "修改员工失败", null);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteEmployee(@RequestBody JSONObject jsonObject) {
		Long id = null;
		if (jsonObject.get("id") != null){
			id = Long.parseLong(jsonObject.get("id").toString());
		}
		BaseEmployee employee = employeeService.getEmployeeById(id);
		if (employee == null) {
			return JsonBuilder.build(1, "要删除的员工不存在", null);
		}
		if (employeeService.deleteEmployee(employee)) {
			return JsonBuilder.build(0, "删除员工成功", null);
		} else {
			return JsonBuilder.build(1, "删除员工失败", null);
		}
	}

	//生成登入用户
	@RequestMapping(value = "enable_log",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject generateUser(@RequestBody JSONObject jsonObject){
		Long id = jsonObject.getLong("id");
		String password = jsonObject.getString("password");
		BaseEmployee employee = employeeService.getEmployeeById(id);
		if (employee == null) {
			return JsonBuilder.build(1, "员工不存在", null);
		}
		if (employee.getUserId() != null){
			return JsonBuilder.build(1,"该员工已可登入系统",null);
		}
		BaseUser user = employeeService.generateUser(employee, password);
		if (user != null){
			return JsonBuilder.build(0,"生成登入用户成功",user);
		}else {
			return JsonBuilder.build(1,"生成登入用户失败",null);
		}
	}
}
