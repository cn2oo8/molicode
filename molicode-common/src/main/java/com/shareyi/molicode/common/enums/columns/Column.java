/**
 * Copyright(C) 2004-2017 shareyi.com All Right Reserved
 */
package com.shareyi.molicode.common.enums.columns;

/**
 * <p> 列描述 </p>
 *
 * @author david
 * @date 2017-09-26 13:55
 */
public interface Column {
    /***
     * 获取到列的名称
     * @return
     */
    String getColumn();

    /**
     * 列的说明
     * @return
     */
    String getDesc();
}

