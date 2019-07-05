/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.sys.impl;

import com.shareyi.molicode.builder.impl.AcUserBuilder;
import com.shareyi.molicode.common.enums.StatusEnum;
import com.shareyi.molicode.dao.sys.AcUserDao;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.manager.AbstractManager;
import com.shareyi.molicode.manager.sys.AcUserManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息Manager实现类
 *
 * @author david
 * @date 2018-08-21
 */
@Component("acUserManager")
public class AcUserManagerImpl extends AbstractManager<AcUser> implements AcUserManager {

    @Resource
    private AcUserDao acUserDao;
    @Resource(name = "acUserBuilder")
    AcUserBuilder acUserBuilder;


    /******* getter and setter ***/
    public AcUserBuilder getBuilder() {
        return acUserBuilder;
    }

    public AcUserDao getDao() {
        return acUserDao;
    }

    @Override
    public AcUser getByUserName(String userName) {
        AcUser query = new AcUser();
        query.setUserName(userName);
        query.setStatus(StatusEnum.YES.getCode());
        List<AcUser> list = getListByExample(query);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }
}
