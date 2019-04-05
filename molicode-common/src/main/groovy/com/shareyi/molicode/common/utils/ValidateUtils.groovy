package com.shareyi.molicode.common.utils

import com.shareyi.molicode.common.valid.Validate

/**
 * 验证工具类
 * @author david
 * @since 2018/9/1
 */
class ValidateUtils {

   static void notNullField(Object obj, String fieldName){
        Validate.notNull(obj[fieldName], fieldName+"不能为空");
    }

    static void notEmptyField(Object obj, String fieldName){
        Validate.notEmpty(obj[fieldName], fieldName+"不能为空");
    }
}
