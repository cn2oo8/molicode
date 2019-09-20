package com.shareyi.molicode.service.common;

import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.conf.AcConfig;

/**
 * 数据库服务
 *
 * @author david
 * @date 2019/6/29
 */
public interface DatabaseService {

    /**
     * 数据库测试
     *
     * @param acConfig
     * @return
     */
    CommonResult<String> testConnection(AcConfig acConfig);
}
