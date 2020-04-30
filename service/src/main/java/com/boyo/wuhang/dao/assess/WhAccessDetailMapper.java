package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.assess.WhAccessDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhAccessDetailMapper {
    int deleteByPrimaryKey(Long ord);

    int insert(WhAccessDetail record);

    int insertSelective(WhAccessDetail record);

    WhAccessDetail selectByPrimaryKey(Long ord);

    int updateByPrimaryKeySelective(WhAccessDetail record);

    int updateByPrimaryKey(WhAccessDetail record);

    List<WhAccessDetail> getAccessDetailById(@Param("accessId")Long accessId);

    int deleteAccessDetailById(@Param("accessId")Long accessId);

    WhAccessDetail getAccessDetailByRank(@Param("rankId") Long rankId , @Param("accessId") Long accessId);
}