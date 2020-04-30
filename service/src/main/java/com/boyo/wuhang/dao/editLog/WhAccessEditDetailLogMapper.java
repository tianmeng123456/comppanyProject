package com.boyo.wuhang.dao.editLog;

import com.boyo.wuhang.entity.WhAccessEditLogForm;
import com.boyo.wuhang.entity.assess.WhAccess;
import com.boyo.wuhang.entity.editLog.WhAccessEditDetailLog;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhAccessEditDetailLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhAccessEditDetailLog record);

    int insertSelective(WhAccessEditDetailLog record);

    WhAccessEditDetailLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhAccessEditDetailLog record);

    int updateByPrimaryKey(WhAccessEditDetailLog record);

    List<WhAccessEditDetailLog> getAccessEditDetailList(@Param("detailLog") WhAccessEditLogForm detailLog,
                                                        @Param("page") Pager page);

    int getAccessEditDetailListCount(@Param("detailLog") WhAccessEditLogForm detailLog);
}