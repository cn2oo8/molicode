/**
 * Copyright(C) 2004-2017 shareyi.com All Right Reserved
 */
package com.shareyi.molicode.validate;


import com.shareyi.molicode.common.exception.AutoCodeException;
import com.shareyi.molicode.domain.BasicDomain;

/**
 * <p> 基础的验证器</p>
 *
 * @author david
 * @date 2017-09-26 11:55
 */
public interface BaseValidator<T extends BasicDomain> {
    /***
     * 添加数据时的校验
     * @param obj
     * @throws AutoCodeException 如果校验不通过，则抛出异常
     */
    void addValid(T obj) throws AutoCodeException;

    /***
     * 修改数据时的数据校验
     * @param obj
     * @throws AutoCodeException 如果校验不通过，则抛出异常
     */
    void updateValid(T obj) throws AutoCodeException;

    /**
     * 查询参数验证
     * @param obj
     */
    void queryValid(T obj);

    /**
     * 删除验证
     * @param primaryKey
     */
    void deleteValid(Long primaryKey);
}
