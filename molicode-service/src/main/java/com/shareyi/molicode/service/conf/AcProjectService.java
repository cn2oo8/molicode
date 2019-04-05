/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.conf;

import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.conf.AcProject;
import com.shareyi.molicode.service.BaseService;

/**
 *  项目 service 接口
 * @author david
 * @date 2018-08-22
 *
 */
public interface AcProjectService extends BaseService<AcProject>{

    /**
     * 通过projectKey获取
     * @param acProject
     * @return
     */
    CommonResult getByProjectKey(AcProject acProject);

    /**
     * 复制项目
     * @param acProject
     * @return
     */
    CommonResult copyProject(AcProject acProject);
}
