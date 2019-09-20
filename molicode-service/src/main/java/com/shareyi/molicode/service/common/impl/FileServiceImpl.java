package com.shareyi.molicode.service.common.impl;

import com.shareyi.molicode.common.enums.RepoTypeEnum;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.common.utils.SystemFileUtils;
import com.shareyi.molicode.common.vo.FileInfoVo;
import com.shareyi.molicode.common.vo.FileVo;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.common.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * 文件服务
 *
 * @author david
 * @date 2019/6/10
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public CommonResult listFiles(FileVo fileVo) {
        CommonResult result = CommonResult.create();
        if (Profiles.getInstance().isHeadLess()) {
            return result.failed("headless下无法支持");
        }

        if (StringUtils.isEmpty(fileVo.getParentPath())) {
            return result.failed("directory path is null");
        }
        String path = SystemFileUtils.parseFilePath(fileVo.getParentPath());
        final File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (StringUtils.isNotEmpty(fileVo.getFileExt()) &&
                            pathname.getName().endsWith(fileVo.getFileExt())) {
                        return true;
                    }
                    return false;
                }
            });

            List<FileInfoVo> fileList = SystemFileUtils.getFileInfo(files);
            result.addModel("fileList", fileList);
            result.setSuccess(true);
        } else {
            result.failed("dirctory [" + path + "] is not exists");
        }
        return result;
    }

    @Override
    public CommonResult listRepo(String repoName) {
        CommonResult result = CommonResult.create();
        if (StringUtils.isEmpty(repoName)) {
            return result.failed("repoName is null");
        }
        try {
            RepoTypeEnum.Parser.parseTo(RepoTypeEnum.class, repoName);
            String path = SystemFileUtils.buildRepoPath(repoName);
            final File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles(new FileFilter() {
                    public boolean accept(File subFile) {
                        return subFile.isDirectory();
                    }
                });
                List<FileInfoVo> fileList = SystemFileUtils.getFileInfo(files);
                result.addModel("fileList", fileList);
                result.succeed();
            } else {
                result.failed("dirctory [" + path + "] is not exists");
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取仓库列表异常，repoName={}", repoName, e);
            result.failed("获取仓库列表异常，message=" + e.getMessage());
        }
        return result;
    }
}
