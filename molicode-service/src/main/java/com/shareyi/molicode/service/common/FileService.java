package com.shareyi.molicode.service.common;

import com.shareyi.molicode.common.vo.FileVo;
import com.shareyi.molicode.common.web.CommonResult;

/**
 * 文件相关服务
 *
 * @author david
 * @date 2019/6/10
 */
public interface FileService {

    /**
     * 列举文件列表
     *
     * @param fileVo
     * @return
     */
    CommonResult listFiles(FileVo fileVo);

    /**
     * 列举仓库信息
     * @param repoName
     * @return
     */
    CommonResult listRepo(String repoName);
}
