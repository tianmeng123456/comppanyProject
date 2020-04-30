package com.boyo.wuhang.controller.editLog;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.entity.WhAccessEditLogForm;
import com.boyo.wuhang.entity.assess.WhAccessDetail;
import com.boyo.wuhang.entity.assess.WhRankEvaluation;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.entity.editLog.WhAccessEditDetailLog;
import com.boyo.wuhang.entity.editLog.WhAccessEditLog;
import com.boyo.wuhang.service.assess.AccessService;
import com.boyo.wuhang.service.editLog.AccessEditDetailLogService;
import com.boyo.wuhang.service.editLog.AccessEditLogService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/editlog/")
public class AccessEditLogController {
    @Autowired
    private AccessEditLogService editLogService;
    @Autowired
    private AccessEditDetailLogService detailLogService;
    @Autowired
    private AccessService accessService;


    @PostMapping("accesslist")
    public JSONObject getAccessEditLogList(@RequestBody WhAccessEditLogForm logForm){
        Pager page = new Pager();
        page.setPageNumber(logForm.getPageNumber());
        page.setPageSize(logForm.getPageSize());
        page = editLogService.getAccessEditLogList(logForm,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("access_detail_list")
    public JSONObject getAccessEditDetailList(@RequestBody WhAccessEditLogForm editLogForm){
        Pager page = new Pager();
        page.setPageNumber(editLogForm.getPageNumber());
        page.setPageSize(editLogForm.getPageSize());
        page = detailLogService.getAccessEditDetailList(editLogForm, page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("edit_log")
    public JSONObject addAccessEdit(@RequestBody JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONArray("detailList");
        List<WhAccessEditDetailLog> detailList = new ArrayList<>();
        for (int i = 0; i<jsonArray.size(); i++){
            JSONObject item = jsonArray.getJSONObject(i);
            WhAccessEditDetailLog editDetailLog = JSONTool.getObject(item.toString(), WhAccessEditDetailLog.class);
            WhAccessDetail accessDetail = accessService.getAccessDetailByRank(editDetailLog.getD_rankId_log(),editDetailLog.getD_accessId_log());
            editDetailLog.setD_wRank_log(accessDetail.getwRank());
            editDetailLog.setD_grade_log(accessDetail.getGrade());
            editDetailLog.setD_wLevel_log(Integer.parseInt(accessDetail.getwLevel()));
            editDetailLog.setD_proportion_log(accessDetail.getProportion());
            editDetailLog.setD_priceAdjust_log(accessDetail.getPriceAdjust());
            editDetailLog.setD_accessId_log(accessDetail.getAccessId());
            editDetailLog.setD_rankId_log(accessDetail.getRankId());
            editDetailLog.setId(null);
            detailList.add(editDetailLog);
        }
        jsonObject.remove("detailList");
        WhAccessEditLog editLog = JSONTool.getObject(jsonObject.toString(), WhAccessEditLog.class);
        BaseUser user = LoginInterceptor.user.get();
        editLog.setCreateId(user.getId());
        editLog.setCreateName(user.getUserName());
        editLog.setCreateTime(new Date());
        if (editLogService.addAccessEditLog(editLog,detailList)){
            return JsonBuilder.build(0,"成功",editLog);
        }else{
            return JsonBuilder.build(1,"失败",null);
        }
    }
}
