package com.xunpoit.oa.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xunpoit.oa.entity.Acl;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.manager.dao.AclManagerDao;
import com.xunpoit.oa.util.Permission;

/**
 * @describe:单元测试类。利用JUnit4来将此测试纳入到Spring容器中
 * @author:小豪
 * 2018年11月27日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class AclTestManager {

	@Autowired
	private AclManagerDao aclManager;
	
	@Test
	public void testAddOrUpdateAcl() {
		
		//aclManager.addOrUpdateAcl(Acl.TYPE_USER,1,1,Permission.CRUD_U,false);
		aclManager.addOrUpdateAcl(Acl.TYPE_ROLE,5,30,Permission.CRUD_R,true);
		
		System.out.println("ok了......");
	}
	
	@Test
	public void testDeleteAcl() {
		
		aclManager.delAcl(Acl.TYPE_USER,1,2);
		
		System.out.println("删除成功！");
	}
	
	@Test
	public void testUpdateExtendState() {
		
		aclManager.updateExtendState(1,1,true);//false表示继承，true表示不继承
		
		System.out.println("修改成功！");
	}
	
	@Test
	public void testFindAllAclByMainTypeMainId() {
		
		List list= aclManager.findAllAclByMainTypeMainId(Acl.TYPE_ROLE,5);
		
		for(int i=0;i<=list.size()-1;i++) {
			
			int array[] = (int[])list.get(i);
			
			System.out.print(array[0]+"  ");
			System.out.print(array[1]+"  ");
			System.out.print(array[2]+"  ");
			System.out.print(array[3]+"  ");
			System.out.print(array[4]+"  ");
			System.out.print(array[5]+"  ");
			System.out.println();
		}
	}
	
	@Test
	public void testFindAllModuleByUserId() {
		
		List<Module> list = aclManager.findAllModuleByUserId(7);
		
		for (Module module : list) {
		
			System.out.println(module.getName()+" | "+module.getId());
		}
	}
	
	@Test
	public void testGetPermission() {
		
		boolean flag = aclManager.getPermission(5,30,Permission.CRUD_R);
		
		if(flag) {
			
			System.out.println("允许操作！");
			
		}else {
			
			System.out.println("不允许操作！");
		}
	}
}