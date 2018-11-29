package com.xunpoit.oa.entity;

import java.util.List;

public class Module {
    private Integer id;

    private String name;

    private String sn;

    private String url;
    
    private Integer orderNo;

    private Module parent;

    private List<Module> childList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public List<Module> getChildList() {
		return childList;
	}

	public void setChildList(List<Module> childList) {
		this.childList = childList;
	}	
	
}