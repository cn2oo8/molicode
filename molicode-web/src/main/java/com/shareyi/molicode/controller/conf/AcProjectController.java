/**
 * Copyright(c) 2004-2018 bianfeng
 */


 package com.shareyi.molicode.controller.conf;


import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.controller.AbstractController;
import com.shareyi.molicode.domain.conf.AcProject;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.service.conf.AcProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/conf/acProject")
public class AcProjectController extends AbstractController<AcProject> {

	@Resource(name="acProjectService")
	private AcProjectService acProjectService;

	@RequestMapping(value="add",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	@UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
	@Override
	public Map add(AcProject acProject) {
		return super.add(acProject);
	}


	@RequestMapping(value="update",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	@UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
	@Override
	public Map update(AcProject acProject) {
		return super.update(acProject);
	}

	@RequestMapping(value="delete",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	@UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
	@Override
	public Map delete(Long primaryKey) {
		return super.delete(primaryKey);
	}

	@RequestMapping(value="getByProjectKey",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map getByProjectKey(AcProject acProject){
		return  getService().getByProjectKey(acProject).getReturnMap();
	}




	@RequestMapping(value="copyProject",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	@UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
	public Map copyProject(AcProject acProject){
		return  getService().copyProject(acProject).getReturnMap();
	}


	public AcProjectService getService() {
	 return acProjectService;
	}
}
