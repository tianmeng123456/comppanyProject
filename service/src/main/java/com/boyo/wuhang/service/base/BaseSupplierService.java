package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseSupplierMapper;
import com.boyo.wuhang.entity.base.BaseSupplier;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseSupplierService {
    @Autowired
    private BaseSupplierMapper supplierMapper;

    public BaseSupplier getSupplierById(Long id){
    	return supplierMapper.selectByPrimaryKey(id);
    }

    public Pager getSupplierList(BaseSupplier supplier, Pager page){
        page.setList(supplierMapper.getSupplierList(supplier, page));
        page.setTotalRow(supplierMapper.getSupplierListCount(supplier));
        return page;
    }


    public boolean addSupplier(BaseSupplier record){
        if (!this.checkNo(record)){
            return false;
        }
        if (!this.checkTel(record)){
            return false;
        }
        return supplierMapper.insertSelective(record) == 1;
    }

    public boolean updateSupplier(BaseSupplier record){
        return supplierMapper.updateByPrimaryKeySelective(record) ==1;
    }

    public boolean delSupplier(Long id){
        return supplierMapper.deleteByPrimaryKey(id) ==1;
    }

    public BaseSupplier selectById(Long id){
        return supplierMapper.selectByPrimaryKey(id);
    }

    public List<BaseSupplier> selectByAreaId(Long id){
        return supplierMapper.selectByAreaId(id);
    }

    public BaseSupplier selectByTel(String tel){
        return supplierMapper.selectByTel(tel);
    }


    public BaseSupplier selectByName(String supplierName){
        return supplierMapper.selectByName(supplierName);
    }

    public boolean checkNo(BaseSupplier record){
        if (StringUtils.isBlank(record.getSupplierNo())){
            return false;
        }
        return supplierMapper.checkNo(record) ==null;
    }

    public boolean checkTel(BaseSupplier record){
        if (StringUtils.isBlank(record.getTel())){
            return false;
        }
        return supplierMapper.checkTel(record) == null;
    }

	public boolean checkName(BaseSupplier record){
		if (StringUtils.isBlank(record.getSupplierName())){
			return false;
		}
		return supplierMapper.checkName(record) == null;
	}
}
