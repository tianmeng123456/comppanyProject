package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseSupplierBank;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseSupplierBankMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseSupplierBank record);

    int insertSelective(BaseSupplierBank record);

    BaseSupplierBank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseSupplierBank record);

    int updateByPrimaryKey(BaseSupplierBank record);

    List<BaseSupplierBank> selectBySupplierId(@Param("supplierId") Long supplierId);

    List<BaseSupplierBank> getSupplierBankList(@Param("bank") BaseSupplierBank bank,
                                               @Param("page") Pager page);

    int getSupplierBankListCount(@Param("bank") BaseSupplierBank bank);
}