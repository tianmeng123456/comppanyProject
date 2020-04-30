package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseDeptMapper;
import com.boyo.wuhang.entity.base.BaseDept;
import com.boyo.wuhang.ultity.TreeBulider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DepartmentService {
	@Autowired
	private BaseDeptMapper deptMapper;

	public BaseDept getDepartmentById(Long id) {
		return deptMapper.selectByPrimaryKey(id);
	}

	public boolean insertDepartment(BaseDept dept) {
		if (!this.checkNo(dept)) {
			return false;
		}
		return deptMapper.insertSelective(dept) == 1;
	}

	public boolean updateDepartment(BaseDept dept) {
		if (dept.getParentId() != null) {
			//修改父节点后修改子节点的深度
			if (!this.updateChildLevel(dept)) {
				return false;
			}
		}
		return deptMapper.updateByPrimaryKeySelective(dept) == 1;
	}

	public boolean deleteDepartment(BaseDept dept, boolean r) {
		//r true 递归删除 false 删除单个
		if (!r) {
			if (deptMapper.deleteByPrimaryKey(dept.getId()) == 0) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
		} else {
			List<BaseDept> childDept = deptMapper.getDeptChild(dept.getId());
			childDept.sort(new Comparator<BaseDept>() {
				@Override
				public int compare(BaseDept o1, BaseDept o2) {
					return o1.getDeptLevel() < o2.getDeptLevel() ? 1 : -1;
				}
			});
			for (BaseDept item : childDept) {
				if (deptMapper.deleteByPrimaryKey(item.getId()) == 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkNo(BaseDept dept) {
		if (StringUtils.isBlank(dept.getDeptNo())) {
			return false;
		}
		return deptMapper.checkNo(dept) == null;
	}

	public List<BaseDept> getDeptChild(Long id) {
		return deptMapper.getDeptChild(id);
	}

	boolean updateChildLevel(BaseDept dept) {
		Integer oldLevel = deptMapper.selectByPrimaryKey(dept.getId()).getDeptLevel();
		Integer newLevel = dept.getDeptLevel();
		Integer diff = oldLevel - newLevel;
		if (diff != 0) {
			List<BaseDept> childs = this.getDeptChild(dept.getId());
			for (BaseDept child : childs) {
				child.setDeptLevel(child.getDeptLevel() + diff);
				if (deptMapper.updateByPrimaryKeySelective(child) == 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}
		return true;
	}

	public List<Map<String, Object>> getDeptList(BaseDept dept) {
		//返回列表
		return deptMapper.getDeptList(dept);
	}

	public List<Map<String, Object>> getDeptTree() {
		//获取部门树结构
		List<Map<String, Object>> result = deptMapper.getDeptList(null);
		TreeBulider.Tree("id", "parentId", "deptLevel", "item", result, null);
		return result;
	}
}
