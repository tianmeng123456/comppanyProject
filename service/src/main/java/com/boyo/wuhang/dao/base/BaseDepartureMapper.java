package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseDeparture;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseDepartureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseDeparture record);

    int insertSelective(BaseDeparture record);

    BaseDeparture selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseDeparture record);

    int updateByPrimaryKey(BaseDeparture record);

    BaseDeparture checkNo(BaseDeparture record);

    BaseDeparture checkName(BaseDeparture record);

    List<BaseDeparture> getAllDeparture(@Param("evaluationId")Long evaluationId);

    List<BaseDeparture> getEffectDeparture();

    List<BaseDeparture> getDepartureList(@Param("departure") BaseDeparture departure);
//                                         @Param("province") String province,
//                                         @Param("city") String city,
//                                         @Param("district") String district,
//                                         @Param("page") Pager page

    int getDepartureListCount(@Param("departure") BaseDeparture departure);

	List<BaseDeparture> getNewestDeparture();

	BaseDeparture getDepartureByFlag(@Param("flag") Integer flag);
}