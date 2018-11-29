package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.exception.MyException;
import com.xunpoit.oa.manager.dao.OrgManagerDao;
import com.xunpoit.web.PageModel;

/**
 * @describe:Org管理接口的实现类
 * @author:小豪
 * 2018年11月23日
 */

@Service//可以不用写名字，因为是根据byType装配，此注解是为了配合spring.xml文件中的全文扫描，将此类的实例纳入spring的管理中
public class OrgManagerImpl implements OrgManagerDao {

	//byType类型的装配方式
	@Autowired
	private OrgMapper orgMapper;

	@Override
	/**
	 * 添加,pid是0.表示顶级机构，否则就在pid下面直接添加子机构
	 */
	public void addOrg(Org org, int pid) {
		
		if(pid > 0) {
			
			Org parent = orgMapper.selectByPrimaryKey(pid);
			
			org.setParent(parent);
		}
		
		orgMapper.insertSelective(org);
		
		//更新sn（机构编号）
		String sn = org.getId()+"";
		
		if(pid > 0) {
			
			Org parent = orgMapper.selectByPrimaryKey(pid);
			
			sn = parent.getSn()+"_"+org.getId();
			
		}
		
		org.setSn(sn);
		
		//更新
		orgMapper.updateByPrimaryKeySelective(org);
	}

	@Override/*
			删除，如果有子机构，则不能直接删除该机构
	          */
	public void removeOrgById(int id) {
		//判断如果有子机构，不可以直接删除，没有子机构的话，可以直接删除
		
		Org org = orgMapper.selectByPrimaryKey(id);
		
		//如果有子机构
		if(org.getChild().size() > 0) {
			
			throw new MyException("旗下拥有子机构！不可以直接删除！");
			
		}
			
		orgMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	/*
	 * 修改，表示MyBatis的动态sql的修改方法
	 */
	public void modifyOrg(Org org) {
		// TODO Auto-generated method stub
		orgMapper.updateByPrimaryKeySelective(org);
	}

	@Override
	/*
	 * 查询一个对象的方法
	 */
	public Org queryOrgById(int id) {
		// TODO Auto-generated method stub
		return orgMapper.selectByPrimaryKey(id);
	}

	@Override/*
	查询所有对象的方法，pid是0，表示查询所有机构，否则是对应父机构下的所有子机构
	*/
	public PageModel<Org> queryAll(int pid,int offset,int pageSize) {
		
		PageModel<Org> pm = new PageModel<Org>();
		
		// 如何为mybatis传递多个参数，使用Map
		Map<String,Integer> paramMap = new HashMap<String,Integer>();
		
		paramMap.put("pid",pid);
		
		paramMap.put("offset",offset);
		
		paramMap.put("pageSize",pageSize);
		
		List<Org> orgList = orgMapper.findAllByParent(paramMap);
		
		pm.setDataList(orgList);
		
		pm.setPageSize(pageSize);
		
		//总条数需要查询
		long items = orgMapper.selectCount(pid);
		
		pm.setItems((int)items);
		
		return pm;
	}

}
