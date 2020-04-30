package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseUserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(BaseUser record);

	int insertSelective(BaseUser record);

	BaseUser selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(BaseUser record);

	int updateByPrimaryKey(BaseUser record);

	BaseUser checkNo(BaseUser record);

	BaseUser getUserByNo(String userNo);

	List<BaseUser> getUserListAll(@Param("targetSystem") String targetSystem);

	List<Map<String, Object>> getUserList(@Param("user") BaseUser user,
	                                      @Param("targetSystem") String targetSystem,
	                                      @Param("page") Pager page);

	int getUserListCount(@Param("user") BaseUser user,
	                     @Param("targetSystem") String targetSystem);
}