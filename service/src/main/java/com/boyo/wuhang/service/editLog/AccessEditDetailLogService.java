package com.boyo.wuhang.service.editLog;

import com.boyo.wuhang.dao.editLog.WhAccessEditDetailLogMapper;
import com.boyo.wuhang.entity.WhAccessEditLogForm;
import com.boyo.wuhang.entity.editLog.WhAccessEditDetailLog;
import com.boyo.wuhang.ultity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessEditDetailLogService {
    @Autowired
    private WhAccessEditDetailLogMapper detailLogMapper;

    public Pager getAccessEditDetailList(WhAccessEditLogForm detailLog, Pager page){
        page.setList(detailLogMapper.getAccessEditDetailList(detailLog, page));
        page.setTotalRow(detailLogMapper.getAccessEditDetailListCount(detailLog));
        return page;
    }

    public int addEditDetail(WhAccessEditDetailLog record){
        return detailLogMapper.insertSelective(record);
    }

    public int upEditDetail(WhAccessEditDetailLog record){
        return detailLogMapper.updateByPrimaryKeySelective(record);
    }

    public int delEditDetaili(Long id){
        return detailLogMapper.deleteByPrimaryKey(id);
    }

    public WhAccessEditDetailLog selectById(Long id){
        return detailLogMapper.selectByPrimaryKey(id);
    }
}
