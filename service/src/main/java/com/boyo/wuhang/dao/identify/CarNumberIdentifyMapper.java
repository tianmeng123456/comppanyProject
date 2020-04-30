package com.boyo.wuhang.dao.identify;

import com.boyo.wuhang.entity.identify.CarNumberIdentify;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarNumberIdentifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarNumberIdentify record);

    int insertSelective(CarNumberIdentify record);

    CarNumberIdentify selectByPrimaryKey(Long id);

    CarNumberIdentify checkNo(CarNumberIdentify identify);

    int updateByPrimaryKeySelective(CarNumberIdentify record);

    int updateByPrimaryKey(CarNumberIdentify record);

    List<CarNumberIdentify> getIdentifyList(@Param("enty") CarNumberIdentify enty,
                                            Pager page);

    int getIdentifyListCount(@Param("enty") CarNumberIdentify enty);
}