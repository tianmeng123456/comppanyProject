package com.boyo.wuhang.entity.editLog;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WhAccessEditLog {
    private Long id;

    private String a_accessNo_log;

    private BigDecimal a_Impurities_log;

    private String a_remark_log;

    private String a_accessPerson_log;

    private Long a_employeeId_log;

    private String a_editPerson_log;

    private Long a_managerId_log;

    private Date a_checkTime_log;

    private Integer a_flag_log;

    private Integer a_mark_log;

    private Long a_deliverId_log;

    private BigDecimal a_priceAdjust_log;

    private Date a_createTime_log;

    private BigDecimal a_sumAdjust_log;

    private Long a_loadPlaceId_log;

    private Integer a_over60_log;

    private Long a_employeeId2_log;

    private String a_accessPerson2_log;

    private Date a_submitTime_log;

    private Date a_adoptTime_log;

    private Long createId;

    private String createName;

    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getA_accessNo_log() {
        return a_accessNo_log;
    }

    public void setA_accessNo_log(String a_accessNo_log) {
        this.a_accessNo_log = a_accessNo_log == null ? null : a_accessNo_log.trim();
    }

    public BigDecimal getA_Impurities_log() {
        return a_Impurities_log;
    }

    public void setA_Impurities_log(BigDecimal a_Impurities_log) {
        this.a_Impurities_log = a_Impurities_log;
    }

    public String getA_remark_log() {
        return a_remark_log;
    }

    public void setA_remark_log(String a_remark_log) {
        this.a_remark_log = a_remark_log == null ? null : a_remark_log.trim();
    }

    public String getA_accessPerson_log() {
        return a_accessPerson_log;
    }

    public void setA_accessPerson_log(String a_accessPerson_log) {
        this.a_accessPerson_log = a_accessPerson_log == null ? null : a_accessPerson_log.trim();
    }

    public Long getA_employeeId_log() {
        return a_employeeId_log;
    }

    public void setA_employeeId_log(Long a_employeeId_log) {
        this.a_employeeId_log = a_employeeId_log;
    }

    public String getA_editPerson_log() {
        return a_editPerson_log;
    }

    public void setA_editPerson_log(String a_editPerson_log) {
        this.a_editPerson_log = a_editPerson_log == null ? null : a_editPerson_log.trim();
    }

    public Long getA_managerId_log() {
        return a_managerId_log;
    }

    public void setA_managerId_log(Long a_managerId_log) {
        this.a_managerId_log = a_managerId_log;
    }

    public Date getA_checkTime_log() {
        return a_checkTime_log;
    }

    public void setA_checkTime_log(Date a_checkTime_log) {
        this.a_checkTime_log = a_checkTime_log;
    }

    public Integer getA_flag_log() {
        return a_flag_log;
    }

    public void setA_flag_log(Integer a_flag_log) {
        this.a_flag_log = a_flag_log;
    }

    public Integer getA_mark_log() {
        return a_mark_log;
    }

    public void setA_mark_log(Integer a_mark_log) {
        this.a_mark_log = a_mark_log;
    }

    public Long getA_deliverId_log() {
        return a_deliverId_log;
    }

    public void setA_deliverId_log(Long a_deliverId_log) {
        this.a_deliverId_log = a_deliverId_log;
    }

    public BigDecimal getA_priceAdjust_log() {
        return a_priceAdjust_log;
    }

    public void setA_priceAdjust_log(BigDecimal a_priceAdjust_log) {
        this.a_priceAdjust_log = a_priceAdjust_log;
    }

    public Date getA_createTime_log() {
        return a_createTime_log;
    }

    public void setA_createTime_log(Date a_createTime_log) {
        this.a_createTime_log = a_createTime_log;
    }

    public BigDecimal getA_sumAdjust_log() {
        return a_sumAdjust_log;
    }

    public void setA_sumAdjust_log(BigDecimal a_sumAdjust_log) {
        this.a_sumAdjust_log = a_sumAdjust_log;
    }

    public Long getA_loadPlaceId_log() {
        return a_loadPlaceId_log;
    }

    public void setA_loadPlaceId_log(Long a_loadPlaceId_log) {
        this.a_loadPlaceId_log = a_loadPlaceId_log;
    }

    public Integer getA_over60_log() {
        return a_over60_log;
    }

    public void setA_over60_log(Integer a_over60_log) {
        this.a_over60_log = a_over60_log;
    }

    public Long getA_employeeId2_log() {
        return a_employeeId2_log;
    }

    public void setA_employeeId2_log(Long a_employeeId2_log) {
        this.a_employeeId2_log = a_employeeId2_log;
    }

    public String getA_accessPerson2_log() {
        return a_accessPerson2_log;
    }

    public void setA_accessPerson2_log(String a_accessPerson2_log) {
        this.a_accessPerson2_log = a_accessPerson2_log == null ? null : a_accessPerson2_log.trim();
    }

    public Date getA_submitTime_log() {
        return a_submitTime_log;
    }

    public void setA_submitTime_log(Date a_submitTime_log) {
        this.a_submitTime_log = a_submitTime_log;
    }

    public Date getA_adoptTime_log() {
        return a_adoptTime_log;
    }

    public void setA_adoptTime_log(Date a_adoptTime_log) {
        this.a_adoptTime_log = a_adoptTime_log;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}