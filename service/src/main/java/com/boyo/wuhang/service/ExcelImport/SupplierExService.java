package com.boyo.wuhang.service.ExcelImport;

import com.boyo.wuhang.dao.assess.SupplierPayMapper;
import com.boyo.wuhang.entity.assess.SupplierPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class SupplierExService {
    @Autowired
    private SupplierPayMapper payMapper;

    @Transactional
    public boolean insertSupplierPay(List<SupplierPay> payList) {
        if (payList != null){
            for (SupplierPay item : payList){
                if (payMapper.insertSelective(item) == 0){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }
        }
        return true;
    }
}
