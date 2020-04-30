package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseUserMenu;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseUserMenuMapper {
	int deleteByPrimaryKey(Long id);

	int insert(BaseUserMenu record);

	int insertSelective(BaseUserMenu record);

	BaseUserMenu selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(BaseUserMenu record);

	int updateByPrimaryKey(BaseUserMenu record);

	BaseUserMenu selectByUnique(@Param("userId")Long userId, @Param("menuId")Long menuId);

	int deleteByUserId(@Param("userId")Long userId);

	int deleteByMenuId(@Param("menuId")Integer menuId);

	List<Map<String,Object>> getUserMenuList(@Param("userId")Long userId);

	List<Map<String,Object>> getUserMenuTree(@Param("userId")Long userId);
}