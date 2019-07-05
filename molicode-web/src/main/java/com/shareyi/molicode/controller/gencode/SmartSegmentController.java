package com.shareyi.molicode.controller.gencode;

import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.vo.page.SmartSegmentPageVo;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.replace.SmartSegmentService;
import com.shareyi.molicode.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 智能片段处理器
 *
 * @author david
 * @date 2019/5/19
 */
@Controller
@RequestMapping("/autoCode/smartSegment")
public class SmartSegmentController extends BaseController {


    @Resource
   private SmartSegmentService smartSegmentService;

    @RequestMapping("/execute")
    @ResponseBody
    @UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
    public Map execute(SmartSegmentPageVo segmentPageVo) {
        CommonResult result = smartSegmentService.execute(segmentPageVo);
        return result.getReturnMap();
    }

}
