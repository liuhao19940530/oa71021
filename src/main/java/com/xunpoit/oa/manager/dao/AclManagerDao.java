package com.xunpoit.oa.manager.dao;

import java.util.List;

import com.xunpoit.oa.entity.Module;

/**
 * @describe:授权相关的接口
 * @author:小豪
 * 2018年11月26日
 */
public interface AclManagerDao {

	//添加或更新访问控制权限，先查询后更新
	public void addOrUpdateAcl(String mainType,int mainId,int moduleId,int permission,boolean yes);
	
	//删除，没有id来辨识
	public void delAcl(String mainType,int mainId,int moduleId);
	
	//更新继承状态，当yes为true时，表示不继承，此时为0，为false时为继承，此时为1
	public void updateExtendState(int userId,int moduleId,boolean yes);
	
	//初始化授权页面的方法
	public List findAllAclByMainTypeMainId(String mainType,int mainId);
	
	//登录后，查询出该用户所有具有的读取权限的模块
	public List<Module> findAllModuleByUserId(int userId);
	
	//做即时认证
	public boolean getPermission(int userId,int moduleId,int permission);
	
	//重载
	public boolean getPermission(int userId,String moduleSn,int permission);
}
