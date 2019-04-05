package com.shareyi.molicode.service.gencode

import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo

import java.util.jar.JarFile;

/**
 * autoMake 服务类
 *
 * @author zhangshibin
 * @since 2018/10/3
 */
interface AutoMakeService {

    /**
     * 加载相关配置文件
     * 在properties中和数据库配置信息中
     * @param autoCodeParams
     */
    void getConfigInfo(AutoCodeParams autoCodeParams)

    /**
     * 加载模板内容
     * @param autoMake
     * @param jarFile
     */
    void loadTemplateContent(AutoMakeVo autoMake, JarFile jarFile)
}
