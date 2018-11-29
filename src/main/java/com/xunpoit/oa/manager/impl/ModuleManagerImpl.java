package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.ModuleMapper;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.manager.dao.ModuleManagerDao;
import com.xunpoit.web.PageModel;

/**
 * @describe:模块相关的实现类
 * @author:小豪
 * 2018年11月26日
 */
@Service
public class ModuleManagerImpl implements ModuleManagerDao {

	//完成crud的对象
	@Autowired
	@Qualifier("moduleMapper")//byName的注入方式
	private ModuleMapper moduleMapper;
	
	@Override
	public void addModule(Module module, int pid) {
		// TODO Auto-generated method stub
		//添加的方法，首先判断是否是二级模块
		if(pid > 0) {//是二级模块
			
			//找到对应的一级父模块
			Module parent = moduleMapper.selectByPrimaryKey(pid);
			
			module.setParent(parent);
		}
		
		moduleMapper.insertSelective(module);//添加
	}

	@Override
	public void removeModuleById(int id) {
		// TODO Auto-generated method stub
		moduleMapper.deleteByPrimaryKey(id);

	}

	@Override
	public void modifyModule(Module module) {
		// TODO Auto-generated method stub
		moduleMapper.updateByPrimaryKeySelective(module);
	}

	@Override
	public Module findModuleById(int id) {
		
		return moduleMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<Module> findAll(int pid, int offset, int pageSize) {

		PageModel<Module> pm = new PageModel<>();
		
		Map<String,Integer> paramMap = new HashMap<>();
		
		paramMap.put("pid",pid);
		
		paramMap.put("offset",offset);
		
		paramMap.put("pageSize",pageSize);
		
		List<Module> dataList = moduleMapper.findAll(paramMap);
		
		pm.setDataList(dataList);
		
		pm.setPageSize(pageSize);
		
		long items = moduleMapper.selectCount();
		
		pm.setItems((int)items);
		
		return pm;
	}

	@Override
	public List<Module> selectChildListByParent(int pid) {
		// TODO Auto-generated method stub
		return moduleMapper.selectChildListByParent(pid);
	}

}
