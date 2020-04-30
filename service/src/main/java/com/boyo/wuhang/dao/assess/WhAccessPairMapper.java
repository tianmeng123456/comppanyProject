package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.assess.WhAccessPair;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhAccessPairMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhAccessPair record);

    int insertSelective(WhAccessPair record);

    WhAccessPair selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhAccessPair record);

    int updateByPrimaryKey(WhAccessPair record);

    WhAccessPair getPairByTwoId(WhAccessPair record);

    WhAccessPair getPairByEmployeeId(@Param("employeeId")Long employeeId);

    List<WhAccessPair> getPairList();
}