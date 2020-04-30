package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseEmployeeMapper;
import com.boyo.wuhang.dao.base.BaseRoleMapper;
import com.boyo.wuhang.entity.base.BaseEmployee;
import com.boyo.wuhang.entity.base.BaseRole;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeService {
	@Autowired
	private BaseEmployeeMapper employeeMapper;
	@Autowired
	private BaseRoleMapper roleMapper;
	@Autowired
	private UserService userService;

	public BaseEmployee getEmployeeById(Long id) {
		return employeeMapper.selectByPrimaryKey(id);
	}

	public BaseEmployee getEmployeeByUserId(Long userId){
		return employeeMapper.getEmployeeByUserId(userId);
	}

	public BaseEmployee getEmployeeByPostId(Long postId){
		return employeeMapper.getEmployeeByPostId(postId);
	}

	public boolean insertEmployee(BaseEmployee employee) {
		if (!this.checkNo(employee)) {
			return false;
		}
		return employeeMapper.insertSelective(employee) == 1;
	}

	public boolean updateEmployee(BaseEmployee employee) {
		return employeeMapper.updateByPrimaryKeySelective(employee) == 1;
	}

	public boolean deleteEmployee(BaseEmployee employee) {
		BaseUser user = new BaseUser();
		user.setId(employee.getUserId());
		userService.deleteUser(user);
		return employeeMapper.deleteByPrimaryKey(employee.getId()) == 1;
	}

	public boolean checkNo(BaseEmployee employee) {
		if (StringUtils.isBlank(employee.getEmployeeNo())) {
			return false;
		}
		return employeeMapper.checkNo(employee) == null;
	}

	public Pager getEmployeeList(BaseEmployee employee, Pager page) {
		page.setList(employeeMapper.getEmployeeList(employee, page));
		page.setTotalRow(employeeMapper.getEmployeeListCount(employee));
		return page;
	}

	@Transactional
	public BaseUser generateUser(BaseEmployee employee, String password){
		BaseUser user = new BaseUser();
		user.setUserNo(employee.getEmployeeNo());
		BaseRole role = roleMapper.getRoleByReferTable("BaseEmployee");
		user.setRoleId(role.getId());
		user.setRoleCode(role.getRoleName());
		user.setUserName(employee.getEmployeeName());
		user.setUserPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
		if (!userService.insertUser(user,"main")){
			return null;
		}
		user.setUserPassword(null);
		BaseEmployee updated = new BaseEmployee();
		updated.setId(employee.getId());
		updated.setUserId(user.getId());
		if (employeeMapper.updateByPrimaryKeySelective(updated)==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
		return user;
	}
}
