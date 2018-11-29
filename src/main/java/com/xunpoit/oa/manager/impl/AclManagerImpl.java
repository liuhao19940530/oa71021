package com.xunpoit.oa.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.AclMapper;
import com.xunpoit.oa.dao.ModuleMapper;
import com.xunpoit.oa.dao.UsersRolesMapper;
import com.xunpoit.oa.entity.Acl;
import com.xunpoit.oa.entity.AclCustomer;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Role;

import com.xunpoit.oa.manager.dao.AclManagerDao;

/**
 * @describe:AclManager的实现类
 * @author:小豪
 * 2018年11月27日
 */@Service("aclManager")
public class AclManagerImpl implements AclManagerDao {

    @Autowired
	private AclMapper aclMapper;
    
    @Resource
    private UsersRolesMapper ursMapper;
    
    //模块的引用
    @Autowired
    private ModuleMapper moduleMapper;
    
    //将可能多处使用的重复代码，封装成方法，方便多次使用
    private Acl findAcl(String mainType,int mainId,int moduleId) {
    	
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	
    	paramMap.put("mainType",mainType);
    	
    	paramMap.put("mainId",mainId);
    	
    	paramMap.put("moduleId",moduleId);
    	
    	Acl acl = aclMapper.selectAclByMain(paramMap);
    	
    	return acl;
   
    }

	@Override
	public void addOrUpdateAcl(String mainType, int mainId, int moduleId, int permission, boolean yes) {
	
		Acl acl = this.findAcl(mainType, mainId, moduleId);
		
		if(acl != null) {//存在就是更新
			
			acl.setPermission(permission,yes);
			
			aclMapper.updateByPrimaryKeySelective(acl);
			
			return;
		}
		
		//不存在就是添加
		acl = new Acl();
		
		//保存内容
		acl.setMainType(mainType);
		
		acl.setMainId(mainId);
		
		acl.setModuleId(moduleId);
		
		acl.setPermission(permission,yes);
		
		//添加的方法
		aclMapper.insertSelective(acl);
	}

	@Override
	public void delAcl(String mainType, int mainId, int moduleId) {

		//查询是否存在
		Acl acl = this.findAcl(mainType, mainId, moduleId);
		
		if(acl != null) {
			
			aclMapper.deleteByPrimaryKey(acl.getId());
		}

	}

	@Override
	public void updateExtendState(int userId, int moduleId, boolean yes) {
		// TODO Auto-generated method stub
		Acl acl = this.findAcl(Acl.TYPE_USER,userId,moduleId);
		
		if(acl != null) {
			
			acl.setExtendState(yes?0:1);//如果yes是true就表示不继承为0，继承就为1
			
			aclMapper.updateByPrimaryKeySelective(acl);
		}
		
	}

	/*
     *查询语句
     *select module_id,acl_state&1,acl_state&2,acl_state&4,acl_state&8,extend_state from t_acl where main_type='Role' and main_id = 1
	 */
	@Override
	public List findAllAclByMainTypeMainId(String mainType, int mainId) {

		Map<String,Object> paramMap = new HashMap<>();
		
		paramMap.put("mainType",mainType);
		
		paramMap.put("mainId",mainId);
		
		List<AclCustomer> aclCustomerList = aclMapper.findAllAclByMainTypeMainId(paramMap);
		
		//这个返回的集合，将一个个 一维数组保存
		List list = new ArrayList();
		
		//将从数据库中取出来的数据，一个个的遍历赋值给一维数组中
		for (AclCustomer aclCustomer : aclCustomerList) {
			
			int array[] = new int[6];//一个元素长度为6的一维数组
			
			array[0] = aclCustomer.getModuleId();
			
			array[1] = aclCustomer.getCrudCreate();
			
			array[2] = aclCustomer.getCrudRead();
			
			array[3] = aclCustomer.getCrudUpdate();
			
			array[4] = aclCustomer.getCrudDelete();
			
			array[5] = aclCustomer.getExtendState();
			
			//添加进集合中
			list.add(array);
		}
		
		return list;
	}

	@Override
	/*
	 *1.查询用户拥有的角色的所有授权，降序排列（优先级高的覆盖优先级低的）
	 *2.查询用户的所有授权，并与1中的授权进行合并（用户权限最高）
	 *3.去除那些没有读取权限的模块
	 *4.返回有读取权限的module模块集合
	 */
	public List<Module> findAllModuleByUserId(int userId) {
		
		//第一步
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		Map<Integer,Acl> aclMap = new HashMap<Integer,Acl>();
		
		map.put("userId",userId);
		
		map.put("desc",1);//定义的方法中，1表示降序
		
		List<Role> roleList = ursMapper.selectRoleListByUser(map);
		
		for(Role role:roleList) {
			
			Map<String,Object> paramMap = new HashMap<>();
			
			paramMap.put("mainType",Acl.TYPE_ROLE);
			
			paramMap.put("mainId",role.getId());
			//返回所有的角色对象的权限
			List<Acl> aclList = aclMapper.findAllAclListByMainTypeMainId(paramMap);
			
			for (Acl acl : aclList) {
				
				aclMap.put(acl.getModuleId(),acl);
			}
		}
		
		//第二步
		Map<String,Object> paramMap = new HashMap<>();
		
		paramMap.put("mainType",Acl.TYPE_USER);
		
		paramMap.put("mainId",userId);
		
		List<Acl> list = aclMapper.findAllAclListByMainTypeMainId(paramMap);
		
		for (Acl acl : list) {
			
			aclMap.put(acl.getModuleId(),acl);
		}
		
		Set<Entry<Integer,Acl>> entrySet = aclMap.entrySet();
		
		//用来保存没有读取权限的 模块的key
		List<Integer> keyList = new ArrayList<Integer>();
		
		//第三步，去除那些没有读取权限的模块
	    for(Iterator<Entry<Integer,Acl>> it = entrySet.iterator();it.hasNext();){
	    	
	    	Entry<Integer, Acl> its = it.next();
	    	
	    	int key = its.getKey();
	    	
	    	Acl acl = its.getValue();
	    
	    	int permission = acl.getPermission(1);
			
	    	if(permission!=Acl.ACL_YES) {
	    		
	    		keyList.add(key);
	    	}
		}
		
	    //移除没有读取权限的模块
	    for (int moduleId : keyList) {
			
	    	aclMap.remove(moduleId);
		}
	    
	    Set<Integer> set = aclMap.keySet();
	    
	    //MyBatis不接受set集合，所以需要转换为list传递
	    List<Integer> idList = new ArrayList<Integer>(set);
	    
	    //第四步
		List<Module> moduleList = moduleMapper.findAllModuleListByKey(idList);
	    
		return moduleList;
	}

	/*
	 * 做即时认证的
	 * 1.首先查询，是否授权给用户，如果授权就使用
	 * 2.如果没有直接授权给用户，那么就逐个查询其所拥有的角色的授权（按照角色的优先级升序查询）
	 *(non-Javadoc)
	 * @see com.xunpoit.oa.manager.dao.ACLManagerDao#getPermission(int, int, int)
	 */
	@Override
	public boolean getPermission(int userId, int moduleId, int permission) {

		Acl acl = this.findAcl(Acl.TYPE_USER,userId, moduleId);
		
		if(acl != null) {//表示有单独授权
			
			if(acl.getPermission(permission) != Acl.ACL_NEUTRAL) {//如果不等于不确定权限，要么就是继承，要么就是不继承
				
				//1是继承权限，0是不继承
				return acl.getPermission(permission) == 1?true:false;
			}
		}
		
		//能够执行到这里就说明没有单独授权，或者说授权不确定
		//查询该用户所拥有的所有角色，按照优先级升序
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		map.put("userId",userId);
		
		map.put("desc",0);//0表示升序，1表示降序
		
		List<Role> roleList = ursMapper.selectRoleListByUser(map);
		
		//逐个查询所有的角色，是否对此模块有授权
		for (Role role : roleList) {
			
			acl = this.findAcl(Acl.TYPE_ROLE,role.getId(), moduleId);
			
			if(acl!=null) {
				
			   return acl.getPermission(permission)==1?true:false;
			}   
		}
		
		return false;
	}

	@Override
	public boolean getPermission(int userId, String moduleSn, int permission) {

        int moduleId = moduleMapper.findModuleBySn(moduleSn);
		
		return this.getPermission(userId, moduleId, permission);
	}

}
