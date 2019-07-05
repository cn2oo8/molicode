/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.sys;

import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.manager.BaseManager;

/**
 * 用户信息Manager接口类
 * @author david
 * @date 2018-08-21
 */
public interface AcUserManager extends BaseManager<AcUser>{

    /**
     * 通过用户名查询用户信息
     * @param userName
     * @return
     */
    AcUser getByUserName(String userName);
}
