package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.dao.PersonMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.manager.dao.PersonManagerDao;
import com.xunpoit.web.PageModel;

/**
 * @describe:Person的是实现类
 * @author:小豪
 * 2018年11月25日
 */
@Service
public class PersonManagerImpl implements PersonManagerDao {

	@Autowired
	private PersonMapper personMapper;//byType的自动注入
	
	@Resource(name="orgMapper")//byName的注入方式
	private OrgMapper orgMapper;
	
	@Override
	public void addPerson(Person person, int orgId) {
		
		Org org = orgMapper.selectByPrimaryKey(orgId);
		
		person.setOrg(org);//保存机构对象
		
		personMapper.insertSelective(person);//添加
    }

	@Override
	public void removePersonById(int id) {
		// TODO Auto-generated method stub
		personMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void modifyPerson(Person person) {
		// TODO Auto-generated method stub
		personMapper.updateByPrimaryKeySelective(person);
	}

	@Override
	public Person findPersonById(int id) {
		
		return personMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<Person> findAll(int offset, int pageSize) {

		PageModel<Person> pm = new PageModel<Person>();
		
		//mybatis中多个参数，用map传递
		Map<String,Integer> personMap = new HashMap<String,Integer>();
		
		personMap.put("offset",offset);
		
		personMap.put("pageSize",pageSize);
		
		List<Person> dataList = personMapper.findAll(personMap);
		
		pm.setDataList(dataList);
		
		pm.setPageSize(pageSize);
		
		//查询总条数
		long items = personMapper.selectCount();
				
	    pm.setItems((int)items);
		
		return pm;
	}

}
