package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.WhCarRecordMapper;
import com.boyo.wuhang.entity.base.WhCarRecord;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhCarRecordService {
    @Autowired
    private WhCarRecordMapper recordMapper;

    public Pager getWhCarRecordList(WhCarRecord record, Pager page){
        page.setList(recordMapper.getWhCarRecordList(record, page));
        page.setTotalRow(recordMapper.getWhCarRecordListCount(record));
        return page;
    }

    public boolean addCar(WhCarRecord record){
        if (!this.checkNo(record)){
            return false;
        }
        return recordMapper.insertSelective(record) == 1;
    }

    public boolean updateCar(WhCarRecord record){
        return recordMapper.updateByPrimaryKeySelective(record) == 1;
    }


    public boolean delCar(Long id){
        return recordMapper.deleteByPrimaryKey(id) == 1;
    }

    public WhCarRecord selectById(Long id){
        return recordMapper.selectByPrimaryKey(id);
    }

    public boolean checkNo(WhCarRecord record){
        if (StringUtils.isBlank(record.getCarNo())){
            return false;
        }
        return recordMapper.checkNo(record) == null;
    }

    public WhCarRecord getCarRecordByNo(String carNo){
    	return recordMapper.getCarRecordByNo(carNo);
    }
}
