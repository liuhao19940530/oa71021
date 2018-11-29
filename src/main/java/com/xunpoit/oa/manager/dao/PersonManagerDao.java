package com.xunpoit.oa.manager.dao;

import com.xunpoit.oa.entity.Person;
import com.xunpoit.web.PageModel;

/**
 * @describe:Person的crud接口
 * @author:小豪
 * 2018年11月25日
 */
public interface PersonManagerDao {

	public void addPerson(Person person,int orgId);
	
	public void removePersonById(int id);
	
	public void modifyPerson(Person person);
	
	public Person findPersonById(int id);
	
	//分页查询
	public PageModel<Person> findAll(int offset,int pageSize);
	
}
