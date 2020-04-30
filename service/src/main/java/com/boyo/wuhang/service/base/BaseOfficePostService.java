package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseOfficePostMapper;
import com.boyo.wuhang.entity.base.BaseOfficePost;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseOfficePostService {
    @Autowired
    private BaseOfficePostMapper postMapper;


    public Pager getOfficePostList(BaseOfficePost post, Pager page){
        page.setList(postMapper.getOfficePostList(post, page));
        page.setTotalRow(postMapper.getOfficePostListCount(post));
        return page;
    }


    public BaseOfficePost selectById(Long id){
        return postMapper.selectByPrimaryKey(id);
    }


    public boolean addOffice(BaseOfficePost post){
        if (!this.checkNo(post)){
            return false;
        }
        return postMapper.insertSelective(post) == 1;
    }

    public boolean upOffice(BaseOfficePost record){
        return postMapper.updateByPrimaryKeySelective(record) ==1;
    }

    public boolean delOffice(Long id ){
        return postMapper.deleteByPrimaryKey(id) ==1;
    }


    public boolean checkNo(BaseOfficePost record){
        if (StringUtils.isBlank(record.getPostNo())){
            return false;
        }
        return postMapper.checkNo(record) == null;
    }
}
