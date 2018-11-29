package com.xunpoit.oa.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.manager.dao.AclManagerDao;

/**
 * @describe:作为函数类
 * @author:小豪
 * 2018年11月28日
 */
@Service
public class SecurityFunction {

	private static AclManagerDao aclManager;
	
	public AclManagerDao getAclManager() {
		return aclManager;
	}

	//注入不能写在静态常量上
	@Autowired
	public void setAclManager(AclManagerDao aclManager) {
		SecurityFunction.aclManager = aclManager;
	}

	//静态的即时验证的方法
	//描述为jstl函数调用的方法
	public static boolean getPermission(int userId,String sn,int permission) {
		
		return aclManager.getPermission(userId, sn, permission);

	}
	
}
