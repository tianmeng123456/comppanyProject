package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseConsignor;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface BaseConsignorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseConsignor record);

    int insertSelective(BaseConsignor record);

    BaseConsignor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseConsignor record);

    int updateByPrimaryKey(BaseConsignor record);

    List<BaseConsignor> getConsignorList(@Param("consignor") BaseConsignor consignor,
                                         @Param("page") Pager page);

    int getConsignorListCount(@Param("consignor") BaseConsignor consignor);


    List<BaseConsignor> selectConsignorBySupplierId(@Param("supplierId") Long supplierId);
}