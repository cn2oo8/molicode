/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.conf;

import com.shareyi.molicode.common.enums.DataTypeEnum;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.service.BaseService;
import groovy.lang.Closure;

import java.util.Map;

/**
 *  通用扩展信息 service 接口
 * @author david
 * @date 2018-08-25
 *
 */
public interface CommonExtInfoService extends BaseService<CommonExtInfo>{

    /**
     * 保存数据
     * @param commonExtInfo
     * @return
     */
    CommonResult save(CommonExtInfo commonExtInfo);

    /**
     * 通过owner 和 key进行查询
     * @param commonExtInfo
     * @return
     */
    CommonResult getByOwnerAndKey(CommonExtInfo commonExtInfo);

    /**
     * 通过拥有者获取数据
     * @param ownerType
     * @param ownerCode
     * @param dataTypeEnum
     * @return
     */
    Map<String,Map<String,String>> getConfigMapByOwner(Integer ownerType, String ownerCode, DataTypeEnum dataTypeEnum);
}
