package com.xunpoit.oa.entity;

import java.util.List;

/**
 * @describe:组织机构实体类，树形结构：父子结构的关系
 * @author:小豪
 * 2018年11月23日
 */
public class Org {
    private Integer id;

    private String name;

    //机构编码，生成规则，如果是顶级机构，那么编码等于id
    //如果不是顶级机构，那么编码等于父机构编码+“_”+id
    private String sn;

    //对于机构的描述
    private String description;

    //父类机构，子对父的关系，多对一的关系
    private Org parent;
    
    //子类机构
    private List<Org> child;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	public List<Org> getChild() {
		return child;
	}

	public void setChild(List<Org> child) {
		this.child = child;
	}

}