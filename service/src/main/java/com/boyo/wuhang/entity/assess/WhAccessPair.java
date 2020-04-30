package com.boyo.wuhang.entity.assess;

public class WhAccessPair {
    private Long id;

    private Long employeeId1;

    private String accessPerson1;

    private Long employeeId2;

    private String accessPerson2;

    private Integer flag;

    private Integer mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId1() {
        return employeeId1;
    }

    public void setEmployeeId1(Long employeeId1) {
        this.employeeId1 = employeeId1;
    }

    public String getAccessPerson1() {
        return accessPerson1;
    }

    public void setAccessPerson1(String accessPerson1) {
        this.accessPerson1 = accessPerson1 == null ? null : accessPerson1.trim();
    }

    public Long getEmployeeId2() {
        return employeeId2;
    }

    public void setEmployeeId2(Long employeeId2) {
        this.employeeId2 = employeeId2;
    }

    public String getAccessPerson2() {
        return accessPerson2;
    }

    public void setAccessPerson2(String accessPerson2) {
        this.accessPerson2 = accessPerson2 == null ? null : accessPerson2.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}