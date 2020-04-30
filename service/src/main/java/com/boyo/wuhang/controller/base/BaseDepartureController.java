package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseChinaCities;
import com.boyo.wuhang.entity.base.BaseDeparture;
import com.boyo.wuhang.service.base.BaseDataService;
import com.boyo.wuhang.service.base.DepartureService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/base/departure")
public class BaseDepartureController {
    @Autowired
    private DepartureService departureService;
    @Autowired
    private BaseDataService dataService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertDeparture(@RequestBody BaseDeparture departure){
        if (StringUtils.isBlank(departure.getdCode())){
            return JsonBuilder.build(1,"启运编号为空",null);
        }
        if (StringUtils.isBlank(departure.getdName())){
            return JsonBuilder.build(1,"启运名称为空",null);
        }
        if (!departureService.checkNo(departure)){
            return JsonBuilder.build(1,"启运编号重复",null);
        }
        if (!departureService.checkName(departure)){
            return JsonBuilder.build(1,"启运名称重复",null);
        }
        departure.setFlag(0);
        departure.setMark(0);
        if (departureService.insertDeparture(departure)){
            return JsonBuilder.build(0,"添加启运地成功",departure);
        }else{
            return JsonBuilder.build(1,"添加启运地失败",null);
        }
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateDeparture(@RequestBody BaseDeparture departure){
        if (StringUtils.isNotBlank(departure.getdCode())){
            if (!departureService.checkNo(departure)){
                return JsonBuilder.build(1,"启运地编号重复",null);
            }
        }
        if (StringUtils.isNotBlank(departure.getdName())){
            if (!departureService.checkName(departure)){
                return JsonBuilder.build(1,"启运地名称重复",null);
            }
        }
//        List<BaseChinaCities> citiesList = dataService.selectByDepartureId(departure.getId());
//        String province = null;
//        String city = null;
//        String district = null;
//        for (BaseChinaCities cities : citiesList){
//            province = cities.getProvince();
//            city = cities.getCity();
//            district = cities.getDistrict();
//        }
        if (departure.getFlag() < 3){
            return JsonBuilder.build(1,"参数错误:至少要有一个默认起运地",null);
        }
        if (departureService.updateDeparture(departure)){
            return JsonBuilder.build(0,"修改启运地成功",departure);
        }else{
            return JsonBuilder.build(1,"修改启运地失败",null);
        }
    }

    @RequestMapping(value = "del",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delDeparture(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.get("id").toString());
        //删除提示先解绑再删除
            BaseDeparture departure = departureService.selectByPrimaryKey(id);
            if (departure == null){
                return JsonBuilder.build(1,"要删除的启运地不存在",null);
            }
//        List<BaseChinaCities> citiesList = dataService.selectByDepartureId(departure.getId());
//            if (citiesList.size() >0){
//                return JsonBuilder.build(1,"请先解绑再删除数据",null);
//            }
//            String province = null;
//            String city = null;
//            String district = null;
//            for (BaseChinaCities cities : citiesList){
//                province = cities.getProvince();
//                city = cities.getCity();
//                district = cities.getDistrict();
//            }
        if (departureService.delDeparture(id)){
            return JsonBuilder.build(0,"删除启运地成功",null);
        }else {
            return JsonBuilder.build(1,"删除启运地失败",null);
        }
    }

	//绑定取消绑定区域，将BaseChinaCities中的departureId 指向 BaseDeparture 表中
    //绑定  BaseDeparture的id写入BaseChinaCities的departureid
    //取消绑定 BaseChinaCities的departureid置空
//    @RequestMapping(value = "binding",method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject binDing(@RequestBody JSONObject jsonObject){
//        String province = jsonObject.get("province").toString();
//        String city = jsonObject.get("city").toString();
//        String district = jsonObject.get("district").toString();
//        long departureId = Long.parseLong(jsonObject.getString("departureId"));
//        if (StringUtils.isBlank(province)){
//            return JsonBuilder.build(1,"省、市、区为空",null);
//        }
//        if (StringUtils.isBlank(city)){
//            return JsonBuilder.build(1,"省、市、区为空",null);
//        }
//        if (StringUtils.isBlank(district)){
//            return JsonBuilder.build(1,"省、市、区为空",null);
//        }
//        BaseDeparture departure = new BaseDeparture();
//        departure.setId(departureId);
//        if (departureService.binDing(province,city,district,departure)){
//            return JsonBuilder.build(0,"绑定成功",null);
//        }else {
//            return JsonBuilder.build(1,"绑定失败",null);
//        }
//    }
//
//    @RequestMapping(value = "unbinding",method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject unBinDing(@RequestBody JSONObject jsonObject){
//        String province = jsonObject.get("province").toString();
//        String city = jsonObject.get("city").toString();
//        String district = jsonObject.get("district").toString();
//        if (StringUtils.isBlank(province)){
//            return JsonBuilder.build(1,"省、市、区为空",null);
//        }
//        if (StringUtils.isBlank(city)){
//            return JsonBuilder.build(1,"省、市、区为空",null);
//        }
//        if (StringUtils.isBlank(district)){
//            return JsonBuilder.build(1,"省、市、区为空",null);
//        }
//        if (departureService.unBinDing(province,city,district)){
//            return JsonBuilder.build(0,"解绑成功",null);
//        }else {
//            return JsonBuilder.build(1,"解绑失败",null);
//        }
//    }
	//获取启运地下绑定的区域   启运地下绑定了哪些区域
//    @RequestMapping(value = "selectarea",method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject selectDepartureByBinding(@RequestBody JSONObject jsonObject){
//        //根据启运地id查询地区表
//        long id = Long.parseLong(jsonObject.get("id").toString());
//        List<BaseChinaCities> citiesList = dataService.selectByDepartureId(id);
//        return JsonBuilder.build(0,"",citiesList);
//    }

    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getDepartureList(@RequestBody JSONObject jsonObject){
        BaseDeparture departure = JSONTool.getObject(jsonObject.toString(),BaseDeparture.class);
        List<BaseDeparture> list = departureService.getDepartureList(departure);
        return JsonBuilder.build(0,"",list);
    }
}
