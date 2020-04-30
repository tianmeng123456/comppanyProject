package com.boyo.wuhang.dao.editLog;

import com.boyo.wuhang.entity.WhAccessEditLogForm;
import com.boyo.wuhang.entity.editLog.WhAccessEditLog;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhAccessEditLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhAccessEditLog record);

    int insertSelective(WhAccessEditLog record);

    int insertAccessData(Long accessId);

    WhAccessEditLog selectByPrimaryKey(Long id);

    int updateByAccessId(WhAccessEditLog record);

    int updateByPrimaryKeySelective(WhAccessEditLog record);

    int updateByPrimaryKey(WhAccessEditLog record);

    List<WhAccessEditLogForm> getAccessEditLogList(@Param("log") WhAccessEditLogForm log,
                                                   @Param("page") Pager page);

    int getAccessEditLogListCount(@Param("log") WhAccessEditLogForm log);

    List<WhAccessEditLog> selectById(Long accessId);
}