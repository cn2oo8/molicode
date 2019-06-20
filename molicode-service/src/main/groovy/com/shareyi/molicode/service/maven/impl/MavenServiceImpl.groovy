package com.shareyi.molicode.service.maven.impl

import com.shareyi.fileutil.FileUtil
import com.shareyi.molicode.common.utils.*
import com.shareyi.molicode.common.vo.maven.MavenResourceVo
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.service.maven.MavenService
import groovy.io.FileType
import org.apache.commons.beanutils.PropertyUtils
import org.apache.commons.io.IOUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

import javax.annotation.Resource

/**
 * 拉去maven服务
 * @author david
 * @since 2018/9/1
 */
@Service
class MavenServiceImpl implements MavenService {

    @Resource
    TemplateUtil templateUtil;

    CommonResult<File> getMavenTemplateFile(MavenResourceVo resourceVo, boolean flushMaven) {
        CommonResult<File> result = CommonResult.create();
        try {

            File mavenTemplateFile = null;
            if (!flushMaven) {
                mavenTemplateFile = this.getMavenResourceFile(resourceVo);

            }
            if (mavenTemplateFile == null) {
                CommonResult prepareResult = this.makeMavenExecuteEvn(resourceVo);
                if (!prepareResult.isSuccess()) {
                    return ResultUtils.passResult(result, prepareResult);
                }

                CommonResult<String> mavenExecuteResult = this.fetchMavenResource(resourceVo);
                if (!mavenExecuteResult.isSuccess()) {
                    return ResultUtils.passResult(result, mavenExecuteResult);
                }
                mavenTemplateFile = this.getMavenResourceFile(resourceVo);
            }
            result.addDefaultModel(mavenTemplateFile);
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取maven模板资源失败, resourceVo={}", resourceVo, e)
            result.failed("获取maven模板资源失败，原因是" + e.getMessage());
        }
        return result;
    }


    @Override
    CommonResult<String> fetchMavenResource(MavenResourceVo resourceVo) {
        CommonResult result = CommonResult.create();
        try {
            result.failed("maven功能已经下线");
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("拉取maven资源失败, resourceVo={}", resourceVo, e)
            result.failed("拉取maven资源失败，原因是" + e.message)
        }
        return result
    }


    @Override
    CommonResult makeMavenExecuteEvn(MavenResourceVo resourceVo) {
        CommonResult result = CommonResult.create();
        ValidateUtils.notEmptyField(resourceVo, "groupId")
        ValidateUtils.notEmptyField(resourceVo, "artifactId")
        ValidateUtils.notEmptyField(resourceVo, "version")
        ValidateUtils.notEmptyField(resourceVo, "mavenTempDir")

        ClassPathResource pomTemplateFile = new ClassPathResource("tempfiles/pom.xml.gsp")
        String template = IOUtils.toString(pomTemplateFile.getInputStream(), "utf-8")

        Map<String, String> contextMap = PropertyUtils.describe(resourceVo)
        String renderPomContent = templateUtil.renderContent(template, contextMap);
        File outputFile = new File(resourceVo.mavenTempDir, "pom.xml")
        FileUtil.makeSureFileExsit(outputFile)
        LogHelper.DEFAULT.info("maven pom.xml 输出路径：" + outputFile.getAbsolutePath())
        IOUtils.write(renderPomContent, new FileOutputStream(outputFile));
        return result.succeed()
    }


    @Override
    File getMavenResourceFile(MavenResourceVo resourceVo) {
        ValidateUtils.notEmptyField(resourceVo, "groupId")
        ValidateUtils.notEmptyField(resourceVo, "artifactId")
        ValidateUtils.notEmptyField(resourceVo, "version")
        ValidateUtils.notEmptyField(resourceVo, "localRepository")


        String resourceJarParentDir = PubUtils.packageToPath(resourceVo.groupId) + "/" +
                resourceVo.artifactId + "/" + resourceVo.version

        File jarParentDirPath = new File(resourceVo.localRepository, resourceJarParentDir);
        if (!jarParentDirPath.exists() || !jarParentDirPath.isDirectory()) {
            return null
        }

        List<File> fileList = []
        jarParentDirPath.eachFileMatch(FileType.FILES, ~/.*\.jar/, { file ->
            fileList.add(file);
        })
        if (fileList.isEmpty()) {
            return null
        }

        Collections.sort(fileList, new Comparator<File>() {
            @Override
            int compare(File o1, File o2) {
                return o2.name <=> o1.name
            }
        })
        return fileList.get(0)
    }


}
