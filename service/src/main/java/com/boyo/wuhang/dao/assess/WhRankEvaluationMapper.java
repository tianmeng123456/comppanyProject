package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.assess.WhRankEvaluation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WhRankEvaluationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhRankEvaluation record);

    int insertSelective(WhRankEvaluation record);

    WhRankEvaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WhRankEvaluation record);

    int updateByPrimaryKey(WhRankEvaluation record);

    int deleteByEvaluationId(@Param("evaluationId")Long evaluationId);

    List<WhRankEvaluation> getByEvaluationId(@Param("evaluationId")Long evaluationId);

    String getDepartureString(@Param("evaluationId")Long evaluationId);

    List<Map<String,Object>> getRankRecordByEvaluationId(@Param("evaluationId")Long evaluationId,
                                                         @Param("departureString")String departureString);
}