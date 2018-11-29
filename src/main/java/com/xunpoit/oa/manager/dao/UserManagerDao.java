package com.xunpoit.oa.manager.dao;

import java.util.List;

import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.web.PageModel;

/**
 * @describe:用户的接口类
 * @author:小豪
 * 2018年11月26日
 */
public interface UserManagerDao {

	public void addUser(User user,int personId);
	
	public void removeUserById(int id);
	
	public void modifyUser(User user);
	
	public User findUserById(int id);
	
	public PageModel<Person> findAll(int offset,int pageSize);
	
	//给用户分配角色之前，首先查询该用户已经被分配的所有角色信息
	public List<UsersRoles> findUrsListByUser(int userId);
	
	public void addUsersRoles(UsersRoles urs);
	
	public void removeUsersRolesById(int ursId);
	
	//登录时的方法
	public User login(User user);
}
