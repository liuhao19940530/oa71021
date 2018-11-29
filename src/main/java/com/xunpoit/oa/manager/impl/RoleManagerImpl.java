package com.xunpoit.oa.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.RoleMapper;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.dao.RoleManagerDao;

/**
 * @describe:角色的实现类
 * @author:小豪
 * 2018年11月26日
 */
@Service
public class RoleManagerImpl implements RoleManagerDao{

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public void addRole(Role role) {
		// TODO Auto-generated method stub
		roleMapper.insertSelective(role);
	}

	@Override
	public void removeRoleById(int id) {
		// TODO Auto-generated method stub
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void modifyRole(Role role) {
		// TODO Auto-generated method stub
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public Role findRoleById(int id) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> findAll() {//此处不分页
		// TODO Auto-generated method stub
		return roleMapper.findAll();
	}
}
