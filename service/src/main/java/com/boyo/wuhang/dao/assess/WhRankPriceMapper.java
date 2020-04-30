package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.assess.WhRankPrice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhRankPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhRankPrice record);

    int insertSelective(WhRankPrice record);

    WhRankPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhRankPrice record);

    int updateByPrimaryKey(WhRankPrice record);

    int deleteByRankId(@Param("rankId")Long rankId);

    int deleteByDepartureId(@Param("departureId")Long departureId);

    List<WhRankPrice> getPriceByRankId(@Param("rankId")Long rankId);

    WhRankPrice getPriceByRankAndDeparture(@Param("rankId")Long rankId,
                                           @Param("departureId")Long departureId);
}