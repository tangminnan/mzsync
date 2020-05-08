package com.menzhenchaxun.information.service.menzhen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.menzhenchaxun.information.domain.menzhen.menzhenDO;
import com.menzhenchaxun.information.service.menzhen.service.menzhenService;


@Service
public class menzhenServiceImpl implements menzhenService {

	@Autowired
	menzhenService menzhenService;

	@Override
	public menzhenDO getMemberCard(String memberCard) {
		return menzhenService.getMemberCard(memberCard);
	}

	@Override
	public int save(menzhenDO menzhenDO) {
		return menzhenService.save(menzhenDO);
	}
	
	
}
