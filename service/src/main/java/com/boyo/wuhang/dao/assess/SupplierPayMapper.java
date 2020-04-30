package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.SupplierPayForm;
import com.boyo.wuhang.entity.assess.SupplierPay;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierPayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierPay record);

    int insertSelective(SupplierPay record);

    SupplierPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierPay record);

    int updateByPrimaryKey(SupplierPay record);

    SupplierPay selectSerialNumber(@Param("serialNumber")String serialNumber);

    List<SupplierPayForm> getSupplierPayList(@Param("pay") SupplierPayForm pay,
                                             @Param("page") Pager page);

    int getSupplierPayListCount(@Param("pay") SupplierPayForm pay);
}