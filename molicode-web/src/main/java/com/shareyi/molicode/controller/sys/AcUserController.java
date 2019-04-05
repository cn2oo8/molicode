/**
 * Copyright(c) 2004-2018 bianfeng
 */


 package com.shareyi.molicode.controller.sys;


import com.shareyi.molicode.controller.AbstractController;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.service.sys.AcUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/acUser")
public class AcUserController extends AbstractController<AcUser> {

	@Resource(name="acUserService")
	private AcUserService acUserService;

	public AcUserService getService() {
	 return acUserService;
	}
}
