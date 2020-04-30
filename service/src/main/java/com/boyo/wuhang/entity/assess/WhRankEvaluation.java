package com.boyo.wuhang.entity.assess;

import java.util.List;

public class WhRankEvaluation {
    private Long id;

    private String wRank;

    private String grade;

    private Integer wLevel;

    private String detail;

    private Long evaluationId;

    private List<WhRankPrice> priceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getwRank() {
        return wRank;
    }

    public void setwRank(String wRank) {
        this.wRank = wRank == null ? null : wRank.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public Integer getwLevel() {
        return wLevel;
    }

    public void setwLevel(Integer wLevel) {
        this.wLevel = wLevel;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

	public List<WhRankPrice> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<WhRankPrice> priceList) {
		this.priceList = priceList;
	}
}