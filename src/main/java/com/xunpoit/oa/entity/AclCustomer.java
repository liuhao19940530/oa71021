package com.xunpoit.oa.entity;

/**
 * @describe:MyBatis中不能返回二维数组形式的结果集，就用此实体类来封装替代
 * 但是Hibernate的原生sql语句却可以返回此结果集
 * @author:小豪
 * 2018年11月27日
 */
public class AclCustomer {

	//6个属性来接收
	private int moduleId;
	
	private int crudCreate;
	
	private int crudRead;
	
	private int crudUpdate;
	
	private int crudDelete;
	
	private int extendState;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getCrudCreate() {
		return crudCreate;
	}

	public void setCrudCreate(int crudCreate) {
		this.crudCreate = crudCreate;
	}

	public int getCrudRead() {
		return crudRead;
	}

	public void setCrudRead(int crudRead) {
		this.crudRead = crudRead;
	}

	public int getCrudUpdate() {
		return crudUpdate;
	}

	public void setCrudUpdate(int crudUpdate) {
		this.crudUpdate = crudUpdate;
	}

	public int getCrudDelete() {
		return crudDelete;
	}

	public void setCrudDelete(int crudDelete) {
		this.crudDelete = crudDelete;
	}

	public int getExtendState() {
		return extendState;
	}

	public void setExtendState(int extendState) {
		this.extendState = extendState;
	}

}
