package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseChinaCitiesMapper;
import com.boyo.wuhang.entity.base.BaseChinaCities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDataService {
    @Autowired
    private BaseChinaCitiesMapper citiesMapper;

    public List<String> getProvinceList(){
        return citiesMapper.getProvinceList();
    }

    public List<String> getCityList(String province){
        return citiesMapper.getCityList(province);
    }

    public List<String> getDistrictList(String province, String city){
        return citiesMapper.getDistrictList(province, city);
    }

    public List<BaseChinaCities> selectByDetailAddrList(String province, String city, String district){
        return citiesMapper.selectByDetailAddrList(province, city, district);
    }

    public int updateChinaCities(BaseChinaCities record){
        return citiesMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BaseChinaCities record){
        return citiesMapper.updateByPrimaryKey(record);
    }

    public List<BaseChinaCities> selectByDepartureId(Long id){
        return citiesMapper.selectByDepartureId(id);
    }
}
