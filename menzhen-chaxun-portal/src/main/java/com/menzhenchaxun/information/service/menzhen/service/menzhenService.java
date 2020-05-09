package com.menzhenchaxun.information.service.menzhen.service;

import com.menzhenchaxun.information.domain.menzhen.menzhenDO;

public interface menzhenService {
	
	menzhenDO getMemberCard(String memberCard);
	
	int save(menzhenDO menzhenDO);

}
