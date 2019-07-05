package com.shareyi.molicode.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户权限注解
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAuthPrivilege {

    /**
     * 权限码，暂时未使用
     *
     * @return
     */
    String code() default "";

    /**
     * 权限等级
     *
     * @return
     */
    int level() default 0;
}