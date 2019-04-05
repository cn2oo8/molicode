/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.conf;

import com.shareyi.molicode.common.enums.DataTypeEnum;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 *  配置文件 service 接口
 * @author david
 * @date 2018-08-22
 *
 */
public interface AcConfigService extends BaseService<AcConfig>{

    /**
     * 保存配置信息到项目上
     * @param acConfig
     * @return
     */
    CommonResult save(AcConfig acConfig);

    /**
     * 查询project 下的所有配置信息，并转化为map
     * @param projectKey
     * @param dataTypeEnum
     * @return
     */
    Map<String,Map<String,String>> getConfigMapByProjectKey(String projectKey, DataTypeEnum dataTypeEnum);

    /**
     * 查询project 下的所有配置信息，列表
     *
     * @param projectKey
     * @return
     */
    List<AcConfig> queryByProjectKey(String projectKey, DataTypeEnum dataTypeEnum);
}
