package com.shareyi.molicode.validate;

import com.shareyi.molicode.domain.BasicDomain;

import static com.shareyi.molicode.common.valid.Validate.notNull;

/**
 * 基础的验证器
 *
 * @author david
 * @date 2018/8/26
 */
public abstract class AbstractValidator<T extends BasicDomain> implements BaseValidator<T>{

    @Override
    public void addValid(T t) {
        notNull(t, "入参对象不能为空.");
    }

    @Override
    public void updateValid(T t) {
        notNull(t, "入参对象不能为空.");
        notNull(t.getId(), "主键ID不能为空.");
    }

    @Override
    public void queryValid(T t) {
        notNull(t, "入参对象不能为空.");
    }

    @Override
    public void deleteValid(Long primaryKey) {
        notNull(primaryKey, "主键值不能为空");
    }
}
