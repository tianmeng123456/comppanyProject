package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseOfficePost;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseOfficePostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseOfficePost record);

    int insertSelective(BaseOfficePost record);

    BaseOfficePost selectByPrimaryKey(Long id);

    BaseOfficePost checkNo(BaseOfficePost record);

    int updateByPrimaryKeySelective(BaseOfficePost record);

    int updateByPrimaryKey(BaseOfficePost record);

    List<BaseOfficePost> getOfficePostList(@Param("post") BaseOfficePost post,
                                           @Param("page") Pager page);

    int getOfficePostListCount(@Param("post") BaseOfficePost post);
}