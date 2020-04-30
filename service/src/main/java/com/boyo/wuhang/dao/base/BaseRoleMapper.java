package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseRoleMapper {
	int deleteByPrimaryKey(Long id);

	int insert(BaseRole record);

	int insertSelective(BaseRole record);

	BaseRole selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(BaseRole record);

	int updateByPrimaryKey(BaseRole record);

	List<BaseRole> getRoleList(BaseRole record);

	BaseRole checkName(BaseRole record);

	BaseRole getRoleByReferTable(@Param("referTable")String referTable);
}