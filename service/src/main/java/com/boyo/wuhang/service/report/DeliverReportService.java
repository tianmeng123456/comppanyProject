package com.boyo.wuhang.service.report;

import com.boyo.wuhang.dao.assess.WhDeliverMapper;
import com.boyo.wuhang.entity.WhDeliverForm;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class DeliverReportService {
    @Autowired
    private WhDeliverMapper deliverMapper;

    public Pager getDeliverReportList(WhDeliverForm deliverForm, Pager page){
        selectDate(deliverForm);
        page.setList(deliverMapper.getDeliverReportList(deliverForm, page));
        page.setTotalRow(deliverMapper.getDeliverReportListCount(deliverForm));
        return page;
    }

    public Pager getPriceStatementList(WhDeliverForm deliverForm, Pager page){
        if (StringUtils.isNotBlank(deliverForm.getEndDate())){
            deliverForm.setStartDate(deliverForm.getStartDate()+" 00:00:00.000");
            deliverForm.setEndDate(deliverForm.getEndDate()+" 23:59:59.999");
        }else{
            deliverForm.setStartDate(null);
            deliverForm.setEndDate(null);
        }
        page.setList(deliverMapper.getPriceStatementList(deliverForm, page));
        page.setTotalRow(deliverMapper.getDeliverReportListCount(deliverForm));
        return page;
    }

    public Pager getDeliverByDayList(String createMonth,WhDeliverForm deliverForm, Pager page){
        page.setList(deliverMapper.getDeliverByDayList(createMonth,deliverForm,page));
        page.setTotalRow(deliverMapper.getDeliverByDayListCount(createMonth,deliverForm));
        return page;
    }

    public Pager getPaymentSummaryList(WhDeliverForm deliverForm, Pager page){
        page.setList(deliverMapper.getPaymentSummaryList(deliverForm, page));
        page.setTotalRow(deliverMapper.getPaymentSummaryListCount(deliverForm));
        return page;
    }

    //时间查询
    private void selectDate(WhDeliverForm deliverForm) {
        if (StringUtils.isNotBlank(deliverForm.getEndDate())){
            deliverForm.setStartDate(deliverForm.getStartDate()+" 00:00:00.000");
            deliverForm.setEndDate(deliverForm.getEndDate()+" 23:59:59.999");
        }else{
            deliverForm.setStartDate(null);
            deliverForm.setEndDate(null);
        }
        if (StringUtils.isNotBlank(deliverForm.getArriveWeighEndDate())){
            deliverForm.setArriveWeighStartDate(deliverForm.getArriveWeighStartDate()+" 00:00:00.000");
            deliverForm.setArriveWeighEndDate(deliverForm.getArriveWeighEndDate() +" 23:59:59.999");
        }else{
            deliverForm.setArriveWeighStartDate(null);
            deliverForm.setArriveWeighEndDate(null);
        }
        if (StringUtils.isNotBlank(deliverForm.getLeaveWeighEndTime())){
            deliverForm.setLeaveWeighStartTime(deliverForm.getLeaveWeighStartTime()+" 00:00:00.000");
            deliverForm.setLeaveWeighEndTime(deliverForm.getLeaveWeighEndTime()+" 23:59:59.999");
        }else{
            deliverForm.setLeaveWeighStartTime(null);
            deliverForm.setLeaveWeighEndTime(null);
        }
    }
}
