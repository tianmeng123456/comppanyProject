package com.boyo.wuhang.ultity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class Pager implements Serializable {

	@ApiModelProperty(value = "分页列表", hidden = true)
	private List list;
	@ApiModelProperty(value = "页码 默认1")
	private int pageNumber = 1;
	@ApiModelProperty(value = "页大小 默认20")
	private int pageSize = 20; //设置默认的pageSize;
	@ApiModelProperty(value = "总页数")
	private int totalPage;
	@ApiModelProperty(value = "总行数")
	private int totalRow;
	@ApiModelProperty(value = "分页起始", hidden = true)
	private int start;
	@ApiModelProperty(value = "分页结尾", hidden = true)
	private int end;    //no-sql型数据库分页用到
	@ApiModelProperty(value = "分页类型", hidden = true)
	private int pageType = 0;   //0：关系型数据库分页  1：no-sql型数据库分页

	public Pager() {
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getPageType() {
		return pageType;
	}

	public void setPageType(int pageType) {
		this.pageType = pageType;
	}

	public Pager(List list, Integer pageNumber, Integer pageSize, int totalRow) {
		this.list = list;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize == 0 ? 20 : pageSize;
		this.totalPage = (totalRow + pageSize) % pageSize;
		this.totalRow = totalRow;
	}

	public int getStart() {
		return this.pageType == 0 ? ((pageNumber - 1) * pageSize) : this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		this.setStart((pageNumber - 1) * pageSize);
	}

	public Integer getPageSize() {
		return this.pageType == 1 ? end - start : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.setStart((this.pageNumber - 1) * pageSize);
	}

	public int getTotalPage() {
		if (totalPage == 0) {
			return 1;
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		this.totalPage = this.totalRow / this.pageSize;
		if (totalRow % pageSize != 0) this.totalPage += 1;
	}

	public boolean isFirstPage() {
		return this.pageNumber == 1;
	}

	public boolean isLastPage() {
		return this.pageNumber == this.totalPage;
	}
}
