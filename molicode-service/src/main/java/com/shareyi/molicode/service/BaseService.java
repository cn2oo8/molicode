package com.shareyi.molicode.service;

import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.common.web.PageQuery;
import com.shareyi.molicode.domain.BasicDomain;

import java.util.List;

/**
 * 基础的服务类 service
 *
 * @author david
 * @date 2018/8/21
 */
public interface BaseService<T extends BasicDomain> {

    /**
     * 添加操作
     * @param t
     * @return
     */
    CommonResult<T> add(T t);

    /**
     * 修改操作
     * @param t
     * @return
     */
    CommonResult<T> update(T t);

    /**
     * 根据主键删除
     */
    CommonResult<String> deleteByPk(Long primaryKey);

    /**
     * 通过主键ID查询数据
     * @param primaryKey
     * @return
     */
    CommonResult<T> getByPk(Long primaryKey);

    /**
     * 分页查询数据
     * @param pageQuery
     * @return
     */
    CommonResult<List<T>> queryByPage(PageQuery pageQuery);

}
