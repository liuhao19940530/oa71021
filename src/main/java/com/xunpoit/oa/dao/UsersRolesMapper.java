package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;

public interface UsersRolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UsersRoles record);

    int insertSelective(UsersRoles record);

    UsersRoles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UsersRoles record);

    int updateByPrimaryKey(UsersRoles record);

	List<UsersRoles> selectUsersRolesListByUser(int userId);

	//查询用户的所有角色，参数desc如果是0.就是升序，如果是1，就是降序
	List<Role> selectRoleListByUser(Map<String, Integer> map);
}