/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.conf;

import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.manager.BaseManager;

import java.util.List;

/**
 * 配置文件Manager接口类
 * @author david
 * @date 2018-08-22
 */
public interface AcConfigManager extends BaseManager<AcConfig>{

    /**
     * 通过项目key & 配置key获取对象
     * @param projectKey
     * @param configKey
     * @return
     */
    AcConfig getByProjectAndKey(String projectKey, String configKey);

    /**
     * 查询列表
     * @param queryBean
     * @return
     */
    List<AcConfig> getListByExample(AcConfig queryBean);
}
