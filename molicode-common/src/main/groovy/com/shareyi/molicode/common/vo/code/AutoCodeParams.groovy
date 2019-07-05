package com.shareyi.molicode.common.vo.code

import com.shareyi.molicode.common.utils.PubUtils
import com.shareyi.molicode.common.vo.git.GitRepoVo
import com.shareyi.molicode.common.vo.maven.MavenResourceVo
import com.shareyi.molicode.sdk.dto.ExtAttrDto
import org.apache.commons.lang3.StringUtils

/**
 *
 * 自动生成代码需要的参数，调用时
 * Created by david on 2015/1/18.
 */
class AutoCodeParams extends ExtAttrDto {

    /****   页面入参 start  ****/
    /**
     * 工程key
     */
    String projectKey;
    /**
     * 自动代码配置xml路径
     */
    String autoXmlPath;
    /**
     * tableModel的路径
     */
    String tableModelPath;
    /**
     * 代码生成的基础路径
     */
    String projectOutputDir;
    /**
     * 模板的基础路径
     */
    String templateBaseDir;

    /**
     * 是否覆盖
     */
    boolean overrideFlag;
    /**
     * 模板ID列表
     */
    String templateIds;

    /**
     * 模板类型, local.本地，maven.maven仓库
     * {@link com.shareyi.molicode.common.enums.TemplateTypeEnum}
     */
    String templateType;
    /**
     * 是否强制刷新maven
     */
    String flushMaven;

    /**
     * 数据处理器名称
     * @see com.shareyi.molicode.common.enums.DataModelTypeEnum
     */
    String dataModelType;

    /**
     * 源内容来源类型
     * @see com.shareyi.molicode.common.enums.ResourceTypeEnum
     */
    String resourceType;

    /**
     * 前台传入的内容，如果为http,可以为http地址
     */
    String frontContent;

    /**
     * 和页面会话ID，日志输出使用
     */
    String sid;

    /****   页面入参 end  ****/

    /**
     * 相关配置信息，从properties里面读取
     */
    ConfigVo config;

    /**
     * maven相关资源参数
     */
    MavenResourceVo mavenResourceVo;

    /**
     * 获取模板内容
     */
    boolean loadTemplateContent;

    /**
     * 从模板ID列表转换而来的ID set
     */
    private HashSet<String> templateIdSet;
    /**
     * json配置信息
     */
    Map<String, Object> jsonConfig
    /**
     * git仓库的配置
     */
    GitRepoVo gitRepoInfo

    /**
     * 输出路径
     */
    String outputType;

    /**
     * 输出目录名称，单一目录名称
     */
    String outputDir

    /**
     * 是否包含该模板ID
     * @param templateId
     * @return
     */
    boolean containsTemplateIds(String templateId) {
        Set<String> set = this.getTemplateIdSet();
        return set.contains(templateId);
    }

    /**
     * 获取模板ID set集合
     * @return
     */
    Set<String> getTemplateIdSet() {
        if (templateIdSet == null) {
            templateIdSet = new HashSet<String>();
            if (StringUtils.isNotEmpty(templateIds)) {
                templateIdSet = new HashSet(PubUtils.stringToList(templateIds))
            }
        }
        return templateIdSet;
    }

    String getProjectKey() {
        return projectKey
    }

    void setProjectKey(String projectKey) {
        this.projectKey = projectKey
    }

    String getAutoXmlPath() {
        return autoXmlPath
    }

    void setAutoXmlPath(String autoXmlPath) {
        this.autoXmlPath = autoXmlPath
    }

    String getTableModelPath() {
        return tableModelPath
    }

    void setTableModelPath(String tableModelPath) {
        this.tableModelPath = tableModelPath
    }

    String getProjectOutputDir() {
        return projectOutputDir
    }

    void setProjectOutputDir(String projectOutputDir) {
        this.projectOutputDir = projectOutputDir
    }

    String getTemplateBaseDir() {
        return templateBaseDir
    }

    void setTemplateBaseDir(String templateBaseDir) {
        this.templateBaseDir = templateBaseDir
    }

    ConfigVo getConfig() {
        return config
    }

    void setConfig(ConfigVo config) {
        this.config = config
    }

    boolean getOverrideFlag() {
        return overrideFlag
    }

    void setOverrideFlag(boolean overrideFlag) {
        this.overrideFlag = overrideFlag
    }

    String getTemplateIds() {
        return templateIds
    }

    void setTemplateIds(String templateIds) {
        this.templateIds = templateIds
    }

    String getTemplateType() {
        return templateType
    }

    void setTemplateType(String templateType) {
        this.templateType = templateType
    }

    MavenResourceVo getMavenResourceVo() {
        return mavenResourceVo
    }

    void setMavenResourceVo(MavenResourceVo mavenResourceVo) {
        this.mavenResourceVo = mavenResourceVo
    }

    boolean getLoadTemplateContent() {
        return loadTemplateContent
    }

    void setLoadTemplateContent(boolean loadTemplateContent) {
        this.loadTemplateContent = loadTemplateContent
    }

    void setTemplateIdSet(HashSet<String> templateIdSet) {
        this.templateIdSet = templateIdSet
    }

    String getFlushMaven() {
        return flushMaven
    }

    void setFlushMaven(String flushMaven) {
        this.flushMaven = flushMaven
    }

    String getDataModelType() {
        return dataModelType
    }

    void setDataModelType(String dataModelType) {
        this.dataModelType = dataModelType
    }

    Map<String, Object> getJsonConfig() {
        return jsonConfig
    }

    void setJsonConfig(Map<String, Object> jsonConfig) {
        this.jsonConfig = jsonConfig
    }

    String getOutputDir() {
        return outputDir
    }

    void setOutputDir(String outputDir) {
        this.outputDir = outputDir
    }

    @Override
    String toString() {
        return "AutoCodeParams{" +
                "projectKey='" + projectKey + '\'' +
                ", autoXmlPath='" + autoXmlPath + '\'' +
                ", tableModelPath='" + tableModelPath + '\'' +
                ", projectOutputDir='" + projectOutputDir + '\'' +
                ", templateBaseDir='" + templateBaseDir + '\'' +
                ", overrideFlag=" + overrideFlag +
                ", templateIds='" + templateIds + '\'' +
                ", templateType='" + templateType + '\'' +
                ", flushMaven='" + flushMaven + '\'' +
                ", dataModelType='" + dataModelType + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", frontContent='" + frontContent + '\'' +
                ", sid='" + sid + '\'' +
                ", mavenResourceVo=" + mavenResourceVo +
                ", loadTemplateContent=" + loadTemplateContent +
                ", templateIdSet=" + templateIdSet +
                "} " + super.toString();
    }
}
