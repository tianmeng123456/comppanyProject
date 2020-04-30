package com.boyo.wuhang.entity.base;

public class BaseRank {
    private Long id;

    private String wRank;

    private String grade;

    private Integer wLevel;

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
}