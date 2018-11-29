package com.xunpoit.oa.entity;

public class Acl {
	
	//相关常量的定义
	public static final String TYPE_ROLE = "ROLE";//角色
	
	public static final String TYPE_USER = "USER";//用户
	
	public static final int ACL_YES = 1;//有权限
	
	public static final int ACL_NO = 0;//无权限
	
	public static final int ACL_NEUTRAL = -1;//暂时不确定权限
	
    private Integer id;

    private String mainType;

    private int mainId;

    private int moduleId;

    private int aclState;

    private int extendState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType == null ? null : mainType.trim();
    }

	public int getMainId() {
		return mainId;
	}

	public void setMainId(int mainId) {
		this.mainId = mainId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getAclState() {
		return aclState;
	}

	public void setAclState(int aclState) {
		this.aclState = aclState;
	}

	public int getExtendState() {
		return extendState;
	}

	public void setExtendState(int extendState) {
		this.extendState = extendState;
	}

	//授权方法，利用二进制的按位或，按位与，取反的操作
	public void setPermission(int permission,boolean flag) {
		
		//用来左移的变量
		int temp = 1;
		
		temp = temp<<permission;//左移的位数根据传递的数字来确定
		
		if(flag) {//如果是true
			
			aclState = aclState|temp;//只要有一个为1，就是1
			
		}else {
			
			temp = ~temp;//二进制取反操作
			
			aclState = aclState&temp;//必须2个都为1，才是1
		}
	}
	
	//认证的方法
	public int getPermission(int permission) {
		
		if(extendState==1) {
			
			//如果是继承，那么需要进一步去验证它的角色的权限
			return Acl.ACL_NEUTRAL;
		}
		
		int temp = 1;
		
		temp <<= permission;
		
		temp &= aclState;
		
		if(temp>0) {
			
			return Acl.ACL_YES;
			
		}else {
			
			return Acl.ACL_NO;
		}
	}
}