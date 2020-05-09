package com.menzhenchaxun.information.dao.menzhen;

import org.apache.ibatis.annotations.Mapper;

import com.menzhenchaxun.information.domain.menzhen.menzhenDO;

@Mapper
public interface menzhenDao {
	
	menzhenDO getMemberCard(String memberCard);
	
	int save(menzhenDO menzhenDO);

}
