package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.valid.Validate;

import java.util.concurrent.TimeUnit;

/**
 * 用时监控表
 *
 * @author zhangshibin
 * @date 2019/6/15
 */
public class CostWatch {

    /**
     * 起始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long stopTime;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * 创建一个已经启动的表
     *
     * @return
     */
    public static CostWatch createStarted() {
        CostWatch costWatch = new CostWatch();
        costWatch.start();
        return costWatch;
    }

    /**
     * 启动
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
    }

    /**
     * 获取耗时
     *
     * @param timeUnit
     * @return
     */
    public long getCost(TimeUnit timeUnit) {
        long cost = getCost();
        switch (timeUnit) {
            case DAYS:
                return cost / (24 * 3600 * 1000);
            case HOURS:
                return cost / (3600 * 1000);
            case MINUTES:
                return cost / (60 * 1000);
            case SECONDS:
                return cost / (1 * 1000);
            case MILLISECONDS:
                return cost;
            default:
                Validate.assertTrue(false, "不支持的类型");
        }
        return 0;
    }

    /**
     * 获取耗时 毫秒
     *
     * @return
     */
    private long getCost() {
        return stopTime - startTime;
    }
}
