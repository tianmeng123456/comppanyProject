package com.boyo.wuhang.controller.assess;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.entity.WhDeliverForm;
import com.boyo.wuhang.entity.assess.WhDeliver;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.assess.DeliverService;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/deliver")
public class PriceCheckController {

    @Autowired
    private DeliverService deliverService;

    /**
     * 单价审核结算列表
     * @param deliverForm
     * @return  0 未修改  1  修改   2审核
     */
    @RequestMapping(value = "pricesettlelist",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getPriceSettleList(@RequestBody WhDeliverForm deliverForm){
        Pager page = new Pager();
        page.setPageNumber(deliverForm.getPageNumber());
        page.setPageSize(deliverForm.getPageSize());
        page = deliverService.getPriceSettleList(deliverForm,page);
        return JsonBuilder.build(0,"",page);
    }

    /**
     * 修改单价后审核操作
     * @param deliverForm
     * @return
     */
    @RequestMapping(value = "check_modify_price",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkModifyPrice(@RequestBody WhDeliverForm deliverForm){
        BaseUser user = LoginInterceptor.user.get();
        WhDeliver deliver = deliverService.getDeliverById(deliverForm.getDeliverId());
        if (deliver == null){
            return JsonBuilder.build(1,"送货单不存在",null);
        }
        if (deliver.getEditFlag() == 2){
            return JsonBuilder.build(1,"已审核",null);
        }
        deliverForm.setId(deliverForm.getDeliverId());
        deliverForm.setEditFlag(2);
        deliverForm.setPriceCheckId(user.getId());
        deliverForm.setPriceCheckName(user.getUserName());
        deliverForm.setPriceCheckTime(new Date());
        if (deliverService.updateDeliver(deliverForm)){
            return JsonBuilder.build(0,"审核成功",deliverForm);
        }else{
            return JsonBuilder.build(1,"审核失败",null);
        }
    }

    /**
     * 单价审核     反审核操作
     * @param deliverForm
     * @return
     */
    @RequestMapping(value = "uncheck_modify_price",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uncheckModifyPrice(@RequestBody WhDeliverForm deliverForm){
        WhDeliver deliver = deliverService.getDeliverById(deliverForm.getDeliverId());
        if (deliver == null){
            return JsonBuilder.build(1,"送货单不存在",null);
        }
        if (deliver.getEditFlag() == 2){
            return JsonBuilder.build(1,"已审核",null);
        }
        deliverForm.setId(deliverForm.getDeliverId());
        deliverForm.setEditFlag(1);
        if (deliverService.updateDeliver(deliverForm)){
            return JsonBuilder.build(0,"审核成功",deliverForm);
        }else{
            return JsonBuilder.build(1,"审核失败",null);
        }
    }
}
