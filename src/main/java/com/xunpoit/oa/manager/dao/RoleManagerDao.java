package com.xunpoit.oa.manager.dao;

import java.util.List;

import com.xunpoit.oa.entity.Role;

/**
 * @describe:角色管理的接口，属于业务逻辑层
 * @author:小豪
 * 2018年11月26日
 */
public interface RoleManagerDao {

	public void addRole(Role role);
	
	public void removeRoleById(int id);
	
	public void modifyRole(Role role);
	
	public Role findRoleById(int id);
	
	List<Role> findAll();
}
