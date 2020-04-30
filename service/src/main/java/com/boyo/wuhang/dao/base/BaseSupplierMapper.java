package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseSupplier;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseSupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseSupplier record);

    int insertSelective(BaseSupplier record);

    BaseSupplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseSupplier record);

    int updateByPrimaryKey(BaseSupplier record);

    BaseSupplier checkNo(BaseSupplier record);

    BaseSupplier checkTel(BaseSupplier record);

    BaseSupplier checkName(BaseSupplier record);

    List<BaseSupplier> selectByAreaId(Long id );

    List<BaseSupplier> getSupplierList(@Param("supplier") BaseSupplier supplier,
                                       @Param("page") Pager page);

    int getSupplierListCount(@Param("supplier") BaseSupplier supplier);

    BaseSupplier selectByTel(@Param("tel") String tel);

	BaseSupplier selectByName(@Param("supplierName") String supplierName);
}