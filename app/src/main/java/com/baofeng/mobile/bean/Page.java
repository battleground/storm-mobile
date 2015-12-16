package com.baofeng.mobile.bean;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int total;// 视频总数
	public int page;// 视频当前页；
	public int pageSize; // 每页的数量
	public String pages; // 视频总页数

	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

//	public int getPages() {
//		return pages;
//	}

//	public void setPages(int pages) {
//		this.pages = pages;
//	}

	@Override
	public String toString() {
		return "VideoListModel [total=" + total + ", page=" + page
				+ ", pageSize=" + pageSize + ", pages=" + pages + ", list="
				+ list + "]";
	}
}
