package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseDept;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseDeptMapper {
	int deleteByPrimaryKey(Long id);

	int insert(BaseDept record);

	int insertSelective(BaseDept record);

	BaseDept selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(BaseDept record);

	int updateByPrimaryKey(BaseDept record);

	List<Map<String, Object>> getDeptList(BaseDept record);

	BaseDept checkNo(BaseDept record);

	List<BaseDept> getDeptChild(Long id);
}