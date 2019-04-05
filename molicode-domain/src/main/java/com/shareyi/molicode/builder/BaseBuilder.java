package com.shareyi.molicode.builder;

import com.shareyi.molicode.domain.BasicDomain;

import java.util.Map;
import java.util.Set;

/**
 * 基础的builder
 *
 * @author david
 * @date 2018/8/21
 */
public interface BaseBuilder<T extends BasicDomain> {

    /**
     * 检查数据库列名
     * @param fields
     * @return
     */
    Set<String> checkColumns(Set<String> fields);

    /**
     * 新增
     * @param dto
     * @return
     */
    T supplyAddInfo(T dto);

    /**
     * 修改数据
     * @param dto
     * @return
     */
    T supplyUpdateInfo(T dto);

    /**
     * bean 转map
     * @param t
     * @return
     */
    Map<String, Object> buildQueryMap(T t);

    /**
     * 新增成功后回调
     * @param localBean
     * @return
     */
    T addCallBack(T localBean);

    /**
     * 获取业务操作名称
     * @return
     */
    String getBizName();

    /**
     * 获取业务class
     * @return
     */
    Class getBizClass();
}
