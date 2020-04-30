package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseEmployee;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseEmployeeMapper {
	int deleteByPrimaryKey(Long id);

	int insert(BaseEmployee record);

	int insertSelective(BaseEmployee record);

	BaseEmployee selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(BaseEmployee record);

	int updateByPrimaryKey(BaseEmployee record);

	BaseEmployee checkNo(BaseEmployee record);

	List<Map<String, Object>> getEmployeeList(@Param("record")BaseEmployee record, @Param("page") Pager page);

	int getEmployeeListCount(BaseEmployee record);

	BaseEmployee getEmployeeByUserId(@Param("userId")Long userId);

	BaseEmployee getEmployeeByPostId(@Param("postId")Long postId);
}