/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.conf;

import com.shareyi.molicode.domain.conf.AcProject;
import com.shareyi.molicode.manager.BaseManager;

/**
 * 项目Manager接口类
 * @author david
 * @date 2018-08-22
 */
public interface AcProjectManager extends BaseManager<AcProject>{

    /**
     * 通过projectKey获取
     * @param projectKey
     * @return
     */
    AcProject getByProjectKey(String projectKey);
}
