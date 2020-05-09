package com.menzhenchaxun.information.dao.shujuchaxun;

import org.apache.ibatis.annotations.Mapper;

import com.menzhenchaxun.information.domain.shujuchaxun.shujuchaxunDO;

@Mapper
public interface shujuchaxunDao {
	
	shujuchaxunDO getMemberId(String SMECIMemberId);

}
