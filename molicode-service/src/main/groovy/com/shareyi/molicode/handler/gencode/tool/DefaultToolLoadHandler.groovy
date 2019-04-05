package com.shareyi.molicode.handler.gencode.tool

import com.alibaba.fastjson.JSON
import com.google.common.collect.Maps
import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.ToolLoadHandlerAware
import com.shareyi.molicode.common.constants.AutoCodeConstant
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.utils.*
import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.vo.code.TableModelVo
import com.shareyi.molicode.common.context.MoliCodeContext
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.MapUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

/**
 * 默认的工具加载器
 *
 * @author zhangshibin
 * @since 2018/10/4
 */
@Service
class DefaultToolLoadHandler extends SimpleHandler<MoliCodeContext> implements ToolLoadHandlerAware {

    @Override
    int getOrder() {
        return 1;
    }

    @Override
    boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    void doHandle(MoliCodeContext context) {

        AutoMakeVo autoMake = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        AutoCodeParams autoCodeParams = context.autoCodeParams;
        TableModelVo tableModel = context.get(MoliCodeConstant.CTX_KEY_TABLE_MODEL);
        /*******获取配置文件相关值 start********/
        //获取公共字典项map
        String dictFilePath = autoMake.getProp(AutoCodeConstant.DICT_PATH);
        Map dictMap = XmlUtils.getDictMap(dictFilePath);

        //获取片段代码路径
        String snippetTemplatePath = autoMake.getProp(AutoCodeConstant.SNIPPET_TEMPLATE_PATH);
        Map snippetTemplateMap = XmlUtils.getSnippetTemplateMap(snippetTemplatePath);

        //获取片段text路径
        String extendTextConfPath = autoMake.getProp(AutoCodeConstant.EXTEND_TEXT_CONFIG_PATH);
        Map extendConfigMap = XmlUtils.readKeyMapFile(extendTextConfPath);

        //获取数据库到Java类型映射
        String db2javaTypeMapPath = autoMake.getProp(AutoCodeConstant.DBTYPE_JAVA_MAP_PATH);
        Map db2javaTypeMap = XmlUtils.readKeyMapFile(db2javaTypeMapPath);

        //获取数据库到页面类型映射（列表类型为前台类型）
        String dbType2ColumnTagMapPath = autoMake.getProp(AutoCodeConstant.DBTYPE_COLUMNTAG_MAP_PATH);
        Map dbType2ColumnTagMap = XmlUtils.readKeyMapFile(dbType2ColumnTagMapPath);

        //获取requireJs文件map
        String requireJsMapPath = autoMake.getProp(AutoCodeConstant.REQUIRE_JS_MAP_PATH);
        Map requireConfigMap = XmlUtils.readRequireConfigFile(requireJsMapPath);

        RequireUtil requireUtil = new RequireUtil();
        requireUtil.setRequireConfigMap(requireConfigMap);

        /** 如果配置文件不为空，则采用配置文件的数据来使用*/
        TableNameUtil tableNameUtil = new TableNameUtil();
        if (MapUtils.isNotEmpty(db2javaTypeMap)) {
            tableNameUtil.dataTypeMap = db2javaTypeMap;
        }
        if (MapUtils.isNotEmpty(dbType2ColumnTagMap)) {
            tableNameUtil.jspTagMap = dbType2ColumnTagMap;
        }

        def commonBinding = ["tableModel"   : tableModel, "tableDefine": tableModel?.tableDefine,
                             "tableNameUtil": tableNameUtil, "PubUtils": PubUtils, "requireUtil": requireUtil,
                             "config"       : autoCodeParams.config, "extendConf": extendConfigMap,
                             "dictMap"      : dictMap,
                             "StringUtils"  : StringUtils.class, "CollectionUtils": CollectionUtils, "JSON": JSON.class];

        //构造片段代码需要的MAP，片段代码直接从本map中获取数据
        def snippetBinding = ["snippetTemplateMap": snippetTemplateMap];
        snippetBinding.putAll(commonBinding)

        SnippetTemplateUtil snippetTemplateUtil = new SnippetTemplateUtil();
        snippetTemplateUtil.binding = snippetBinding;
        snippetTemplateUtil.dictMap = dictMap;
        snippetTemplateUtil.snippetTemplateMap = snippetTemplateMap;

        //构造模板需要的MAP，模板直接从本map中获取数据
        def templateBinding = [
                "snippetTemplateUtil": snippetTemplateUtil, "dictUtil": snippetTemplateUtil, "dataProcessUtil": DataProcessUtil, "customTool": Maps.newHashMap(), "customData": Maps.newHashMap()];
        templateBinding.putAll(context.getDataMap())
        templateBinding.putAll(commonBinding)
        context.templateBinding = templateBinding;
    }
}
