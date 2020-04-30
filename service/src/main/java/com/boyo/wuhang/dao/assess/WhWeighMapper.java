package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.WhWeighForm;
import com.boyo.wuhang.entity.assess.WhWeigh;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface WhWeighMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhWeigh record);

    int insertSelective(WhWeigh record);

    WhWeigh selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhWeigh record);

    int updateByPrimaryKey(WhWeigh record);

    WhWeigh checkNo(@Param("weighNo")String weighNo);

    int getTodayCount();

    WhWeigh getWeighByDeliverId(@Param("deliverId")Long deliverId);

    List<Map<String,Object>> getWeighList(@Param("record") WhWeighForm record,
                                          @Param("page")Pager page);

    int getWeighListCount(@Param("record")WhWeighForm record);
}