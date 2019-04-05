package com.shareyi.molicode.common.utils

import com.shareyi.molicode.common.context.MoliCodeContext
import com.shareyi.molicode.common.enums.EngineType
import com.shareyi.molicode.common.enums.ResultCodeEnum
import com.shareyi.molicode.common.exception.AutoCodeException
import com.shareyi.molicode.common.vo.code.TemplateVo
import net.sf.jxls.transformer.XLSTransformer
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.Workbook
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.apache.velocity.app.VelocityEngine


import groovy.text.SimpleTemplateEngine;
import groovy.text.TemplateEngine
import org.springframework.stereotype.Service

import java.util.jar.JarEntry;

@Service
class TemplateUtil {

    /** Groovy 模板引擎*/
    TemplateEngine engine;

    /**
     * VelocityEngine
     */
    VelocityEngine velocityEngine;
    /**
     * excel模板
     */
    XLSTransformer transformer;

    public TemplateUtil() {
        engine = new SimpleTemplateEngine();
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.INPUT_ENCODING, "utf-8");
        velocityEngine.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
        velocityEngine.init();
        this.transformer = new XLSTransformer();

    }

    /**
     * 渲染模板文件
     * @param template 模板对象
     * @param binding 绑定的内容
     * @return 渲染后的内容
     */
    String renderTemplate(TemplateVo template, Map<String, Object> binding) {
        EngineType engineType = EngineType.getEngineType(template.getEngine());
        String content = null;
        String templateContent = template.templateContent
        if (StringUtils.isEmpty(templateContent) && !Objects.equals(template.engine, EngineType.JXLS.type)) {
            String tFilePath = template.templateFile;
            File templateFile = new File(tFilePath);
            if (!templateFile.exists()) {
                throw new AutoCodeException("模板文件不存在，templateFile=" + templateFile.getAbsolutePath(), ResultCodeEnum.DATA_NOT_EXIST)
            }
            templateFile.withInputStream { inputStream ->
                templateContent = IOUtils.toString(inputStream, "utf-8");
            }
        }

        if (engineType == EngineType.GROOVY) {
            def tp = engine.createTemplate(templateContent).make(binding);
            content = tp.toString();
        } else if (engineType == EngineType.VELOCITY) {
            StringWriter stringWriter = new StringWriter();
            VelocityContext context = new VelocityContext(binding);
            velocityEngine.evaluate(context, stringWriter, "velocityEngine", templateContent)
            content = stringWriter.toString();
        } else if (engineType == EngineType.JXLS) {
            dealJxlsExcel(template, binding)

        } else {
            LogHelper.EXCEPTION.warn("不存在的模板引擎类型, tempate={}", template);
        }
        return content;
    }

    /**
     * 处理excel模板
     * @param template
     * @param binding
     */
    private void dealJxlsExcel(TemplateVo template, Map<String, Object> binding) {
        InputStream inputStream = null;
        try {
            MoliCodeContext moliCodeContext = ThreadLocalHolder.getMoliCodeContext();
            if (moliCodeContext.getMavenJarFile() != null) {
                JarEntry jarEntry = moliCodeContext.getMavenJarFile().getJarEntry(template.getTemplateFile());
                if (jarEntry == null) {
                    throw new AutoCodeException("jar包中未查询到资源=" + template.getTemplateFile(), ResultCodeEnum.DATA_NOT_EXIST);
                }
                inputStream = moliCodeContext.getMavenJarFile().getInputStream(jarEntry);
            } else {
                File file = new File(template.templateFile)
                if (file.exists() && file.isFile()) {
                    inputStream = new FileInputStream(file)
                }
            }
            if (inputStream != null) {
                Workbook workbook = transformer.transformXLS(inputStream, binding);
                template.putExtInfo("workbook", workbook);
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("执行excel模板失败，templateId={}", template.getId(), e);
        } finally {
            IOUtils.closeQuietly(inputStream)
        }
    }

/**
 * 渲染模板内容
 * @param src 模板内容
 * @param binding 绑定的内容
 * @return 渲染后的内容
 */
    String renderContent(String src, Map<String, Object> binding) {
        def tp = engine.createTemplate(src).make(binding);
        return tp.toString();
    }

}
