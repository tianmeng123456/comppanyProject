package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.assess.WhDeliver;
import com.boyo.wuhang.entity.base.BaseConsignor;
import com.boyo.wuhang.entity.base.BaseSupplier;
import com.boyo.wuhang.entity.base.BaseSupplierBank;
import com.boyo.wuhang.service.assess.DeliverService;
import com.boyo.wuhang.service.base.BaseConsignorService;
import com.boyo.wuhang.service.base.BaseSupplierBankService;
import com.boyo.wuhang.service.base.BaseSupplierService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import com.boyo.wuhang.ultity.Validator;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/base/supplier")
public class BaseSupplierController {
    @Autowired
    private BaseSupplierService supplierService;
    @Autowired
    private BaseConsignorService consignorService;
    @Autowired
    private BaseSupplierBankService bankService;
    @Autowired
    private DeliverService deliverService;

    @PostMapping("list")
    public JSONObject getSupplierList(@RequestBody JSONObject jsonObject) {
	    Pager page = new Pager();
	    page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
	    page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
	    jsonObject.remove("pageNumber");
	    jsonObject.remove("pageSize");
	    BaseSupplier supplier = JSONTool.getObject(jsonObject.toString(),BaseSupplier.class);
        page = supplierService.getSupplierList(supplier,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("add")
    public JSONObject addSupplier(@RequestBody BaseSupplier supplier){
        if (StringUtils.isBlank(supplier.getSupplierNo())){
            return JsonBuilder.build(1,"供应商编码为空",null);
        }
        if (!supplierService.checkNo(supplier)){
            return JsonBuilder.build(1,"供应商编码重复",null);
        }
        if (StringUtils.isBlank(supplier.getSupplierName())){
	        return JsonBuilder.build(1,"供应商姓名为空",null);
        }
	    if (!supplierService.checkName(supplier)){
		    return JsonBuilder.build(1,"供应商姓名重复",null);
	    }
        if (StringUtils.isBlank(supplier.getTel())){
            return JsonBuilder.build(1,"手机号为空",null);
        }
        if (!Validator.isMobile(supplier.getTel())){
            return JsonBuilder.build(1,"手机号格式不正确",null);
        }
        //一个供应商一个手机号
        if (!supplierService.checkTel(supplier)){
            return JsonBuilder.build(1,"手机号重复",null);
        }
        if (supplierService.addSupplier(supplier)){
            return JsonBuilder.build(0,"添加供应商信息成功",supplier);
        }else {
            return JsonBuilder.build(1,"添加供应商信息失败",supplier);
        }
    }

    @PostMapping("update")
    public JSONObject updateSupplier(@RequestBody BaseSupplier supplier){
        if (supplier.getSupplierNo() != null){
            if (StringUtils.isBlank(supplier.getSupplierNo())){
                return JsonBuilder.build(1,"供应商编码为空",null);
            }
            if (!supplierService.checkNo(supplier)){
                return JsonBuilder.build(1,"供应商编码重复",null);
            }
        }
        if (supplier.getSupplierName() != null){
	        if (StringUtils.isBlank(supplier.getSupplierName())){
		        return JsonBuilder.build(1,"供应商姓名为空",null);
	        }
	        if (!supplierService.checkName(supplier)){
		        return JsonBuilder.build(1,"供应商姓名重复",null);
	        }
        }
        if (supplierService.updateSupplier(supplier)){
            return JsonBuilder.build(0,"修改供应商成功",supplier);
        }else {
            return JsonBuilder.build(1,"修改供应商失败",null);
        }
    }

    //删除  如果供应商下有货主无法删除   如要删除先删除货主再删除供应商
    @PostMapping("del")
    public JSONObject delSupplier(@RequestBody JSONObject jsonObject){
        long supplierId = Long.parseLong(jsonObject.getString("supplierId"));
        List<BaseConsignor> supplierIdList = consignorService.getConsignorBySupplierId(supplierId);
        if (supplierIdList.size() >0 ){
            return JsonBuilder.build(1,"供应商下存在货主，无法删除",null);
        }
        List<WhDeliver> deliver = deliverService.selectBySupplierId(supplierId);
        if (deliver == null){
            return JsonBuilder.build(1,"供应商下存在送货,无法删除",null);
        }
        if (supplierService.delSupplier(supplierId)){
            return JsonBuilder.build(0,"删除供应商成功",null);
        }else {
            return JsonBuilder.build(1,"删除供应商失败",null);
        }
    }

    //查询供应商下银行账户
    @PostMapping("bank")
    public JSONObject getSupplierBank(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("supplierId"));
        List<BaseSupplierBank> bankList = bankService.selectBySupplierId(id);
        return JsonBuilder.build(0,"",bankList);
    }

    @PostMapping("supplierbyarea")
    public JSONObject selectByAreaId(@RequestBody JSONObject jsonObject){
        long areaId = Long.parseLong(jsonObject.getString("areaId"));
        List<BaseSupplier> supplierList = supplierService.selectByAreaId(areaId);
        return JsonBuilder.build(0,"",supplierList);
    }

    @PostMapping("supplierbytel")
    public JSONObject selectByTel(@RequestBody JSONObject jsonObject){
        String tel = jsonObject.getString("tel");
        BaseSupplier supplier = supplierService.selectByTel(tel);
        return JsonBuilder.build(0,"",supplier);
    }
}
