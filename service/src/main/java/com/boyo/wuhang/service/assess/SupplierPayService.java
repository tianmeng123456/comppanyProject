package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.dao.assess.SupplierPayMapper;
import com.boyo.wuhang.entity.SupplierPayForm;
import com.boyo.wuhang.entity.assess.SupplierPay;
import com.boyo.wuhang.ultity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class SupplierPayService {
	@Autowired
	private SupplierPayMapper supplierPayMapper;

	public SupplierPay getSupplierPay(Long id){
		return supplierPayMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public boolean insertSupplierPay(List<SupplierPay> list){
		if (list!=null){
			for (SupplierPay item : list){
				if (supplierPayMapper.insertSelective(item)==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}
		return true;
	}

	public boolean updateSupplierPay(SupplierPay supplierPay){
		return supplierPayMapper.updateByPrimaryKeySelective(supplierPay)==1;
	}

	public boolean deleteSupplierPay(SupplierPay supplierPay){
		return supplierPayMapper.deleteByPrimaryKey(supplierPay.getId())==1;
	}

	public Pager getSupplierPayList(SupplierPayForm payForm, Pager page){
		page.setList(supplierPayMapper.getSupplierPayList(payForm, page));
		page.setTotalRow(supplierPayMapper.getSupplierPayListCount(payForm));
		return page;
	}
}
