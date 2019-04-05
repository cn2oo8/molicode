/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.validate.provide;

import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.validate.AbstractValidator;
import org.springframework.stereotype.Service;

import static com.shareyi.molicode.common.valid.Validate.notEmpty;
import static com.shareyi.molicode.common.valid.Validate.notNull;


/**
 * 配置文件 SDK 验证 类
 * @author david
 * @date 2018-08-22
 */
@Service("acConfigValidator")
public class AcConfigValidator extends AbstractValidator<AcConfig> {
   @Override
   public void addValid(AcConfig obj) {
       notNull(obj, "入参对象不能为空.");
       notNull(obj.getProjectKey(), "projectKey不能为空.");
       notNull(obj.getConfigKey(), "configKey不能为空.");
       notEmpty(obj.getConfigValue(), "configValue不能为空.");
   }


    @Override
    public void deleteValid(Long primaryKey) {
        Validate.notNull(primaryKey, "主键值不能为空");
    }
}
