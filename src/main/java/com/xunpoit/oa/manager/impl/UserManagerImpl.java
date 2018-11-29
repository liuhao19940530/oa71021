package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.PersonMapper;
import com.xunpoit.oa.dao.UserMapper;
import com.xunpoit.oa.dao.UsersRolesMapper;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.manager.dao.UserManagerDao;
import com.xunpoit.web.PageModel;

/**
 * @describe:用户的实现类
 * @author:小豪
 * 2018年11月26日
 */
@Service
public class UserManagerImpl implements UserManagerDao {

	@Autowired
	private UserMapper userMapper;
	
	@Resource
	private PersonMapper personMapper;
	
	@Autowired
	private UsersRolesMapper ursMapper;
	
	@Override
	public void addUser(User user, int personId) {
		// 根据person_id查询出对应的人员
        Person person = personMapper.selectByPrimaryKey(personId);
        
        user.setPerson(person);//设置进user信息
        
        userMapper.insertSelective(user);
	}

	@Override
	public void removeUserById(int id) {
		// TODO Auto-generated method stub
        userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void modifyUser(User user) {
		// TODO Auto-generated method stub
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public User findUserById(int id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<Person> findAll(int offset, int pageSize) {

		//用户的分页，实际是查询人员信息
		PageModel<Person> pm = new PageModel<Person>();
		
		Map<String,Integer> paramMap = new HashMap<String,Integer>();
		
		paramMap.put("offset",offset);
		
		paramMap.put("pageSize",pageSize);
		
		List<Person> dataList = personMapper.findAll(paramMap);
		
		pm.setDataList(dataList);
		
		pm.setPageSize(pageSize);
		
		long items = personMapper.selectCount();
		
		pm.setItems((int)items);
		
		return pm;
	}

	@Override
	public List<UsersRoles> findUrsListByUser(int userId) {
		//通过用户查询对应的所有的角色
		return ursMapper.selectUsersRolesListByUser(userId);
	}

	@Override
	public void addUsersRoles(UsersRoles urs) {
		// TODO Auto-generated method stub
		ursMapper.insertSelective(urs);
	}

	@Override
	public void removeUsersRolesById(int ursId) {
		// TODO Auto-generated method stub
		ursMapper.deleteByPrimaryKey(ursId);
	}

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return userMapper.login(user);
	}

}
