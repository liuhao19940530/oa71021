package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.Org;

/**
 * @describe:
 * @author:小豪
 * 2018年11月23日
 * OrgDao接口-->接口的实现类是OrgDaoImpl（不需要实现，使用mapper动态代理）
 * 要求OrgMapper.xml中的namespace必须指定为Dao对应的全路径
 */
public interface OrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);

    //3个参数:pid,offset,pageSize
	List<Org> findAllByParent(Map<String,Integer> paramMap);

	//根据pid查询总条数的方法
	long selectCount(int pid);
}