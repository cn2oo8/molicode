package com.shareyi.molicode.common.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 不用guava的list，非常安全
 *
 * @author david
 * @date 2017/6/1
 */
public class MyLists {

    /**
     * @param fromList 来源list
     * @param function 转换工具
     * @param <F> 入参对象
     * @param <T> 出参对象
     * @return 转换后的list
     */
    public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
        return transformInner(fromList, function, false);
    }

    /**
     * 将List对象，转换为list，并过滤为空值
     * @param fromList  来源list
     * @param function 转换工具
     * @param <F> 入参对象
     * @param <T> 出参对象
     * @return 转换后的list
     */
    public static <F, T> List<T> transformNotNull(List<F> fromList, Function<? super F, ? extends T> function) {
        return transformInner(fromList, function, true);
    }

    /**
     * 内部转换 List
     *
     * @param fromList   来源list
     * @param function   转换工具
     * @param filterNull 是否过滤null值
     * @param <F>        入参对象
     * @param <T>        出参对象
     * @return 返回转换后的列表
     */
    private static <F, T> List<T> transformInner(List<F> fromList,
                                                 Function<? super F, ? extends T> function,
                                                 boolean filterNull) {
        if (CollectionUtils.isEmpty(fromList)) {
            return new ArrayList<T>(0);
        }

        List<T> resultList = new ArrayList<T>(fromList.size());
        for (F f : fromList) {
            T data = function.apply(f);
            if (filterNull && data == null) {
                continue;
            }
            resultList.add(data);
        }
        return resultList;
    }


    /**
     * 将List对象，转换为Set对象
     *
     * @param fromList 原list
     * @param function 转换function
     * @param <F>      原数据
     * @param <T>      目标数据
     * @return 返回转换后的set
     */
    public static <F, T> Set<T> transformSet(List<F> fromList, Function<? super F, ? extends T> function) {
        return transformSetInner(fromList, function, false);
    }


    /**
     * 将List对象，转换为Set对象，并过滤为空值
     *
     * @param fromList 原list
     * @param function 转换function
     * @param <F>      原数据
     * @param <T>      目标数据
     * @return 返回转换后的set ,notNull
     */
    public static <F, T> Set<T> transformSetNotNull(List<F> fromList,
                                                    Function<? super F, ? extends T> function) {
        return transformSetInner(fromList, function, true);
    }


    /**
     * 内部执行
     *
     * @param fromList   原list
     * @param function   转换function
     * @param filterNull 是否过滤null
     * @param <F>        原数据
     * @param <T>        目标数据
     * @return
     */
    private static <F, T> Set<T> transformSetInner(List<F> fromList,
                                                   Function<? super F, ? extends T> function,
                                                   boolean filterNull) {
        if (CollectionUtils.isEmpty(fromList)) {
            return new HashSet<T>(0);
        }

        HashSet<T> resultList = new HashSet<T>(fromList.size());
        for (F f : fromList) {
            T data = function.apply(f);
            if (filterNull && data == null) {
                continue;
            }
            resultList.add(data);
        }
        return resultList;
    }

    /**
     * 执行数据分拆，并返回安全的arrayList对象
     *
     * @param fromList 原list
     * @param size     最大长度
     * @param <T>      数据类型
     * @return
     */
    public static <T> List<List<T>> partition(List<T> fromList, int size) {
        List<List<T>> partList = Lists.partition(fromList, size);
        List<List<T>> resultList = new ArrayList<List<T>>(partList.size());
        for (List<T> part : partList) {
            resultList.add(new ArrayList<T>(part));
        }
        return resultList;
    }

    /**
     * 执行数据分拆，并返回安全的HashSet对象
     *
     * @param fromList 原list
     * @param size     最大长度
     * @param <T>      数据类型
     * @return
     */
    public static <T> List<Set<T>> partitionSet(List<T> fromList, int size) {
        List<List<T>> partList = Lists.partition(fromList, size);
        List<Set<T>> resultList = new ArrayList<Set<T>>(partList.size());
        for (List<T> part : partList) {
            resultList.add(new HashSet<T>(part));
        }
        return resultList;
    }

    /**
     * 将列表转化为指定map对象
     *
     * @param list          列表
     * @param functionKey   key转化函数
     * @param functionValue
     * @param <S>           列表元素类型
     * @param <T>           mapping key
     * @return map 对象
     */
    public static <S, T, U> Map<S, T> transformToMap(List<U> list, Function<U, S> functionKey, Function<U, T> functionValue) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(list)) {
            return Maps.newHashMapWithExpectedSize(0);
        }

        Map<S, T> map = Maps.newHashMap();
        for (U element : list) {
            if (functionValue.apply(element) != null) {
                map.put(functionKey.apply(element), functionValue.apply(element));
            }
        }
        return map;
    }
}
