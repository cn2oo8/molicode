/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.validate.provide;

import static com.shareyi.molicode.common.valid.Validate.*;

import com.shareyi.molicode.validate.AbstractValidator;
import com.shareyi.molicode.validate.BaseValidator;
import org.springframework.stereotype.Service;
import com.shareyi.molicode.domain.sys.AcUser;


/**
 * 用户信息 SDK 验证 类
 * @author david
 * @date 2018-08-21
 */
@Service("acUserValidator")
public class AcUserValidator extends AbstractValidator<AcUser> {
   @Override
   public void addValid(AcUser obj) {
       notNull(obj, "入参对象不能为空.");
   }

   @Override
   public void updateValid(AcUser obj) {
       notNull(obj, "入参对象不能为空.");
       notNull(obj.getId(), "主键ID不能为空.");
   }

}
