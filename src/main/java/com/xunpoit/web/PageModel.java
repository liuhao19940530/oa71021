package com.xunpoit.web;

import java.util.List;

/**
 * @describe:分页模型
 * @author:小豪
 * 2018年11月24日
 */
public class PageModel<E> {

	private int items;//总条数
	
	private int pageSize;//每页显示的条数
	
	private List<E> dataList;//保存数据的集合

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<E> getDataList() {
		return dataList;
	}

	public void setDataList(List<E> dataList) {
		this.dataList = dataList;
	}
	
}
