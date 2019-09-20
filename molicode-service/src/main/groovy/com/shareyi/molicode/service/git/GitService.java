package com.shareyi.molicode.service.git;

import com.shareyi.molicode.common.vo.git.GitRepoVo;
import com.shareyi.molicode.common.web.CommonResult;

/**
 * git相关服务
 *
 * @author david
 * @date 2019/6/14
 */
public interface GitService {

    /**
     * 拉取仓库资源
     *
     * @param gitRepoVo
     * @return
     */
    CommonResult<String> fetch(GitRepoVo gitRepoVo);

}
