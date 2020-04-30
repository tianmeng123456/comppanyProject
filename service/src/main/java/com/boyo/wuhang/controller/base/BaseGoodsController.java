package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseGoods;
import com.boyo.wuhang.service.base.BaseGoodsService;
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
@RequestMapping("/api/base/goods")
public class BaseGoodsController {
    @Autowired
    private BaseGoodsService goodsService;

    @PostMapping("list")
    public JSONObject getGoodsList(@RequestBody JSONObject jsonObject){
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        BaseGoods baseGoods = JSONTool.getObject(jsonObject.toString(), BaseGoods.class);
        page = goodsService.getGoodsList(baseGoods,page);
        return JsonBuilder.build(1,"",page);
    }

    @PostMapping("add")
    public JSONObject addGoods(@RequestBody BaseGoods goods){
        if (StringUtils.isBlank(goods.getGoodsName())){
            return JsonBuilder.build(1,"商品名称为空",null);
        }
        if (StringUtils.isBlank(goods.getGoodsCode())){
            return JsonBuilder.build(1,"商品编码为空",null);
        }
        if (!goodsService.checkNo(goods)){
            return JsonBuilder.build(1,"商品编码重复",null);
        }
        if (goodsService.addGoods(goods)){
            return JsonBuilder.build(0,"添加商品档案成功",goods);
        }else{
            return JsonBuilder.build(1,"添加商品档案失败",null);
        }
    }

    @PostMapping("update")
    public JSONObject upGoods(@RequestBody BaseGoods goods){
        BaseGoods baseGoods = goodsService.selectById(goods.getId());
        if (baseGoods == null){
            return JsonBuilder.build(1,"修改的商品不存在",null);
        }
        if (StringUtils.isNotBlank(goods.getGoodsCode())) {
            if (!goodsService.checkNo(goods)) {
                return JsonBuilder.build(1, "商品编码重复", null);
            }
        }
        if (goodsService.upGoods(goods)){
            return JsonBuilder.build(0,"修改商品档案成功",goods);
        }else{
            return JsonBuilder.build(1,"修改商品档案失败",null);
        }
    }

    @PostMapping("del")
    public JSONObject delGoods(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        BaseGoods baseGoods = goodsService.selectById(id);
        if (baseGoods == null){
            return JsonBuilder.build(1,"删除的商品档案不存在",null);
        }
        if (goodsService.delGoods(id)){
            return JsonBuilder.build(0,"删除商品档案成功",null);
        }else {
            return JsonBuilder.build(1,"删除商品档案失败",null);
        }
    }
}
