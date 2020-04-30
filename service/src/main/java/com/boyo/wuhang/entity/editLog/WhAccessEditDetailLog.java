package com.boyo.wuhang.entity.editLog;

import java.math.BigDecimal;

public class WhAccessEditDetailLog {
    private Long id;

    private String d_wRank_log;

    private String d_grade_log;

    private Integer d_wLevel_log;

    private BigDecimal d_proportion_log;

    private BigDecimal d_priceAdjust_log;

    private Long d_accessId_log;

    private Long d_rankId_log;

    private Long accessEditId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getD_wRank_log() {
        return d_wRank_log;
    }

    public void setD_wRank_log(String d_wRank_log) {
        this.d_wRank_log = d_wRank_log == null ? null : d_wRank_log.trim();
    }

    public String getD_grade_log() {
        return d_grade_log;
    }

    public void setD_grade_log(String d_grade_log) {
        this.d_grade_log = d_grade_log == null ? null : d_grade_log.trim();
    }

    public Integer getD_wLevel_log() {
        return d_wLevel_log;
    }

    public void setD_wLevel_log(Integer d_wLevel_log) {
        this.d_wLevel_log = d_wLevel_log;
    }

    public BigDecimal getD_proportion_log() {
        return d_proportion_log;
    }

    public void setD_proportion_log(BigDecimal d_proportion_log) {
        this.d_proportion_log = d_proportion_log;
    }

    public BigDecimal getD_priceAdjust_log() {
        return d_priceAdjust_log;
    }

    public void setD_priceAdjust_log(BigDecimal d_priceAdjust_log) {
        this.d_priceAdjust_log = d_priceAdjust_log;
    }

    public Long getD_accessId_log() {
        return d_accessId_log;
    }

    public void setD_accessId_log(Long d_accessId_log) {
        this.d_accessId_log = d_accessId_log;
    }

    public Long getD_rankId_log() {
        return d_rankId_log;
    }

    public void setD_rankId_log(Long d_rankId_log) {
        this.d_rankId_log = d_rankId_log;
    }

    public Long getAccessEditId() {
        return accessEditId;
    }

    public void setAccessEditId(Long accessEditId) {
        this.accessEditId = accessEditId;
    }
}