package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.Acl;
import com.xunpoit.oa.entity.AclCustomer;

public interface AclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Acl record);

    int insertSelective(Acl record);

    Acl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Acl record);

    int updateByPrimaryKey(Acl record);

	Acl selectAclByMain(Map<String, Object> paramMap);

	List<AclCustomer> findAllAclByMainTypeMainId(Map<String, Object> paramMap);

	List<Acl> findAllAclListByMainTypeMainId(Map<String, Object> paramMap);
}