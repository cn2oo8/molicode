/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.conf.impl;

import com.shareyi.molicode.common.enums.StatusEnum;
import com.shareyi.molicode.common.enums.columns.AcConfigColumn;
import com.shareyi.molicode.common.enums.columns.BasicColumn;
import com.shareyi.molicode.common.enums.columns.CommonExtInfoColumn;
import com.shareyi.molicode.domain.conf.AcConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.dao.conf.CommonExtInfoDao;
import com.shareyi.molicode.manager.conf.CommonExtInfoManager;
import com.shareyi.molicode.manager.AbstractManager;
import com.shareyi.molicode.builder.impl.CommonExtInfoBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用扩展信息Manager实现类
 * @author david
 * @date 2018-08-25
 */
@Component("commonExtInfoManager")
public class CommonExtInfoManagerImpl extends AbstractManager<CommonExtInfo> implements CommonExtInfoManager {

    @Resource
    private CommonExtInfoDao commonExtInfoDao;
    @Resource(name="commonExtInfoBuilder")
    CommonExtInfoBuilder commonExtInfoBuilder;


    /**
     * 通过owner & key 查询数据
     *
     * @param commonExtInfo
     * @return
     */
    public CommonExtInfo getByOwnerAndKey(CommonExtInfo commonExtInfo) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put(CommonExtInfoColumn.ownerType.name(), commonExtInfo.getOwnerType());
        queryParam.put(CommonExtInfoColumn.ownerCode.name(), commonExtInfo.getOwnerCode());
        queryParam.put(CommonExtInfoColumn.extKey.name(), commonExtInfo.getExtKey());
        queryParam.put(CommonExtInfoColumn.type.name(),commonExtInfo.getType());
        queryParam.put(BasicColumn.status.name(), StatusEnum.YES.getCode());
        List<CommonExtInfo> list = commonExtInfoDao.getListByExample(queryParam);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /******* getter and setter ***/
    public CommonExtInfoBuilder getBuilder() {
        return commonExtInfoBuilder;
    }
    public CommonExtInfoDao getDao() {
        return commonExtInfoDao;
    }
}
