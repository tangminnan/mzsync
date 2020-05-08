package com.menzhenchaxun.common.controller;

import org.springframework.stereotype.Controller;

import com.menzhenchaxun.common.utils.ShiroUtils;
import com.menzhenchaxun.owneruser.domain.OwnerUserDO;
import com.menzhenchaxun.system.domain.UserToken;

@Controller
public class BaseController {
	public OwnerUserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
	public Long getforIds() {
		return getUser().getUserId();
	}
}