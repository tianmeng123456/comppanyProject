package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseRankMapper;
import com.boyo.wuhang.entity.base.BaseRank;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseRankService {
    @Autowired
    private BaseRankMapper rankMapper;

    public Pager getRankList(BaseRank rank, Pager page){
        page.setList(rankMapper.getRankList(rank, page));
        page.setTotalRow(rankMapper.getRankListCount(rank));
        return page;
    }

    public boolean addRank(BaseRank rank){
        if (!this.checkNo(rank)){
            return false;
        }
        return rankMapper.insertSelective(rank) == 1;
    }

    public boolean upRank(BaseRank rank){
        return rankMapper.updateByPrimaryKeySelective(rank) == 1;
    }

    public boolean delRank(Long id){
        return rankMapper.deleteByPrimaryKey(id) ==1 ;
    }

    public BaseRank selectById(Long id){
        return rankMapper.selectByPrimaryKey(id);
    }

    public boolean checkNo(BaseRank rank){
        if (StringUtils.isBlank(rank.getGrade())
        && StringUtils.isBlank(rank.getwRank())
        && StringUtils.isBlank(rank.getwLevel().toString())){
            return false;
        }
        return rankMapper.checkNo(rank) == null;
    }
}
