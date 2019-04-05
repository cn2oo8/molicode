package com.shareyi.molicode.manager;

import com.shareyi.molicode.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @autor david
 * @description 基本的manager, 非SDK版本
 * @date 2018/8/2
 */
public interface BaseManager<T> {

    /**
     * 获取Dao
     *
     * @return
     */
    BaseDao<T> getDao();

    /***
     * 根据id，获取到明细
     * @param id
     * @return
     */
    T getByPk(Long id);

    /***
     * 条件查询
     * @param params
     * @return
     */
    List<T> queryByPage(Map<String, Object> params);

    /***
     * 查询行数
     * @param params
     * @return
     */
    Long count(Map<String, Object> params);

    /***
     * 修改数据
     * @param dto
     * @return
     */
    int update(T dto);


    /***
     * 添加数据
     * @param t
     * @return 主键
     */
    Long add(T t);

    /***
     * 批量 添加数据
     * @param list
     * @return
     */
    int batchAdd(List<T> list);

    /**
     * 物理删除，仅供测试清理数据
     */
    int deleteByPk(Long id);

    /***
     * 条件查询,最大1000条
     * @param queryBean
     * @return
     */
     List<T> getListByExample(T queryBean);

}
