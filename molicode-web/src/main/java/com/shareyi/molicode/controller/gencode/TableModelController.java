package com.shareyi.molicode.controller.gencode;

import com.shareyi.molicode.common.constants.AutoCodeConstant;
import com.shareyi.molicode.common.constants.ConfigKeyConstant;
import com.shareyi.molicode.common.enums.DataTypeEnum;
import com.shareyi.molicode.common.utils.BindResourceUtil;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.vo.code.SimpleTableInfoVo;
import com.shareyi.molicode.common.vo.page.TableModelPageVo;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.conf.AcConfigService;
import com.shareyi.molicode.service.gencode.DatabaseTableService;
import com.shareyi.molicode.web.base.BaseController;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/autoCode/tableModel")
public class TableModelController extends BaseController {

    @Resource
    DatabaseTableService databaseTableService;

    @Resource
    AcConfigService acConfigService;

    /**
     * 生成tableModel
     */
    @RequestMapping("/getTableList")
    @ResponseBody
    public Map getTableList(String projectKey) {
        return databaseTableService.getTableList(projectKey).getReturnMap();
    }


    /**
     * 生成tableModel
     */
    @ResponseBody
    @RequestMapping("/makeTableModel")
    public Map makeTableModel(TableModelPageVo tableModelPageVo) {
        CommonResult result = databaseTableService.generateTableModel(tableModelPageVo);
        return result.getReturnMap();
    }

}
