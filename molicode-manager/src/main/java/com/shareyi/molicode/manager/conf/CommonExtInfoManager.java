/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.conf;

import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.manager.BaseManager;

/**
 * 通用扩展信息Manager接口类
 * @author david
 * @date 2018-08-25
 */
public interface CommonExtInfoManager extends BaseManager<CommonExtInfo>{

    /**
     * 通过owner & key 查询数据
     * @param commonExtInfo
     * @return
     */
    CommonExtInfo getByOwnerAndKey(CommonExtInfo commonExtInfo);
}
