package com.menzhenchaxun.information.service.shujuchaxun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.menzhenchaxun.information.domain.shujuchaxun.shujuchaxunDO;
import com.menzhenchaxun.information.service.shujuchaxun.service.shujuchaxunService;

@Service
public class shujuchaxunServiceImpl implements shujuchaxunService {

	@Autowired
	shujuchaxunService shujuchaxunService;

	@Override
	public shujuchaxunDO getMemberId(String SMECIMemberId) {
		return shujuchaxunService.getMemberId(SMECIMemberId);
	}
	
}
