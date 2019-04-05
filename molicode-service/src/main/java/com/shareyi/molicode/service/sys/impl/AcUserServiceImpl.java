/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.sys.impl;

import javax.annotation.Resource;
import com.shareyi.molicode.builder.impl.AcUserBuilder;
import com.shareyi.molicode.manager.sys.AcUserManager;
import com.shareyi.molicode.service.sys.AcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.validate.provide.AcUserValidator;
import com.shareyi.molicode.service.AbstractService;
import org.springframework.stereotype.Service;

/**
 *  <p>用户信息 service实现</p>
 *
 * @author david
 * @date 2018-08-21
 *
 */
@Service("acUserService")
public class AcUserServiceImpl extends
    AbstractService<AcUser> implements AcUserService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AcUserServiceImpl.class);

    @Resource(name="acUserValidator")
    private AcUserValidator acUserValidator;

    @Resource(name="acUserManager")
    private AcUserManager acUserManager;

    @Resource(name="acUserBuilder")
    private AcUserBuilder acUserBuilder;


    /******* getter and setter ***/
    public AcUserManager getManager() {
        return acUserManager;
    }

    public AcUserValidator getValidator() {
        return acUserValidator;
    }

    @Override
    public AcUserBuilder getBuilder() {
        return acUserBuilder;
    }

}
