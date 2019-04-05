package com.shareyi.molicode.builder;

import com.shareyi.molicode.common.beanhelper.BeanConventor;
import com.shareyi.molicode.domain.BasicDomain;

import java.util.Map;
import java.util.Set;

/**
 * 抽象的dtoBuilder
 *
 * @author david
 * @date 2017/9/27
 */
public abstract class AbstractBuilder<T extends BasicDomain> implements BaseBuilder<T> {

    /**
     * 数据库列名
     */
    protected Set<String> columnSet;

    /**
     * 检查数据库列名是否合法
     *
     * @param fields
     * @return
     */
    @Override
    public Set<String> checkColumns(Set<String> fields) {
        return AbstractBuilderHelper.checkFiledStatic(fields, this.columnSet);
    }

    /**
     * 封装新增参数
     *
     * @param dto
     * @return
     */
    @Override
    public T supplyAddInfo(T dto) {
        AbstractBuilderHelper.supplyAddInfo(dto);
        return dto;
    }

    /**
     * 封装修改参数
     *
     * @param dto
     * @return
     */
    @Override
    public T supplyUpdateInfo(T dto) {
        AbstractBuilderHelper.supplyUpdateInfo(dto);
        return dto;
    }

    /**
     * dto转换为本地查询map
     *
     * @param t bean
     * @return 本地查询map
     */
    @Override
    public Map<String, Object> buildQueryMap(T t) {
        Map<String, Object> queryMap = BeanConventor.buildQueryMapByBean(t);
        return queryMap;
    }

    /**
     * 新增方法后回调,默认为空操作t
     *
     * @param localBean
     * @return
     */
    @Override
    public T addCallBack(T localBean) {
        return localBean;
    }

}
