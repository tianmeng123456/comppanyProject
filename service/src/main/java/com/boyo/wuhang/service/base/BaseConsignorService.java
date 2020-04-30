package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseConsignorMapper;
import com.boyo.wuhang.entity.base.BaseConsignor;
import com.boyo.wuhang.ultity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseConsignorService {
    @Autowired
    private BaseConsignorMapper consignorMapper;

    public Pager getConsignorList(BaseConsignor consignor, Pager page){
        page.setList(consignorMapper.getConsignorList(consignor, page));
        page.setTotalRow(consignorMapper.getConsignorListCount(consignor));
        return page;
    }

    public boolean delConsignor(Long id){
        return consignorMapper.deleteByPrimaryKey(id) == 1;
    }

    public BaseConsignor selectById(Long id){
        return consignorMapper.selectByPrimaryKey(id);
    }

    public boolean updateConsignor(BaseConsignor record){
        return consignorMapper.updateByPrimaryKeySelective(record)==1;
    }

    public boolean addConsignor(BaseConsignor record){
        return consignorMapper.insertSelective(record) ==1;
    }


    public List<BaseConsignor> getConsignorBySupplierId(Long supplierId){
        return consignorMapper.selectConsignorBySupplierId(supplierId);
    }
}
