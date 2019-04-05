package com.shareyi.molicode.common.beanhelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询字段说明
 *
 * @author david
 * @date 2017/9/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface BeanQueryFiled {

    /**
     * 是否忽略，不作为查询字段
     */
    boolean ignore() default false;

    /**
     * 转换为的查询KEY
     */
    String queryKey() default "";


    /**
     * 前置拼接串
     */
    String prepend() default "";


}
