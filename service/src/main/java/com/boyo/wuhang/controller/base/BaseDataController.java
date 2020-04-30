package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.service.base.BaseDataService;
import com.boyo.wuhang.ultity.JsonBuilder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/base/data")
public class BaseDataController {
    @Autowired
    private BaseDataService dataService;

    @RequestMapping(value = "province",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getProvince(){
        List<String> result = dataService.getProvinceList();
        return JsonBuilder.build(0,"",result);
    }

    @RequestMapping(value = "city",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getCity(@RequestBody String province){
        List<String> result = dataService.getCityList(province);
        return JsonBuilder.build(0,"",result);
    }

    @RequestMapping(value = "district",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getDistrict(@RequestBody JSONObject jsonObject){
        String province = jsonObject.get("province").toString();
        String city = jsonObject.get("city").toString();
        List<String> result = dataService.getDistrictList(province, city);
        return JsonBuilder.build(0,"",result);
    }
}
