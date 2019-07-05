package com.shareyi.molicode.controller.gencode;

import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.vo.page.TableModelPageVo;
import com.shareyi.molicode.common.web.CommonResult;
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

    /**
     * 获取数据库表列表
     */
    @RequestMapping("/getTableList")
    @ResponseBody
    @UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
    public Map getTableList(String projectKey) {
        return databaseTableService.getTableList(projectKey).getReturnMap();
    }

    /**
     * 通过建表语句获取数据库表
     */
    @RequestMapping("/getTableListBySql")
    @ResponseBody
    public Map getTableListBySql(String projectKey, String createSql) {
        return databaseTableService.getTableListBySql(projectKey, createSql).getReturnMap();
    }

    /**
     * 通过表名，获取表结构
     */
    @RequestMapping("/getTableInfo")
    @ResponseBody
    public Map getTableInfo(TableModelPageVo tableModelPageVo) {
        return databaseTableService.getTableInfo(tableModelPageVo).getReturnMap();
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
    @Deprecated
    @ResponseBody
    @RequestMapping("/makeTableModel")
    public Map makeTableModel(TableModelPageVo tableModelPageVo) {
        CommonResult result = databaseTableService.generateTableModel(tableModelPageVo);
        return result.getReturnMap();
    }

}
