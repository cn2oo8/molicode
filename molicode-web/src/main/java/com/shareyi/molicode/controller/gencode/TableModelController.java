package com.shareyi.molicode.controller.gencode;

import com.shareyi.molicode.common.vo.page.TableModelPageVo;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.conf.AcConfigService;
import com.shareyi.molicode.service.gencode.DatabaseTableService;
import com.shareyi.molicode.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

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
     * 通过表名，获取表结构
     */
    @RequestMapping("/getTableInfo")
    @ResponseBody
    public Map getTableInfo(String projectKey, String tableName) {
        return databaseTableService.getTableInfo(projectKey, tableName).getReturnMap();
    }


    /**
     * 保存表模型
     */
    @RequestMapping("/saveTableModel")
    @ResponseBody
    public Map saveTableModel(String projectKey, String tableModelJson) {
        return databaseTableService.saveTableModel(projectKey, tableModelJson).getReturnMap();
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
