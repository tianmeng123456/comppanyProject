package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.assess.WhDeliveryDetails;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface WhDeliveryDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhDeliveryDetails record);

    int insertSelective(WhDeliveryDetails record);

    WhDeliveryDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhDeliveryDetails record);

    int updateByPrimaryKey(WhDeliveryDetails record);

    List<WhDeliveryDetails> selectByDeliverId(Long id);

    List<WhDeliveryDetails> selectByDeliverPage(@Param("detail") WhDeliveryDetails details, @Param("page") Pager page);

    int selectCountDetails(@Param("detail") WhDeliveryDetails details);

    int updateByDeliverIdSelective(WhDeliveryDetails details);

    /**
     * 根据 WhDeliveryDetails 对象进行条件查询
     * @param details 条件对象
     * @return         WhDeliveryDetails对象
     */
    WhDeliveryDetails selectByDetail(@Param("detail") WhDeliveryDetails details);

    /**
     * 根据 入场称重时间 和 送货单号 和 车编号 进行出场操作
     * @param details 实体类对象
     * @return 条数
     */
    int updateTareWeighAndPlace(@Param("detail") WhDeliveryDetails details);

    /**
     * 根据车编号和送货单号获取最新的入场称重车辆
     * @param details 实体类
     * @return 实体类
     */
    WhDeliveryDetails getNewDetail(@Param("detail") WhDeliveryDetails details);

    WhDeliveryDetails getStartDetail(@Param("deliverId") Long deliverId);

    WhDeliveryDetails getShipRank(@Param("deliverId") Long deliverId);

    /**
     * 根据送货单统计出船的净重
     * @param id  送货单
     * @return    netWeight -> 净重
     *            tareWeight -> 皮重
     *            grossWeight -> 毛重
     */
    Map<String,BigDecimal> getWeight(Long id);

    /**
     * 根据送货单号统计卸货次数
     * @param deliverId 送货单
     * @return 卸货次数
     */
    int countDetailByDeliverId(Long deliverId);


    List<WhDeliveryDetails> getDetailById(Long deliverId);

    WhDeliveryDetails selectDetailByDeliverId(String carNo,Long deliverId);
}