package com.shareyi.molicode.service.gencode.impl

import com.alibaba.fastjson.JSON
import com.shareyi.fileutil.FileUtil
import com.shareyi.molicode.common.constants.AutoCodeConstant
import com.shareyi.molicode.common.constants.CommonConstant
import com.shareyi.molicode.common.constants.ConfigKeyConstant
import com.shareyi.molicode.common.enums.*
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.valid.Validate
import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.vo.code.ConfigVo
import com.shareyi.molicode.common.vo.git.GitRepoVo
import com.shareyi.molicode.common.vo.maven.MavenResourceVo
import com.shareyi.molicode.service.conf.AcConfigService
import com.shareyi.molicode.service.conf.CommonExtInfoService
import com.shareyi.molicode.service.gencode.AutoMakeService
import com.shareyi.molicode.service.maven.MavenService
import org.apache.commons.beanutils.BeanUtils
import org.apache.commons.collections4.MapUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

import javax.annotation.Resource
import java.text.SimpleDateFormat
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * autoMake 实现类
 * @author zhangshibin
 * @since 2018/10/3
 */
@Service
class AutoMakeServiceImpl implements AutoMakeService {

    @Resource
    AcConfigService acConfigService;
    @Resource
    MavenService mavenService;
    @Resource
    CommonExtInfoService commonExtInfoService;

    /**
     * 加载相关配置文件
     * 在properties中和数据库配置信息中
     * @param autoCodeParams
     */
    void getConfigInfo(AutoCodeParams codeParams) {
        Validate.notEmpty(codeParams.getProjectKey(), "projectKey不能为空")
        Map<String, Map<String, String>> configMap = acConfigService.getConfigMapByProjectKey(codeParams.projectKey, DataTypeEnum.JSON);
        Map<String, String> pathConfigMap = configMap.get(ConfigKeyConstant.PathConfig.CONFIG_KEY);
        Map<String, String> codeConfigMap = configMap.get(ConfigKeyConstant.CodeConfig.CONFIG_KEY);
        Map<String, String> extConfigMap = configMap.get(ConfigKeyConstant.ExtConfig.CONFIG_KEY);

        if (extConfigMap != null) {
            String jsonConfig = MapUtils.getString(extConfigMap, ConfigKeyConstant.ExtConfig.JSON_KEY)
            if (StringUtils.isNotEmpty(jsonConfig)) {
                codeParams.jsonConfig = JSON.parseObject(jsonConfig);
            }
        }
        Map<String, Map<String, String>> globalConfigMap = commonExtInfoService.getConfigMapByOwner(OwnerTypeEnum.SYSTEM.getCode(), CommonConstant.DEFAULT_SYS_OWNER, DataTypeEnum.JSON);

        this.parsePathConfigInfo(globalConfigMap, codeParams, pathConfigMap)
        this.parseConfigVoInfo(codeConfigMap, codeParams)
    }

    /**
     * 转换路径相关的参数
     *
     * @param globalConfigMap
     * @param codeParams
     * @param pathConfigMap
     */
    private void parsePathConfigInfo(Map<String, Map<String, String>> globalConfigMap, AutoCodeParams codeParams, Map<String, String> pathConfigMap) {
        Map<String, String> mavenConfigMap = globalConfigMap.get(ConfigKeyConstant.GlobalMavenConfig.CONFIG_KEY)
        if (StringUtils.isEmpty(codeParams.getAutoXmlPath())) {
            codeParams.setAutoXmlPath(MapUtils.getString(pathConfigMap, ConfigKeyConstant.PathConfig.AUTO_XML_PATH));
        }
        if (StringUtils.isEmpty(codeParams.getTemplateBaseDir())) {
            codeParams.setTemplateBaseDir(MapUtils.getString(pathConfigMap, ConfigKeyConstant.PathConfig.TEMPLATE_BASE_PATH));
        }

        def templateType = MapUtils.getString(pathConfigMap, ConfigKeyConstant.PathConfig.TEMPLATE_TYPE);
        codeParams.setTemplateType(templateType)
        TemplateTypeEnum templateTypeEnum = EnumCode.Parser.parseTo(TemplateTypeEnum.class, templateType);
        switch (templateTypeEnum) {
            case TemplateTypeEnum.GIT:
                GitRepoVo gitRepoVo = new GitRepoVo();
                BeanUtils.populate(gitRepoVo, pathConfigMap);
                codeParams.gitRepoInfo = gitRepoVo;
                break;
            case TemplateTypeEnum.MAVEN:
                MavenResourceVo resourceVo = new MavenResourceVo();
                codeParams.setMavenResourceVo(resourceVo)
                if (pathConfigMap != null) {
                    BeanUtils.populate(resourceVo, pathConfigMap);
                }
                if (mavenConfigMap != null) {
                    BeanUtils.populate(resourceVo, mavenConfigMap);
                }
                if (StringUtils.isEmpty(resourceVo.mavenTempDir)) {
                    resourceVo.setMavenTempDir(FileUtil.getRuntimeFilePath("maven_temp"));
                }
                break;
            case TemplateTypeEnum.LOCAL:
                break;
        }
    }

    /**
     * 转换老的configVo的参数数据
     *
     * @param codeConfigMap
     * @param codeParams
     */
    private void parseConfigVoInfo(Map<String, String> codeConfigMap, AutoCodeParams codeParams) {
        ConfigVo config = new ConfigVo();
        BeanUtils.populate(config, codeConfigMap);

        String author = MapUtils.getString(codeConfigMap, ConfigKeyConstant.CodeConfig.AUTHOR);
        String basePackage = MapUtils.getString(codeConfigMap, ConfigKeyConstant.CodeConfig.BASE_PACKAGE);
        String artifactId = MapUtils.getString(codeConfigMap, ConfigKeyConstant.CodeConfig.ARTIFACT_ID);
        String category = MapUtils.getString(codeConfigMap, ConfigKeyConstant.CodeConfig.CATEGORY);
        String date = MapUtils.getString(codeConfigMap, ConfigKeyConstant.CodeConfig.DATE);
        String year = MapUtils.getString(codeConfigMap, ConfigKeyConstant.CodeConfig.YEAR);


        config.setAuthor(author);
        String systemDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (StringUtils.isBlank(date)) {
            date = systemDate;
        }
        if (StringUtils.isBlank(year)) {
            year = systemDate.substring(0, 4);
        }
        if (StringUtils.isEmpty(category)) {
            category = "";
        }
        config.setAuthor(author);
        config.setNowDate(date);
        config.setYear(year);
        config.setCategory(category);
        config.setBasePackage(basePackage);
        config.setArtifactId(artifactId);
        codeParams.setConfig(config);
    }

    /**
     * 加载模板内容
     * @param autoMake
     * @param jarFile
     */
    void loadTemplateContent(AutoMakeVo autoMake, JarFile jarFile) {
        autoMake.templates.each { template ->
            //如果为jxls ,需要将jar file放在template中，方便后面获取inputStream
            if (Objects.equals(template.engine, EngineType.JXLS.getType())) {
                // template.templateJarEntry = jarFile.getJarEntry(template.templateFile)
            } else {
                template.templateContent = getContentFromJarFile(template.templateFile, jarFile)
            }
        }
        /**
         * 用户自定义工具，用户自定义数据处理
         */
        String customToolContent = this.getContentFromJarFile(AutoCodeConstant.MOLI_TEMPLATE_CUSTOM_TOOL, jarFile);
        String customDataProcessContent = this.getContentFromJarFile(AutoCodeConstant.MOLI_TEMPLATE_CUSTOM_DATA_PROCESS, jarFile);
        autoMake.putMoliTemplate(AutoCodeConstant.MOLI_TEMPLATE_CUSTOM_TOOL, customToolContent)
        autoMake.putMoliTemplate(AutoCodeConstant.MOLI_TEMPLATE_CUSTOM_DATA_PROCESS, customDataProcessContent)
    }

    private String getContentFromJarFile(String templatePath, JarFile jarFile) {
        JarEntry jarEntry = jarFile.getJarEntry(templatePath)
        String templateContent = null;
        if (jarEntry == null) {
            LogHelper.DEFAULT.warn("模板文件未查询到, path={}", templatePath)
        } else {
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            try {
                templateContent = IOUtils.toString(inputStream, "UTF-8");
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return templateContent;
    }
}
