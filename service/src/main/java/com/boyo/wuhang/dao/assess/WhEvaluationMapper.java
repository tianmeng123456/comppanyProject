package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.EvaluationForm;
import com.boyo.wuhang.entity.assess.WhEvaluation;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface WhEvaluationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhEvaluation record);

    int insertSelective(WhEvaluation record);

    WhEvaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhEvaluation record);

    int updateByPrimaryKey(WhEvaluation record);

    WhEvaluation checkNo(@Param("evaluationNo") String evaluationNo);

    int getETodayCount();

    List<Map<String,Object>> getEvaluationList(@Param("record") EvaluationForm record, Pager page);

    int getEvaluationCount(@Param("record") EvaluationForm record);

    WhEvaluation getNewestEvaluation();

    List<Map<String,Object>> getExecRankList(@Param("arriveWeighTime")Date arriveWeighTime);
}