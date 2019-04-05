package com.shareyi.molicode.common.utils

import com.alibaba.fastjson.JSON
import com.shareyi.molicode.common.constants.CommonConstant
import com.shareyi.molicode.common.enums.EngineType
import com.shareyi.molicode.common.enums.ResultCodeEnum
import com.shareyi.molicode.common.exception.AutoCodeException
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.vo.code.ColumnVo
import com.shareyi.molicode.common.vo.code.DictVo
import com.shareyi.molicode.common.vo.code.OptionVo
import com.shareyi.molicode.common.vo.code.OrderColumnVo
import com.shareyi.molicode.common.vo.code.RequireConfigVo
import com.shareyi.molicode.common.vo.code.SnippetTemplateVo
import com.shareyi.molicode.common.vo.code.TableDefineVo
import com.shareyi.molicode.common.vo.code.TableModelVo
import com.shareyi.molicode.common.vo.code.TemplateVo
import com.shareyi.molicode.common.vo.maven.MavenResourceVo
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils;


import com.shareyi.fileutil.FileUtil;

class XmlUtils {

    /**
     * 解析tableModel xml文件，将其数据封装为TableModel模型中
     * @param filePath tableModel xml文件路径
     * @return TableModel数据封装
     */
    static TableModelVo getTableModelByFile(String filePath) {
        File tableModelFile = new File(filePath);
        def tableModelXml = new XmlParser().parse(tableModelFile);
        TableModelVo tableModel = new TableModelVo();

        def tableDefine = tableModelXml.tableDefine;
        if (tableDefine != null && tableDefine != []) {
            tableDefine = tableDefine[0];
            TableDefineVo tableDefine2 = new TableDefineVo();
            LinkedList<ColumnVo> columns = new LinkedList<ColumnVo>()
            tableDefine2.setColumns(columns);
            tableDefine2.cnname = tableDefine.attribute("cnname");
            tableDefine2.pageSize = tableDefine.attribute("pageSize");
            tableDefine2.id = tableDefine.attribute("id");
            tableDefine2.dbTableName = tableDefine.attribute("dbTableName");
            tableDefine2.isPaged = tableDefine.attribute("isPaged");
            tableModel.tableDefine = tableDefine2;

            tableDefine.column.each {
                ColumnVo column = new ColumnVo();
                column.dataName = it.attribute("dataName");
                column.columnName = it.attribute("columnName");
                column.jspTag = it.attribute("jspTag");
                column.cnname = it.attribute("cnname");
                column.canBeNull = it.attribute("canBeNull") == 'true';
                column.readonly = it.attribute("readonly");
                column.isPK = it.attribute("isPK") == 'true';
                column.setLength(it.attribute("length"));
                column.columnType = it.attribute("columnType");
                column.dictName = it.attribute("dictName");
                column.comment = it.attribute("comment");
                columns.add(column);
            }
        }

        tableModel.searchKeys = tableModelXml.searchKeys[0]?.text();
        tableModel.allColumn = tableModelXml.allColumn[0]?.text();
        tableModel.queryList = tableModelXml.queryList[0]?.text();
        tableModel.addList = tableModelXml.addList[0]?.text();
        tableModel.updateList = tableModelXml.updateList[0]?.text();
        tableModel.viewList = tableModelXml.viewList[0]?.text();
        tableModel.createTime = tableModelXml.createTime[0]?.text();
        tableModel.updateTime = tableModelXml.updateTime[0]?.text();

        LinkedList<OrderColumnVo> orderColumns = new LinkedList<OrderColumnVo>()
        tableModel.orderColumns = orderColumns;
        def orderColumns2 = tableModelXml.orderColumns;
        if (orderColumns2 != null && orderColumns2 != []) {
            orderColumns2.orderColumn.each {
                OrderColumnVo orderColumn = new OrderColumnVo();
                orderColumn.orderType = it.attribute("orderType");
                orderColumn.columnName = it.text();
                orderColumns.add(orderColumn);
            }
        }

        Map<String, DictVo> dictMap = [:];
        def dictElements = tableModelXml.dicts.dict;
        if (dictElements != null && dictElements.size() > 0) {
            dictElements.each {
                DictVo dict = new DictVo();
                dict.name = it.attribute("name");
                dict.id = it.attribute("id");
                dictMap.put(dict.id, dict);

                List options = dict.optionList;
                it.option.each {
                    OptionVo option = new OptionVo();
                    option.value = it.attribute("value");
                    option.cssClass = it.attribute("cssClass");
                    option.name = it.text();
                    options.add option;
                    print option.name + " " + option.value;
                }
            }
        }
        tableModel.dictMap = dictMap;

        return tableModel;
    }

    /**
     * 解析tableModel xml文件，将其数据封装为TableModel模型中
     * @param filePath tableModel xml文件路径
     * @return TableModel数据封装
     */
    static TableModelVo getTableModelByContent(String content) {
        def tableModelXml = new XmlParser().parseText(content);
        TableModelVo tableModel = new TableModelVo();

        def tableDefine = tableModelXml.tableDefine;
        if (tableDefine != null && tableDefine != []) {
            tableDefine = tableDefine[0];
            TableDefineVo tableDefine2 = new TableDefineVo();
            LinkedList<ColumnVo> columns = new LinkedList<ColumnVo>()
            tableDefine2.setColumns(columns);
            tableDefine2.cnname = tableDefine.attribute("cnname");
            tableDefine2.pageSize = tableDefine.attribute("pageSize");
            tableDefine2.id = tableDefine.attribute("id");
            tableDefine2.dbTableName = tableDefine.attribute("dbTableName");
            tableDefine2.isPaged = tableDefine.attribute("isPaged");
            tableModel.tableDefine = tableDefine2;

            tableDefine.column.each {
                ColumnVo column = new ColumnVo();
                column.dataName = it.attribute("dataName");
                column.columnName = it.attribute("columnName");
                column.jspTag = it.attribute("jspTag");
                column.cnname = it.attribute("cnname");
                column.canBeNull = it.attribute("canBeNull") == 'true';
                column.readonly = it.attribute("readonly");
                column.isPK = it.attribute("isPK") == 'true';
                column.setLength(it.attribute("length"));
                column.columnType = it.attribute("columnType");
                column.dictName = it.attribute("dictName");
                column.comment = it.attribute("comment");
                columns.add(column);
            }
        }

        tableModelXml.bizFieldsMap.bizFields.each {
            String key = it.attribute("key")
            String fields = it.text();
            tableModel.putBizFields(key, fields)
        }

        LinkedList<OrderColumnVo> orderColumns = new LinkedList<OrderColumnVo>()
        tableModel.orderColumns = orderColumns;
        def orderColumns2 = tableModelXml.orderColumns;
        if (orderColumns2 != null && orderColumns2 != []) {
            orderColumns2.orderColumn.each {
                OrderColumnVo orderColumn = new OrderColumnVo();
                orderColumn.orderType = it.attribute("orderType");
                orderColumn.columnName = it.text();
                orderColumns.add(orderColumn);
            }
        }

        Map<String, DictVo> dictMap = [:];
        def dictElements = tableModelXml.dicts.dict;
        if (dictElements != null && dictElements.size() > 0) {
            dictElements.each {
                DictVo dict = new DictVo();
                dict.name = it.attribute("name");
                dict.id = it.attribute("id");
                dictMap.put(dict.id, dict);

                List options = dict.optionList;
                it.option.each {
                    OptionVo option = new OptionVo();
                    option.value = it.attribute("value");
                    option.cssClass = it.attribute("cssClass");
                    option.name = it.text();
                    options.add option;
                    print option.name + " " + option.value;
                }
            }
        }
        tableModel.dictMap = dictMap;

        return tableModel;
    }

    /**
     * 解析autoMake配置文件，封装为AutoMake实例
     * @param filePath autoMake配置文件路径
     * @return AutoMake实例
     */
    static AutoMakeVo getAutoMake(String filePath, String templateBaseDir) {
        if (filePath == null) {
            return null;
        }
        File autoXmlFile = new File(filePath);
        if (!autoXmlFile.exists() || !autoXmlFile.isFile()) {
            throw new AutoCodeException("autoCode.xml配置文件不存在,filePath=" + filePath, ResultCodeEnum.DATA_NOT_EXIST)
        }
        AutoMakeVo autoMakeVo = autoXmlFile.withInputStream { inputStream ->
            return getAutoMakeByContent(IOUtils.toString(inputStream, "UTF-8"))
        }
        autoMakeVo.templates.each { template ->
            template.templateFile = FileUtil.contactPath(templateBaseDir, template.templateFile?.trim());
        }
        return autoMakeVo;
    }

    /**
     * 解析autoMake配置文件，封装为AutoMake实例
     * @param filePath autoMake配置文件路径
     * @return AutoMake实例
     */
    static AutoMakeVo getAutoMakeByContent(String xmlContent) {
        if (xmlContent == null) {
            return null;
        }
        def autoMakeXml = new XmlParser().parseText(xmlContent);
        AutoMakeVo autoMake = new AutoMakeVo();


        def propsNodes = autoMakeXml.properties;
        if (propsNodes != null && propsNodes.size() > 0) {
            propsNodes.property.each {
                def key = getStringFromNode(it, "key", true);
                String value = StringUtils.trim(it.text())
                autoMake.addProp(key, value);
            }
        }

        List<TemplateVo> templates = [];
        autoMake.templates = templates;
        autoMake.moliCodeVersion = getStringFromNode(autoMakeXml, "moliCodeVersion", true);
        /**
         * 新版本的XML，后期通过版本号来确定走哪个逻辑
         */
        if (autoMakeXml.templates != null && autoMakeXml.templates.size() > 0) {
            autoMakeXml.templates.template.each { it ->
                TemplateVo t = new TemplateVo();
                t.id = getStringFromNode(it, "id", true);
                t.destFile = getStringFromNode(it, "destFile", true);
                t.templateFile = getStringFromNode(it, "templateFile", true);
                t.desc = getStringFromNode(it, "desc", true);
                t.name = getStringFromNode(it, "name", true);
                if (t.desc == null) {
                    t.desc = t.name;
                }

                def engine = getStringFromNode(it, "engine", true);
                EngineType engineType = EngineType.getEngineType(engine);
                if (engineType == null) {
                    engineType = EngineType.GROOVY;
                }

                String acceptDataModel = getStringFromNode(it, "acceptDataModel", true)
                if (StringUtils.isNotEmpty(acceptDataModel)) {
                    t.acceptDataModel = acceptDataModel;
                    t.acceptDataModelList = MoliCodeStringUtils.splitToList(acceptDataModel, CommonConstant.COMMA);
                }
                t.engine = engineType.getType();
                templates.add t;
            }
        } else {
            autoMakeXml.template.each {
                TemplateVo t = new TemplateVo();
                t.id = getStringFromNode(it, "id", true);
                t.destFile = getStringFromNode(it, "destFile", true);
                t.templateFile = getStringFromNode(it, "templateFile", true);
                t.desc = getStringFromNode(it, "desc", true);
                t.name = t.desc;
                def engine = getStringFromNode(it, "engine", true);
                EngineType engineType = EngineType.getEngineType(engine);
                if (engineType == null) {
                    engineType = EngineType.GROOVY;
                }
                t.engine = engineType.getType();
                t.templateFile = it.text();
                t.templateFile = t.templateFile?.trim();
                templates.add t;
            }
        }
        return autoMake;
    }

    /**
     * 从NODE节点获取数据
     * @param node
     * @param key
     * @param trim
     * @return
     */
    static String getStringFromNode(Node node, String key, boolean trim) {
        String val = null;
        def attribute = node.attribute(key)
        if (attribute != null) {
            val = attribute;
        } else {
            def elementNode = node[key]
            if (elementNode != null && elementNode.size() > 0) {
                val = elementNode.text()
            }
        }
        if (trim && val != null) {
            val = StringUtils.trim(val);
        }
        return val;
    }

    /**
     * 解析autoMake配置文件，封装为AutoMake实例
     * @param filePath autoMake配置文件路径
     * @return AutoMake实例
     */
    static Map getDictMap(String filePath) {
        if (filePath == null) {
            return [:];
        }
        File dictFile = new File(SystemFileUtils.parseFilePath(filePath));
        if (!dictFile.exists()) {
            return [:];
        }
        def dictXml = new XmlParser().parse(dictFile);
        Map<String, DictVo> dictMap = [:];
        dictXml.dict.each {
            DictVo dict = new DictVo();
            dict.name = it.attribute("name");
            dict.id = it.attribute("id");
            dictMap.put(dict.id, dict);

            List options = dict.optionList;
            it.option.each {
                OptionVo option = new OptionVo();
                option.value = it.attribute("value");
                option.cssClass = it.attribute("cssClass");
                option.name = it.text();
                options.add option;
                print option.name + " " + option.value;
            }
        }

        return dictMap;
    }

    /**
     * 获取代码片段数据(文本内容获取也在复用，目前只是用了 ID和内容两个值)
     * @param filePath
     * @return
     */
    static Map getSnippetTemplateMap(String filePath) {
        if (filePath == null) {
            return [:];
        }
        File snippetTemplateFile = new File(SystemFileUtils.parseFilePath(filePath));
        if (!snippetTemplateFile.exists()) {
            return [:];
        }
        def snippetTemplateXml = new XmlParser().parse(snippetTemplateFile);
        Map<String, DictVo> snippetTemplateMap = [:];
        snippetTemplateXml.snippetTemplate.each {
            SnippetTemplateVo snippetTemplate = new SnippetTemplateVo();
            snippetTemplate.name = it.attribute("name");
            snippetTemplate.id = it.attribute("id");
            snippetTemplate.group = it.attribute("group");
            snippetTemplate.template = it.text();
            snippetTemplateMap.put(snippetTemplate.id, snippetTemplate);
        }

        return snippetTemplateMap;

    }

    /**
     * 获取代码片段数据(文本内容获取也在复用，目前只是用了 ID和内容两个值)
     * @param filePath
     * @return
     */
    static Map readKeyMapFile(String filePath) {
        if (filePath == null) {
            return [:];
        }
        File keyValueFile = new File(SystemFileUtils.parseFilePath(filePath));
        if (!keyValueFile.exists()) {
            return [:];
        }
        def keyValueXml = new XmlParser().parse(keyValueFile);
        Map<String, DictVo> keyValueMap = [:];
        keyValueXml.keyValue.each {
            String key = it.attribute("key");
            String value = it.text();
            keyValueMap.put(key, value);
        }

        return keyValueMap;

    }

    /**
     * 获取代码片段数据(文本内容获取也在复用，目前只是用了 ID和内容两个值)
     * @param filePath
     * @return
     */
    static Map readRequireConfigFile(String filePath) {
        if (filePath == null) {
            return [:];
        }
        File keyValueFile = new File(SystemFileUtils.parseFilePath(filePath));
        if (!keyValueFile.exists()) {
            return [:];
        }
        def keyValueXml = new XmlParser().parse(keyValueFile);
        Map<String, RequireConfigVo> keyValueMap = [:];
        keyValueXml.requireConfig.each {

            RequireConfigVo requireConfig = new RequireConfigVo();
            requireConfig.columnTag = it.attribute("columnTag");
            requireConfig.desc = it.attribute("desc");
            requireConfig.requireModule = it.attribute("requireModule");
            String sortNum = it.attribute("sortNum");
            if (StringUtils.isNumeric(sortNum)) {
                requireConfig.sortNum = Integer.parseInt(sortNum);
            }


            requireConfig.requireText = it.text();
            keyValueMap.put(requireConfig.columnTag, requireConfig);
        }

        return keyValueMap;
    }

    /**
     * 获取maven相关信息
     * @param xmlContent
     * @return MavenResourceVo
     */
    static MavenResourceVo parseMavenInfoByContent(String xmlContent) {
        if (xmlContent == null) {
            return null;
        }
        def rootXml = new XmlParser().parseText(xmlContent);
        MavenResourceVo mavenResourceVo = new MavenResourceVo();
        mavenResourceVo.name = getStringFromNode(rootXml, "name", true);
        mavenResourceVo.groupId = getStringFromNode(rootXml, "groupId", true);
        mavenResourceVo.version = getStringFromNode(rootXml, "version", true);
        mavenResourceVo.artifactId = getStringFromNode(rootXml, "artifactId", true);

        mavenResourceVo.description = getStringFromNode(rootXml, "description", true);
        mavenResourceVo.url = getStringFromNode(rootXml, "url", true);
        mavenResourceVo.inceptionYear = getStringFromNode(rootXml, "inceptionYear", true);
        def developersNode = rootXml.developers;
        if (developersNode != null && developersNode.size() > 0) {
            def developerInfos = [];
            developersNode.developer.each { it ->
                def developerInfo = [:]
                def childNodeIter = it.iterator();
                while (childNodeIter.hasNext()) {
                    def childNode = childNodeIter.next();
                    developerInfo.put(childNode.name().localPart, childNode.text())
                }
                developerInfos.add(developerInfo)
            }
            mavenResourceVo.developersJson = JSON.toJSON(developerInfos);
        }
        return mavenResourceVo;
    }
}
