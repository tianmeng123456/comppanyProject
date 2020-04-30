package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseLoadPlaceMapper;
import com.boyo.wuhang.entity.base.BaseLoadPlace;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseLoadPlaceService {
    @Autowired
    private BaseLoadPlaceMapper loadPlaceMapper;

    public Pager getLoadPlaceList(BaseLoadPlace loadPlace, Pager page){
        page.setList(loadPlaceMapper.getLoadPlaceList(loadPlace, page));
        page.setTotalRow(loadPlaceMapper.getLoadPlaceListCount(loadPlace));
        return page;
    }

    public boolean addLoadPlace(BaseLoadPlace loadPlace){
        if (!this.checkNo(loadPlace)){
            return false;
        }
        return loadPlaceMapper.insertSelective(loadPlace)==1;
    }

    public boolean upLoadPlace(BaseLoadPlace record){
        return loadPlaceMapper.updateByPrimaryKeySelective(record)==1;
    }

    public boolean delLoadPlace(Long id){
        return loadPlaceMapper.deleteByPrimaryKey(id)==1;
    }

    public BaseLoadPlace selectById(Long id){
        return loadPlaceMapper.selectByPrimaryKey(id);
    }

    public boolean checkNo(BaseLoadPlace record){
        if (StringUtils.isBlank(record.getPlaceNo())){
            return false;
        }
        return loadPlaceMapper.checkNo(record) == null;
    }
}
