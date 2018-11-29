package com.xunpoit.oa.manager.dao;

import java.util.List;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.web.PageModel;

/**
 * @describe:模块的接口
 * @author:小豪
 * 2018年11月26日
 */
public interface ModuleManagerDao {

	public void addModule(Module module,int pid);
	
	public void removeModuleById(int id);
	
	public void modifyModule(Module module);
	
	public Module findModuleById(int id);
	
	public List<Module> selectChildListByParent(int pid);
	
	public PageModel<Module> findAll(int pid,int offset,int pageSize);
}

