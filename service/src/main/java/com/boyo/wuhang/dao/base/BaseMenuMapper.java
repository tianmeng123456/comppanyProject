package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseMenuMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BaseMenu record);

	int insertSelective(BaseMenu record);

	BaseMenu selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BaseMenu record);

	int updateByPrimaryKey(BaseMenu record);

	List<Map<String, Object>> getMenuList(BaseMenu record);

	BaseMenu checkCode(BaseMenu record);

	List<BaseMenu> getMenuChild(@Param("id") Integer id);
}