package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseGoods;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseGoods record);

    int insertSelective(BaseGoods record);

    BaseGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseGoods record);

    int updateByPrimaryKey(BaseGoods record);

    List<BaseGoods> getGoodsList(@Param("goods") BaseGoods goods,
                                 @Param("page") Pager page);

    int getGoodsListCount(@Param("goods") BaseGoods goods);


    BaseGoods checkNo(BaseGoods goods);

    BaseGoods selectByEntity(BaseGoods goods);
}