package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseLoadPlace;
import com.boyo.wuhang.service.base.BaseLoadPlaceService;
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
@RequestMapping("/api/base/loadplace/")
public class BaseLoadPlaceController {
    @Autowired
    private BaseLoadPlaceService placeService;

    @PostMapping("list")
    public JSONObject getLoadPlaceList(@RequestBody JSONObject jsonObject){
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        BaseLoadPlace loadPlace = JSONTool.getObject(jsonObject.toString(), BaseLoadPlace.class);
        page = placeService.getLoadPlaceList(loadPlace,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("add")
    public JSONObject addLoadPlace(@RequestBody BaseLoadPlace loadPlace){
        if (!placeService.checkNo(loadPlace)){
            return JsonBuilder.build(1,"装卸地编号重复",null);
        }
        if (StringUtils.isBlank(loadPlace.getPlaceNo())){
            return JsonBuilder.build(1,"装卸地编号为空",null);
        }
        if (StringUtils.isBlank(loadPlace.getPlaceName())){
            return JsonBuilder.build(1,"装卸地名称为空",null);
        }
        if (placeService.addLoadPlace(loadPlace)){
            return JsonBuilder.build(0,"添加装卸地成功",loadPlace);
        }else{
            return JsonBuilder.build(1,"添加装卸地失败",null);
        }
    }

    @PostMapping("edit")
    public JSONObject upLoadPlace(@RequestBody BaseLoadPlace loadPlace){
        BaseLoadPlace place = placeService.selectById(loadPlace.getId());
        if (place == null){
            return JsonBuilder.build(1,"要修改的数据不存在",null);
        }
        if (StringUtils.isNotBlank(loadPlace.getPlaceName())){
            if (StringUtils.isBlank(loadPlace.getPlaceName())){
                return JsonBuilder.build(1,"装卸地名称为空",null);
            }
        }
        if (StringUtils.isNotBlank(loadPlace.getPlaceNo())){
            if (!placeService.checkNo(loadPlace)){
                return JsonBuilder.build(1,"装卸地编号重复",null);
            }
        }
        if (placeService.upLoadPlace(loadPlace)){
            return JsonBuilder.build(0,"修改装卸地成功",loadPlace);
        }else{
            return JsonBuilder.build(1,"修改装卸地失败",null);
        }
    }

    @PostMapping("del")
    public JSONObject delLoadPlace(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        BaseLoadPlace baseLoadPlace = placeService.selectById(id);
        if (baseLoadPlace == null ){
            return JsonBuilder.build(1,"数据不存在",null);
        }
        if (placeService.delLoadPlace(id)){
            return JsonBuilder.build(0,"删除装卸地成功",null);
        }else{
            return JsonBuilder.build(1,"删除装卸地失败",null);
        }
    }
}
