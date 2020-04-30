package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseSupplierAreaMapper;
import com.boyo.wuhang.entity.base.BaseSupplierArea;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseSupplierAreaService {
    @Autowired
    private BaseSupplierAreaMapper supplierAreaMapper;

    public Pager getSupplierAreaList(BaseSupplierArea area, Pager page){
        page.setList(supplierAreaMapper.getSupplierAreaList(area, page));
        page.setTotalRow(supplierAreaMapper.getSupplierAreaListCount(area));
        return page;
    }

    public boolean addSupplierArea(BaseSupplierArea area){
        if (!this.checkNo(area)){
            return false;
        }
        return supplierAreaMapper.insertSelective(area)==1;
    }

    public boolean upSupplierArea(BaseSupplierArea area){
        return supplierAreaMapper.updateByPrimaryKeySelective(area)==1;
    }

    public boolean delSupplierArea(Long id){
        return supplierAreaMapper.deleteByPrimaryKey(id)==1;
    }

    public BaseSupplierArea selectById(Long id){
        return supplierAreaMapper.selectByPrimaryKey(id);
    }

    public boolean checkNo(BaseSupplierArea record){
        if (StringUtils.isBlank(record.getAreaNo())){
            return false;
        }
        return supplierAreaMapper.checkNo(record) == null;
    }
}
