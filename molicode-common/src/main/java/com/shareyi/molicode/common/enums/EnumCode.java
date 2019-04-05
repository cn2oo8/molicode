package com.shareyi.molicode.common.enums;

import java.util.EnumSet;

/**
 * EnumCode
 *
 * @author david
 * @date 2018/8/11
 */
public interface EnumCode<T> {

    T getCode();

    String getDesc();

    class Parser {

        /**
         * 把传递的值转换成指定的枚举类型
         *
         * 注意:会通过{@code equals()}方法对
         * {@linkplain EnumCode#getCode()}
         * 返回值与value进行比较
         *
         * @param type  需要转换的枚举类型
         * @param value 传递的值
         * @return 指定的枚举类型
         * @throws IllegalArgumentException 如果没有匹配成功则抛出异常
         */
        public static <T, E extends Enum<E>> E parseTo(Class<E> type, T value) {
            E en = parseToNullSafe(type, value);
            if (en != null) {
                return en;
            }
            throw new IllegalArgumentException(String.format("Invalid argument [%s]", value));
        }


        /**
         * 把传递的值转换成指定的枚举类型
         * 注意:会通过{@code equals()}方法对
         * {@linkplain EnumCode#getCode()}
         * 返回值与value进行比较
         *
         * @param type  需要转换的枚举类型
         * @param value 传递的值
         * @return 指定的枚举类型
         * @throws IllegalArgumentException 如果没有匹配成功则返回空
         */
        public static <T, E extends Enum<E>> E parseToNullSafe(Class<E> type, T value) {
            return parseToNullSafe(type, value, null);
        }


        /**
         * 把传递的值转换成指定的枚举类型
         * 注意:会通过{@code equals()}方法对
         * {@linkplain EnumCode#getCode()}
         * 返回值与value进行比较
         *
         * @param type    需要转换的枚举类型
         * @param value   传递的值
         * @param defType 默认值
         * @return 指定的枚举类型
         * @throws IllegalArgumentException 如果没有匹配成功则返回默认值
         */
        public static <T, E extends Enum<E>> E parseToNullSafe(Class<E> type, T value, E defType) {
            EnumSet<E> enums = EnumSet.allOf(type);
            for (Enum<E> en : enums) {
                if (!(en instanceof EnumCode)) {
                    throw new IllegalArgumentException(type + " is not implemented " + EnumCode.class);
                }
                EnumCode<T> code = (EnumCode<T>) en;
                if (code.getCode().equals(value)) {
                    return (E) en;
                }
            }
            return defType;
        }


        /**
         * 获取{@linkplain EnumCode}枚举值
         *
         * @param enumCode
         * @param <T>
         * @return 如果enumCode为null, 则返回null
         */
        public static <T> T getCode(EnumCode<T> enumCode) {
            return getCode(enumCode, null);
        }

        /**
         * 获取{@linkplain EnumCode}枚举值
         *
         * @param enumCode
         * @param defaults
         * @param <T>
         * @return 如果enumCode为null, 则返回defaults默认值
         */
        public static <T> T getCode(EnumCode<T> enumCode, T defaults) {
            if (enumCode != null) {
                return enumCode.getCode();
            }
            return defaults;
        }
    }
}
