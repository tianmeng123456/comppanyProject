package com.boyo.wuhang.service.identify;

import com.boyo.wuhang.dao.identify.CarNumberIdentifyMapper;
import com.boyo.wuhang.entity.identify.CarNumberIdentify;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CarNumberIdentifyService {
    @Autowired
    private CarNumberIdentifyMapper identifyMapper;

    public Pager getIdentifyList(CarNumberIdentify identify, Pager page){
        page.setList(identifyMapper.getIdentifyList(identify, page));
        page.setTotalRow(identifyMapper.getIdentifyListCount(identify));
        return page;
    }

    public boolean addIdentify(CarNumberIdentify identify){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String carIdentifyNo = 'C' + sdf.format(new Date());
        if (!this.checkNo(identify)){
            return false;
        }
        identify.setCarNumber(carIdentifyNo);
        return identifyMapper.insertSelective(identify) == 1;
    }

    public boolean delIdentify(Long id){
        return identifyMapper.deleteByPrimaryKey(id) == 1;
    }

    public boolean upIdentify(CarNumberIdentify record){
        return identifyMapper.updateByPrimaryKeySelective(record) ==1;
    }

    public boolean checkNo(CarNumberIdentify record){
        if (StringUtils.isBlank(record.getCarNumber())){
            return false;
        }
        return identifyMapper.checkNo(record) == null;
    }
}
