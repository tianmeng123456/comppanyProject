package com.boyo.wuhang.service.editLog;

import com.boyo.wuhang.dao.editLog.WhAccessEditDetailLogMapper;
import com.boyo.wuhang.dao.editLog.WhAccessEditLogMapper;
import com.boyo.wuhang.entity.WhAccessEditLogForm;
import com.boyo.wuhang.entity.editLog.WhAccessEditDetailLog;
import com.boyo.wuhang.entity.editLog.WhAccessEditLog;
import com.boyo.wuhang.ultity.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class AccessEditLogService {
    @Autowired
    private WhAccessEditLogMapper editLogMapper;
    @Autowired
    private WhAccessEditDetailLogMapper detailLogMapper;

    public List<WhAccessEditLog> selectById(Long accessId){
        return editLogMapper.selectById(accessId);
    }

    @Transactional
    public boolean addAccessEditLog(WhAccessEditLog editLog, List<WhAccessEditDetailLog> list){
        if (editLogMapper.insertSelective(editLog)==0){
            return false;
        }
        if (list != null){
            for (WhAccessEditDetailLog item : list){
                item.setAccessEditId(editLog.getId());
                if (detailLogMapper.insertSelective(item)==0){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean delAccessEditLog(Long id){
        return editLogMapper.deleteByPrimaryKey(id) == 1;
    }

    public int upAccessEditLog(WhAccessEditLog record){
        return editLogMapper.updateByPrimaryKeySelective(record);
    }

    public int insertAccessData(Long accessId){
        return editLogMapper.insertAccessData(accessId);
    }

    public Pager getAccessEditLogList(WhAccessEditLogForm editLog, Pager page){
        page.setList(editLogMapper.getAccessEditLogList(editLog, page));
        page.setTotalRow(editLogMapper.getAccessEditLogListCount(editLog));
        return page;
    }

    public int updateByAccessId(WhAccessEditLog record){
        return editLogMapper.updateByAccessId(record);
    }
}
