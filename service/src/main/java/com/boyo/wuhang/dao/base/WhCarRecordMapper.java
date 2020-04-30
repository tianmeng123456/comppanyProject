package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.WhCarRecord;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhCarRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhCarRecord record);

    int insertSelective(WhCarRecord record);

    WhCarRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhCarRecord record);

    int updateByPrimaryKey(WhCarRecord record);

    WhCarRecord checkNo(WhCarRecord record);

    List<WhCarRecord> getWhCarRecordList(@Param("record") WhCarRecord record,
                                         @Param("page") Pager page);

    int getWhCarRecordListCount(@Param("record") WhCarRecord record);

    WhCarRecord getCarRecordByNo(@Param("carNo")String carNo);
}