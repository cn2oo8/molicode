/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.conf.impl;

import com.shareyi.molicode.common.enums.columns.AcProjectColumn;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.shareyi.molicode.domain.conf.AcProject;
import com.shareyi.molicode.dao.conf.AcProjectDao;
import com.shareyi.molicode.manager.conf.AcProjectManager;
import com.shareyi.molicode.manager.AbstractManager;
import com.shareyi.molicode.builder.impl.AcProjectBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目Manager实现类
 * @author david
 * @date 2018-08-22
 */
@Component("acProjectManager")
public class AcProjectManagerImpl extends AbstractManager<AcProject> implements AcProjectManager {

    @Resource
    private AcProjectDao acProjectDao;
    @Resource(name="acProjectBuilder")
    AcProjectBuilder acProjectBuilder;


    @Override
    public AcProject getByProjectKey(String projectKey) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put(AcProjectColumn.projectKey.name(), projectKey);
        List<AcProject> list = acProjectDao.getListByExample(queryParam);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /******* getter and setter ***/
    public AcProjectBuilder getBuilder() {
        return acProjectBuilder;
    }
    public AcProjectDao getDao() {
        return acProjectDao;
    }

}
