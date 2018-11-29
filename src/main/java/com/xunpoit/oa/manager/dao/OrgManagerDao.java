package com.xunpoit.oa.manager.dao;

import java.util.List;

import com.xunpoit.oa.entity.Org;
import com.xunpoit.web.PageModel;

/**
 * @describe:实现类的接口
 * @author:小豪
 * 2018年11月23日
 */
public interface OrgManagerDao {

	//添加,pid是0.表示顶级机构
	public void addOrg(Org org,int pid);
	
	//删除，如果有子机构，则不能直接删除该机构
	public void removeOrgById(int id);
	
	//修改
	public void modifyOrg(Org org);
	
	//查询一个对象的方法
	public Org queryOrgById(int id);
	
	//分页查询所有对象的方法，pid是0，表示查询所有机构，否则是对应父机构下的所有子机构
	public PageModel<Org> queryAll(int pid,int offset,int pageSize);

}
