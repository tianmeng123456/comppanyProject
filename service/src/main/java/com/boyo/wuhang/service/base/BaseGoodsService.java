package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseGoodsMapper;
import com.boyo.wuhang.entity.base.BaseGoods;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseGoodsService {

    @Autowired
    private BaseGoodsMapper goodsMapper;

    public Pager getGoodsList(BaseGoods goods, Pager page){
        page.setList(goodsMapper.getGoodsList(goods,page));
        page.setTotalRow(goodsMapper.getGoodsListCount(goods));
        return page;
    }

    public boolean addGoods(BaseGoods goods){
        if (!this.checkNo(goods)){
            return false;
        }
        return goodsMapper.insertSelective(goods) == 1;
    }

    public boolean upGoods(BaseGoods goods){
        return goodsMapper.updateByPrimaryKeySelective(goods) == 1;
    }

    public boolean delGoods(Long id){
        return goodsMapper.deleteByPrimaryKey(id) == 1;
    }

    public BaseGoods selectById(Long id){
        return goodsMapper.selectByPrimaryKey(id);
    }

    public boolean checkNo(BaseGoods record){
        if (StringUtils.isBlank(record.getGoodsCode())){
            return false;
        }
        return goodsMapper.checkNo(record) == null;
    }

}
