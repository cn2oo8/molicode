package com.shareyi.molicode.service.schedule;

import com.google.common.collect.Sets;
import com.shareyi.fileutil.FileUtil;
import com.shareyi.molicode.common.utils.LogHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;

/**
 * 定时任务
 *
 * @author zhangshibin
 * @date 2019-07-18
 */
@Component
@EnableScheduling // 1.开启定时任务
@EnableAsync //2.开启异步
public class ScheduleTask {

    /**
     * 每隔半小时删除零时文件夹信息
     *
     * @throws InterruptedException
     */
    @Async
    @Scheduled(fixedDelay = 1800 * 1000)  //间隔半小时
    public void wipeTempData() throws InterruptedException {
        LogHelper.DEFAULT.info("执行临时文件夹清理start");
        String outputFilePath = FileUtil.getRuntimeFilePath("code_output");
        File file = new File(outputFilePath);
        if (file.exists() && file.isDirectory()) {
            cleanTempFile(file);
        }
        LogHelper.DEFAULT.info("执行临时文件夹清理end");
    }

    private void cleanTempFile(File file) {
        File[] files = file.listFiles();
        for (File subFile : files) {
            cleanExceptedDirectory(subFile, Sets.newHashSet("sampleProject", "zip"));
        }
    }


    /**
     * 删除压缩包
     *
     * @param file
     */
    private void cleanExceptedDirectory(File file, Set<String> innerFindFileSet) {
        String name = file.getName();
        //清理子目录
        if (CollectionUtils.isNotEmpty(innerFindFileSet) &&
                innerFindFileSet.contains(name) && file.isDirectory()) {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                cleanExceptedDirectory(subFile, innerFindFileSet);
            }
        } else {
            if (isExpired(file)) {
                LogHelper.DEFAULT.info("定时清理临时文件夹，删除文件{},lastModified={}", file.getAbsolutePath(), file.lastModified());
                FileUtils.deleteQuietly(file);
            }
        }
    }


    /**
     * 文件是否已经过期，1小时过期
     *
     * @param file
     * @return
     */
    private boolean isExpired(File file) {
        return System.currentTimeMillis() - file.lastModified() > 3600 * 1000;
    }
}
