package com.shareyi.molicode.common.vo.code;

/**
 * 依赖资源的配置
 * Created by david on 2016/4/24.
 */
class RequireConfigVo implements Comparable<RequireConfigVo> {

    /**
     * 字段列的标签，用于表示其展现形式
     */
    String columnTag;
    /**
     * 字段标签对于的requireJs模块
     */
    String requireModule;
    /**
     * 描述信息
     */
    String desc;
    /**
     * 依赖的script或者css等
     */
    String requireText;

    int sortNum = 0;


    @Override
    public int compareTo(RequireConfigVo o) {
        return this.sortNum - o.sortNum;
    }
}
