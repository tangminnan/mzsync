package com.menzhenchaxun.information.service.menzhen.service.impl;

import com.menzhenchaxun.information.dao.menzhen.menzhenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.menzhenchaxun.information.domain.menzhen.menzhenDO;
import com.menzhenchaxun.information.service.menzhen.service.menzhenService;


@Service
public class menzhenServiceImpl implements menzhenService {

	@Autowired
	menzhenDao menzhenService;

	@Override
	public menzhenDO getMemberCard(String memberCard) {
		return menzhenService.getMemberCard(memberCard);
	}

	@Override
	public int save(menzhenDO menzhenDO) {
		return menzhenService.save(menzhenDO);
	}
	
	
}
