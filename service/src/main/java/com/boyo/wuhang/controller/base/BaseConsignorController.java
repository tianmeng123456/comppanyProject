package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseConsignor;
import com.boyo.wuhang.service.base.BaseConsignorService;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/base/consignor")
@Api(tags = "货主管理")
public class BaseConsignorController {
    @Autowired
    private BaseConsignorService consignorService;

    @PostMapping("list")
    @ApiOperation(value = "查看货主列表",notes = "返回成功与失败")
    public JSONObject getConsignorList(@RequestBody JSONObject jsonObject){
        JSONObject consignorJson = JSONObject.fromObject(jsonObject.getString("Consignor"));
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(BaseConsignor.class);
        BaseConsignor consignor = (BaseConsignor) JSONObject.toBean(consignorJson, jsonConfig);
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        page = consignorService.getConsignorList(consignor,page);
        return JsonBuilder.build(0,"",page);

    }

    @PostMapping("add")
    @ApiOperation(value = "添加货主信息",notes = "返回成功与失败")
    public JSONObject addConsignor(@RequestBody BaseConsignor consignor){
        if (consignor.getSupplierId() == null){
            return JsonBuilder.build(1,"对应供应商为空",null);
        }
        if (consignorService.addConsignor(consignor)){
            return JsonBuilder.build(0,"添加货主信息成功",consignor);
        }else {
            return JsonBuilder.build(1,"添加货主信息失败",null);
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "修改货主信息",notes = "返回成功与失败")
    public JSONObject updateConsignor(@RequestBody BaseConsignor consignor){
        if (consignor.getSupplierId() != null){
            if (consignor.getSupplierId() == null){
                return JsonBuilder.build(1,"对应供应商为空",null);
            }
        }
        if (consignorService.updateConsignor(consignor)){
            return JsonBuilder.build(0,"修改货主信息成功",consignor);
        }else {
            return JsonBuilder.build(1,"修改货主信息失败",null);
        }
    }

    @PostMapping("del")
    @ApiOperation(value = "删除货主信息",notes = "返回成功与失败")
    public JSONObject delConsignor(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        BaseConsignor consignor = consignorService.selectById(id);
        if (consignor == null ){
            return JsonBuilder.build(1,"货主信息不存在",null);
        }
        if (consignorService.delConsignor(id)){
            return JsonBuilder.build(0,"删除货主信息成功",null);
        }else{
            return JsonBuilder.build(1,"删除货主信息失败",null);
        }
    }

    //供应商下货主信息
    @PostMapping("getconsignorlist")
    @ApiOperation(value = "查看供应商下货主列表",notes = "返回成功与失败")
    public JSONObject getConsignorBySupplierId(@RequestBody JSONObject jsonObject){
        long supplierId = Long.parseLong(jsonObject.getString("supplierId"));
        List<BaseConsignor> supplierIdList = consignorService.getConsignorBySupplierId(supplierId);
        return JsonBuilder.build(0,"",supplierIdList);
    }
}
