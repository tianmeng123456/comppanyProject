package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseSupplierBankMapper;
import com.boyo.wuhang.entity.base.BaseSupplierBank;
import com.boyo.wuhang.ultity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseSupplierBankService {
    @Autowired
    private BaseSupplierBankMapper bankMapper;

    public List<BaseSupplierBank> selectBySupplierId(Long supplierId){
        return bankMapper.selectBySupplierId(supplierId);
    }

    public Pager getSuppllierBankList(BaseSupplierBank bank, Pager page){
        page.setList(bankMapper.getSupplierBankList(bank, page));
        page.setTotalRow(bankMapper.getSupplierBankListCount(bank));
        return page;
    }

    public boolean addSupplierBank(BaseSupplierBank bank){
        return bankMapper.insertSelective(bank) == 1;
    }

    public boolean upSupplierBank(BaseSupplierBank bank){
        return bankMapper.updateByPrimaryKeySelective(bank) == 1;
    }

    public boolean delSupplierBank(Long id){
        return bankMapper.deleteByPrimaryKey(id) == 1;
    }

    public BaseSupplierBank selectById(Long id){
        return bankMapper.selectByPrimaryKey(id);
    }
}
