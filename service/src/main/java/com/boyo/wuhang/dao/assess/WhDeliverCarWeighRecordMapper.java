package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.assess.WhDeliverCarWeighRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface WhDeliverCarWeighRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhDeliverCarWeighRecord record);

    int insertSelective(WhDeliverCarWeighRecord record);

    WhDeliverCarWeighRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhDeliverCarWeighRecord record);

    int updateByPrimaryKey(WhDeliverCarWeighRecord record);

    BigDecimal getNetWeight(@Param("record") WhDeliverCarWeighRecord record);
}