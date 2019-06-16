package com.shareyi.molicode.controller.repo;

import com.shareyi.molicode.common.vo.git.GitRepoVo;
import com.shareyi.molicode.service.git.GitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * git仓库服务
 *
 * @author zhangshibin
 * @date 2019/6/15
 */
@Controller
@RequestMapping("/repo/git")
public class GitController {

    @Resource
    private GitService gitService;

    @ResponseBody
    @RequestMapping("fetchRepo")
    public Map fetchRepo(GitRepoVo gitRepoVo) {
        return gitService.fetch(gitRepoVo).getReturnMap();
    }


}
