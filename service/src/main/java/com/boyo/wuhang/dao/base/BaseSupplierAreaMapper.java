package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseSupplierArea;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseSupplierAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseSupplierArea record);

    int insertSelective(BaseSupplierArea record);

    BaseSupplierArea selectByPrimaryKey(Long id);

    BaseSupplierArea checkNo(BaseSupplierArea record);

    int updateByPrimaryKeySelective(BaseSupplierArea record);

    int updateByPrimaryKey(BaseSupplierArea record);

    List<BaseSupplierArea> getSupplierAreaList(@Param("area") BaseSupplierArea area,
                                               @Param("page") Pager page);

    int getSupplierAreaListCount(@Param("area") BaseSupplierArea area);
}