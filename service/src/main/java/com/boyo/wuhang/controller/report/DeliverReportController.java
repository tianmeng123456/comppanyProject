package com.boyo.wuhang.controller.report;

import com.boyo.wuhang.entity.WhDeliverForm;
import com.boyo.wuhang.service.report.DeliverReportService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/repoty/deliver/")
public class DeliverReportController {
    @Autowired
    private DeliverReportService reportService;

    @PostMapping("deliverreportlist")
    public JSONObject getDeliverReportList(@RequestBody WhDeliverForm deliverForm){
        Pager page = new Pager();
        page.setPageNumber(deliverForm.getPageNumber());
        page.setPageSize(deliverForm.getPageSize());
        page = reportService.getDeliverReportList(deliverForm,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("statementlist")
    public JSONObject getPriceStatementList(@RequestBody WhDeliverForm deliverForm){
        Pager page = new Pager();
        page.setPageNumber(deliverForm.getPageNumber());
        page.setPageSize(deliverForm.getPageSize());
        page = reportService.getPriceStatementList(deliverForm,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("daliypurchase")
    public JSONObject getDeliverByDayList(@RequestBody JSONObject jsonObject){
        String createMonth = jsonObject.getString("createMonth");
//        long createYear = Long.parseLong(jsonObject.getString("createYear"));
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        WhDeliverForm deliverForm = JSONTool.getObject(jsonObject.toString(), WhDeliverForm.class);
        page = reportService.getDeliverByDayList(createMonth,deliverForm,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("PaymentSummary")
    public JSONObject getPaymentSummaryList(@RequestBody WhDeliverForm deliverForm){
        Pager page = new Pager();
        page.setPageNumber(deliverForm.getPageNumber());
        page.setPageSize(deliverForm.getPageSize());
        page = reportService.getPaymentSummaryList(deliverForm,page);
        return JsonBuilder.build(0,"",page);
    }
}
