package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseSupplier;
import com.boyo.wuhang.entity.base.BaseSupplierBank;
import com.boyo.wuhang.service.base.BaseSupplierBankService;
import com.boyo.wuhang.service.base.BaseSupplierService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/base/supplier/bank")
public class BaseSupplierBankController {
    @Autowired
    private BaseSupplierBankService bankService;
    @Autowired
    private BaseSupplierService supplierService;

    @PostMapping("list")
    public JSONObject getSupplierBankList(@RequestBody JSONObject jsonObject){
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        BaseSupplierBank bank = JSONTool.getObject(jsonObject.toString(), BaseSupplierBank.class);
        page = bankService.getSuppllierBankList(bank,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("add")
    public JSONObject addSupplierBank(@RequestBody BaseSupplierBank bank){
        if (StringUtils.isBlank(bank.getBank())){
            return JsonBuilder.build(1,"银行为空",null);
        }
        if (StringUtils.isBlank(bank.getBankAccount())){
            return JsonBuilder.build(1,"开户账户为空",null);
        }
        if (bank.getSupplierId() == null){
            return JsonBuilder.build(1,"供应商为空",null);
        }
        BaseSupplier supplier = supplierService.selectById(bank.getSupplierId());
        if (supplier == null){
            return JsonBuilder.build(1,"供应商不存在",null);
        }
        if (bankService.addSupplierBank(bank)){
            return JsonBuilder.build(0,"添加供应商银行账户成功",bank);
        }else {
            return JsonBuilder.build(1,"添加供应商银行账户失败",null);
        }
    }

    @PostMapping("update")
    public JSONObject upSupplierBank(@RequestBody BaseSupplierBank bank){
        BaseSupplierBank supplierBank = bankService.selectById(bank.getId());
        if (supplierBank == null){
            return JsonBuilder.build(1,"账户不存在",null);
        }
        if (bank.getSupplierId() != null){
            BaseSupplier supplier = supplierService.selectById(bank.getSupplierId());
            if (supplier == null){
                return JsonBuilder.build(1,"供应商不存在",null);
            }
        }
        if (bankService.upSupplierBank(bank)){
            return JsonBuilder.build(0,"修改供应商银行账户成功",bank);
        }else{
            return JsonBuilder.build(1,"修改供应商银行账户失败",null);
        }
    }

    @PostMapping("del")
    public JSONObject delSupplierBank(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        BaseSupplierBank bank = bankService.selectById(id);
        if (bank == null){
            return JsonBuilder.build(1,"删除的银行账户不存在",null);
        }
        if (bankService.delSupplierBank(id)){
            return JsonBuilder.build(0,"删除供应商银行账户成功",null);
        }else{
            return JsonBuilder.build(1,"删除供应商银行账户失败",null);
        }
    }
}
