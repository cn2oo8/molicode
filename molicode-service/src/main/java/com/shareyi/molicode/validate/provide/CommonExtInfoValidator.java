/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.validate.provide;

import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.validate.AbstractValidator;
import org.springframework.stereotype.Service;

import static com.shareyi.molicode.common.valid.Validate.notEmpty;
import static com.shareyi.molicode.common.valid.Validate.notNull;


/**
 * 通用扩展信息 SDK 验证 类
 *
 * @author david
 * @date 2018-08-25
 */
@Service("commonExtInfoValidator")
public class CommonExtInfoValidator extends AbstractValidator<CommonExtInfo> {
    @Override
    public void addValid(CommonExtInfo obj) {
        notNull(obj, "入参对象不能为空.");
        notNull(obj.getOwnerType(), "ownerType不能为空.");
        notEmpty(obj.getOwnerCode(), "ownerCode不能为空.");
        notEmpty(obj.getExtKey(), "key不能为空.");
    }


    @Override
    public void queryValid(CommonExtInfo obj) {
        notNull(obj, "入参对象不能为空.");
        notNull(obj.getOwnerType(), "ownerType不能为空.");
        notEmpty(obj.getOwnerCode(), "ownerCode不能为空.");
        notEmpty(obj.getExtKey(), "key不能为空.");
    }
}
