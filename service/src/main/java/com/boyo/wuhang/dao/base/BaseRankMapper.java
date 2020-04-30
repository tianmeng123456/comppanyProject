package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseRank;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseRankMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseRank record);

    int insertSelective(BaseRank record);

    BaseRank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseRank record);

    int updateByPrimaryKey(BaseRank record);

    BaseRank checkNo(@Param("wRank") BaseRank rank);

    List<BaseRank> getRankList(@Param("enty") BaseRank rank,
                               @Param("page") Pager page);

    int getRankListCount(@Param("enty") BaseRank rank);
}