package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseLoadPlace;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseLoadPlaceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseLoadPlace record);

    int insertSelective(BaseLoadPlace record);

    BaseLoadPlace selectByPrimaryKey(Long id);

    BaseLoadPlace checkNo(BaseLoadPlace record);

    int updateByPrimaryKeySelective(BaseLoadPlace record);

    int updateByPrimaryKey(BaseLoadPlace record);

    List<BaseLoadPlace> getLoadPlaceList(@Param("place") BaseLoadPlace place,
                                         @Param("page") Pager page);

    int getLoadPlaceListCount(@Param("place") BaseLoadPlace place);
}