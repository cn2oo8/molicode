package com.shareyi.molicode.controller.gencode;

import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.vo.page.ReplaceParams;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.replace.CopyAndReplaceService;
import com.shareyi.molicode.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 *  文件内容及目录替换功能
 */
@Controller
@RequestMapping("/autoCode/replace")
public class ReplaceController extends BaseController {


	@Resource
	CopyAndReplaceService copyAndReplaceService;

	@RequestMapping("/doReplace")
	@ResponseBody
	@UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
	public Map doReplace(ReplaceParams replaceParams){
		CommonResult result=copyAndReplaceService.doCopyAndReplace(replaceParams);
		return result.getReturnMap();
	}

}
