package com.boyo.wuhang.entity.assess;

import java.math.BigDecimal;

public class WhAccessDetail {
    private Long ord;

    private String wRank;

    private String grade;

    private String wLevel;

    private BigDecimal proportion;

    private BigDecimal priceAdjust;

    private Long accessId;

    private Long rankId;

    public Long getOrd() {
        return ord;
    }

    public void setOrd(Long ord) {
        this.ord = ord;
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

    public String getwLevel() {
        return wLevel;
    }

    public void setwLevel(String wLevel) {
        this.wLevel = wLevel == null ? null : wLevel.trim();
    }

    public BigDecimal getPriceAdjust() {
        return priceAdjust;
    }

    public void setPriceAdjust(BigDecimal priceAdjust) {
        this.priceAdjust = priceAdjust;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

	public BigDecimal getProportion() {
		return proportion;
	}

	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
	}
}