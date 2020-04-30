package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseSupplierArea;
import com.boyo.wuhang.service.base.BaseSupplierAreaService;
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
@RequestMapping("/api/base/supplier/area")
public class BaseSupplierAreaController {
    @Autowired
    private BaseSupplierAreaService supplierAreaService;

    @PostMapping("list")
    public JSONObject getSupplierAreaList(@RequestBody JSONObject jsonObject){
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        BaseSupplierArea supplierArea = JSONTool.getObject(jsonObject.toString(), BaseSupplierArea.class);
        page = supplierAreaService.getSupplierAreaList(supplierArea,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("add")
    public JSONObject addSupplierArea(@RequestBody BaseSupplierArea supplierArea){
        if (!supplierAreaService.checkNo(supplierArea)){
            return JsonBuilder.build(0,"区域编号重复",null);
        }
        if (supplierAreaService.addSupplierArea(supplierArea)){
            return JsonBuilder.build(0,"添加供应商区域成功",supplierArea);
        }else{
            return JsonBuilder.build(0,"添加供应商区域失败",null);
        }
    }

    @PostMapping("edit")
    public JSONObject upSupplierArea(@RequestBody BaseSupplierArea supplierArea){
        BaseSupplierArea area = supplierAreaService.selectById(supplierArea.getId());
        if (area == null){
            return JsonBuilder.build(1,"要修改的数据不存在",null);
        }
        if (StringUtils.isNotBlank(supplierArea.getAreaNo())){
            if (!supplierAreaService.checkNo(supplierArea)){
                return JsonBuilder.build(0,"区域编号重复",null);
            }
        }
        if (supplierAreaService.upSupplierArea(supplierArea)){
            return JsonBuilder.build(0,"修改供应商区域成功",supplierArea);
        }else{
            return JsonBuilder.build(0,"修改供应商区域失败",null);
        }
    }

    @PostMapping("del")
    public JSONObject delSupplierArea(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        BaseSupplierArea area = supplierAreaService.selectById(id);
        if (area == null){
            return JsonBuilder.build(1,"数据不存在",null);
        }
        if (supplierAreaService.delSupplierArea(id)){
            return JsonBuilder.build(0,"删除供应商区域成功",null);
        }else{
            return JsonBuilder.build(1,"删除供应商区域失败",null);
        }
    }
}
