package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.WhCarRecord;
import com.boyo.wuhang.service.base.WhCarRecordService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import com.boyo.wuhang.ultity.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/base/carrecord")
@Api(tags = "入场车辆资料")
public class WhCarRecordController {
    @Autowired
    private WhCarRecordService recordService;

    @PostMapping("list")
    @ApiOperation(value = "查看入场车辆资料列表",notes = "返回成功与失败")
    @ApiImplicitParams({
		    @ApiImplicitParam(name = "id",value = "车辆记录Id"),
		    @ApiImplicitParam(name = "carNo",value = "车牌号")
    })
    public JSONObject getList(@RequestBody JSONObject jsonObject){
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        WhCarRecord carRecord = JSONTool.getObject(jsonObject.toString(), WhCarRecord.class);
        page = recordService.getWhCarRecordList(carRecord,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("add")
    @ApiOperation(value = "添加入场车辆资料",notes = "返回成功与失败")
    public JSONObject addCarRecord(@RequestBody WhCarRecord record){
        if (!Validator.isCarNumber(record.getCarNo()) || StringUtils.isBlank(record.getCarNo())){
            return JsonBuilder.build(1,"车牌号输入不规范或车牌号为空",null);
        }
        if (!Validator.isMobile(record.getPhone())){
            return JsonBuilder.build(1,"手机号输入不规范",null);
        }
        if (!recordService.checkNo(record)){
            return JsonBuilder.build(1,"车牌号重复",null);
        }
        if (record.getTravelLicense() == null ){
            return JsonBuilder.build(1,"证件为空",null);
        }
        if (record.getDriverLicense() == null){
            return JsonBuilder.build(1,"证件为空",null);
        }
        if (recordService.addCar(record)){
            return JsonBuilder.build(0,"添加入场车辆资料成功",record);
        }else {
            return JsonBuilder.build(1,"添加入场车辆资料失败",null);
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "修改入场车辆资料",notes = "返回成功与失败")
    public JSONObject updateCarRecord(@RequestBody WhCarRecord record){
        if (StringUtils.isNotBlank(record.getCarNo())){
            if (!Validator.isCarNumber(record.getCarNo()) || StringUtils.isBlank(record.getCarNo())){
                return JsonBuilder.build(1,"车牌号输入不规范或车牌号为空",null);
            }
        }
        if (StringUtils.isNotBlank(record.getPhone())){
            if (!Validator.isMobile(record.getPhone())){
                return JsonBuilder.build(1,"手机号输入不规范",null);
            }
        }
        record.setEditTime(new Date());
        if (recordService.updateCar(record)){
            return JsonBuilder.build(0,"修改入场车辆资料成功",record);
        }else{
            return JsonBuilder.build(1,"修改入场车辆资料失败",null);
        }
    }

    @PostMapping("del")
    @ApiOperation(value = "删除入场车辆资料",notes = "返回成功与失败")
    public JSONObject delCarRecord(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        WhCarRecord carRecord = recordService.selectById(id);
        if (carRecord == null){
            return JsonBuilder.build(1,"入场车辆资料不存在",null);
        }
        if (recordService.delCar(id)){
            return JsonBuilder.build(0,"删除入场车辆资料成功",null);
        }else{
            return JsonBuilder.build(1,"删除入场车辆资料失败",null);
        }
    }
}
