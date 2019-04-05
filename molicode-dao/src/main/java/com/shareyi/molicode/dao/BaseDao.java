package com.shareyi.molicode.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础DAO功能
 *
 * @author david
 * @date 2017/10/17
 */
public interface BaseDao<T> {

    /***
     * 根据主键，获取到明细
     * @param id
     * @return
     */
    T getByPk(Long id);

    /***
     * 条件查询,最大1000条
     * @param params
     * @return
     */
    List<T> getListByExample(Map<String, Object> params);

    /***
     * 分页条件查询
     * @param params
     * @return
     */
    List<T> queryByPage(Map<String, Object> params);

    /***
     * 分页查询行数，一定要传入分页信息
     * @param params
     * @return
     */
    Long count(Map<String, Object> params);

    /***
     * 修改数据
     * @param obj
     * @return
     */
    int update(T obj);


    /***
     * 添加数据
     * @param obj
     * @return
     */
    int add(T obj);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int batchAdd(List<T> list);

    /**
     * 物理删除，仅供测试清理数据
     */
    int deleteByPk(Long id);
}
