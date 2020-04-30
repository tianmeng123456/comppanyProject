package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseRank;
import com.boyo.wuhang.service.base.BaseRankService;
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
@RequestMapping("/api/base/rank")
public class BaseRankController {
    @Autowired
    private BaseRankService rankService;

    @PostMapping("list")
    public JSONObject getRankList(@RequestBody JSONObject jsonObject){
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        BaseRank rank = JSONTool.getObject(jsonObject.toString(), BaseRank.class);
        page = rankService.getRankList(rank,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("add")
    public JSONObject addRank(@RequestBody BaseRank rank){
        if (rank.getwLevel() == null){
            return JsonBuilder.build(1,"层次为空",null);
        }
        if (StringUtils.isBlank(rank.getGrade())){
            return JsonBuilder.build(1,"品级为空",null);
        }
        if (StringUtils.isBlank(rank.getwRank())){
            return JsonBuilder.build(1,"级别为空",null);
        }
        if (!rankService.checkNo(rank)){
            return JsonBuilder.build(1,"级别、品级、层次重复",null);
        }
        if (rankService.addRank(rank)){
            return JsonBuilder.build(0,"添加评价表级别基础表成功",rank);
        }else{
            return JsonBuilder.build(1,"添加评价表级别基础表失败",null);
        }
    }

    @PostMapping("update")
    public JSONObject upRank(@RequestBody BaseRank rank){
        BaseRank baseRank = rankService.selectById(rank.getId());
        if (baseRank == null){
            return JsonBuilder.build(1,"修改的评级不存在",null);
        }
        if (StringUtils.isNotBlank(rank.getGrade())
        && StringUtils.isNotBlank(rank.getwRank())
        && StringUtils.isNotBlank(rank.getwLevel().toString())){
            if (!rankService.checkNo(rank)){
                return JsonBuilder.build(1,"级别、品级、层次重复",null);
            }
        }
        if (rankService.upRank(rank)){
            return JsonBuilder.build(0,"修改评价表级别基础表成功",rank);
        }else {
            return JsonBuilder.build(1,"修改评价表级别基础表失败",null);
        }
    }

    @PostMapping("del")
    public JSONObject delRank(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        BaseRank baseRank = rankService.selectById(id);
        if (baseRank == null){
            return JsonBuilder.build(1,"数据不存在",null);
        }
        if (rankService.delRank(id)){
            return JsonBuilder.build(0,"删除评价表级别基础表成功",null);
        }else {
            return JsonBuilder.build(1,"删除评价表级别基础表失败",null);
        }
    }
}
