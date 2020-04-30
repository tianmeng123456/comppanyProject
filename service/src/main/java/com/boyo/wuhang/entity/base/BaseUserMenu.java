package com.boyo.wuhang.entity.base;

import java.util.Date;

public class BaseUserMenu {
	private Long id;

	private Long userId;

	private Integer menuId;

	private Date createTime;

	private Integer addAuth;

	private Integer editAuth;

	private Integer delAuth;

	private Integer readyingAuth;

	private Integer returnAuth;

	private Integer checkAuth;

	private Integer uncheckAuth;

	private Integer accountingAuth;

	private Integer unAccountingAuth;

	private Integer printAuth;

	private Integer excelAuth;

	private Integer changeQuery;

	private Integer allQuery;

	private Integer bindStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAddAuth() {
		return addAuth;
	}

	public void setAddAuth(Integer addAuth) {
		this.addAuth = addAuth;
	}

	public Integer getEditAuth() {
		return editAuth;
	}

	public void setEditAuth(Integer editAuth) {
		this.editAuth = editAuth;
	}

	public Integer getDelAuth() {
		return delAuth;
	}

	public void setDelAuth(Integer delAuth) {
		this.delAuth = delAuth;
	}

	public Integer getReadyingAuth() {
		return readyingAuth;
	}

	public void setReadyingAuth(Integer readyingAuth) {
		this.readyingAuth = readyingAuth;
	}

	public Integer getReturnAuth() {
		return returnAuth;
	}

	public void setReturnAuth(Integer returnAuth) {
		this.returnAuth = returnAuth;
	}

	public Integer getCheckAuth() {
		return checkAuth;
	}

	public void setCheckAuth(Integer checkAuth) {
		this.checkAuth = checkAuth;
	}

	public Integer getUncheckAuth() {
		return uncheckAuth;
	}

	public void setUncheckAuth(Integer uncheckAuth) {
		this.uncheckAuth = uncheckAuth;
	}

	public Integer getAccountingAuth() {
		return accountingAuth;
	}

	public void setAccountingAuth(Integer accountingAuth) {
		this.accountingAuth = accountingAuth;
	}

	public Integer getUnAccountingAuth() {
		return unAccountingAuth;
	}

	public void setUnAccountingAuth(Integer unAccountingAuth) {
		this.unAccountingAuth = unAccountingAuth;
	}

	public Integer getPrintAuth() {
		return printAuth;
	}

	public void setPrintAuth(Integer printAuth) {
		this.printAuth = printAuth;
	}

	public Integer getExcelAuth() {
		return excelAuth;
	}

	public void setExcelAuth(Integer excelAuth) {
		this.excelAuth = excelAuth;
	}

	public Integer getChangeQuery() {
		return changeQuery;
	}

	public void setChangeQuery(Integer changeQuery) {
		this.changeQuery = changeQuery;
	}

	public Integer getAllQuery() {
		return allQuery;
	}

	public void setAllQuery(Integer allQuery) {
		this.allQuery = allQuery;
	}

	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
}