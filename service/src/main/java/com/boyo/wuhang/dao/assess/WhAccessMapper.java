package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.WhAccessForm;
import com.boyo.wuhang.entity.assess.WhAccess;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface WhAccessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhAccess record);

    int insertSelective(WhAccess record);

    WhAccess selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhAccess record);

    int updateByPrimaryKey(WhAccess record);

    WhAccess checkNo(@Param("accessNo")String accessNo);

    int getTodayCount();

    List<Map<String,Object>> getAccessList(@Param("record") WhAccessForm record,
                                           @Param("page")Pager page);

    int getAccessListCount(@Param("record")WhAccessForm record);

    /**
     * 获取指定送货单的单价,单价调整,杂质
     *  Impurities -> 杂质
     *  priceAdjust -> 单价调整
     *  sumPrice    -> 单价
     * @param deliverId 送货单
     */
    Map<String,BigDecimal> getPriceByDeliverId(Long deliverId);

    /**
     * 根据送货单ID查询
     * @param deliverId
     * @return
     */
    WhAccess getAccessByDeliverId(Long deliverId);
}