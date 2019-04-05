package com.shareyi.molicode.service.maven

import com.shareyi.molicode.common.vo.maven.MavenResourceVo
import com.shareyi.molicode.common.web.CommonResult;

/**
 * maven相关服务
 *
 * @author david
 * @since 2018/9/1
 */
interface MavenService {

    /**
     * 获取maven模板资源路径
     * @param resourceVo
     * @param flushMaven 强刷maven
     * @return
     */
    CommonResult<File> getMavenTemplateFile(MavenResourceVo resourceVo, boolean flushMaven)
    /**
     * 拉去maven相关资源
     * @return
     */
    CommonResult<String> fetchMavenResource(MavenResourceVo resourceVo)

    /**
     * 创建maven可执行环境
     * @param resourceVo
     * @return
     */
    CommonResult makeMavenExecuteEvn(MavenResourceVo resourceVo);
        /**
     * 获取maven的jar包
     * @param resourceVo
     * @return
     */
    File getMavenResourceFile(MavenResourceVo resourceVo);
}
