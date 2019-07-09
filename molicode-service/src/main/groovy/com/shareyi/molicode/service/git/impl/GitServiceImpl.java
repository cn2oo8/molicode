package com.shareyi.molicode.service.git.impl;

import com.shareyi.fileutil.FileUtil;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.utils.CostWatch;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.SystemFileUtils;
import com.shareyi.molicode.common.vo.git.GitRepoVo;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.git.GitService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * git服务实现
 *
 * @author zhangshibin
 * @date 2019/6/15
 */
@Service
public class GitServiceImpl implements GitService {

    @Override
    public CommonResult<String> fetch(GitRepoVo gitRepoVo) {
        CommonResult<String> result = CommonResult.create();
        try {
            String filePath = SystemFileUtils.buildGitRepoDir(gitRepoVo.getGitUrl(), gitRepoVo.getBranchName());
            File repoDir = new File(filePath);

            CostWatch costWatch = CostWatch.createStarted();
            Git git = null;
            CredentialsProvider credentialsProvider = buildCredentialsProvider(gitRepoVo);
            if (repoDir.exists() && isGitDirectory(repoDir)) {
                git = Git.open(repoDir);
                LogHelper.DEFAULT.info("git repo already exist, fetch from url={}, branch={}", gitRepoVo.getGitUrl(), gitRepoVo.getBranchName());
                PullCommand pull = git.pull();
                pull.setRemoteBranchName(gitRepoVo.getBranchName()).call();
                if (credentialsProvider != null) {
                    pull.setCredentialsProvider(credentialsProvider);
                }
                result.addDefaultModel("info", "仓库已存在，拉取最新内容到分支:" + gitRepoVo.getBranchName());
            } else {
                FileUtil.makeDir(repoDir);
                CloneCommand cloneCommand = Git.cloneRepository().setURI(gitRepoVo.getGitUrl()).setDirectory(repoDir).setBranch(gitRepoVo.getBranchName());
                if (credentialsProvider != null) {
                    cloneCommand.setCredentialsProvider(credentialsProvider);
                }
                git = cloneCommand
                        .call();
                LogHelper.DEFAULT.info("Cloning from " + gitRepoVo.getGitUrl() + " to " + git.getRepository());
                result.addDefaultModel("info", "仓库在本地还未存在，拉取最新内容到分支:" + gitRepoVo.getBranchName());
            }
            costWatch.stop();
            LogHelper.DEFAULT.info("拉取仓库模板，gitUrl={}, 耗时={}ms ", gitRepoVo.getGitUrl(), costWatch.getCost(TimeUnit.MILLISECONDS));
            result.addModel(MoliCodeConstant.ResultInfo.COST_TIME_KEY, costWatch.getCost(TimeUnit.SECONDS));
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("拉取git仓库失败, vo={}", gitRepoVo, e);
            result.failed("拉取git仓库失败，原因是：" + e.getMessage());
        }
        return result;
    }

    /**
     * 构建验证用户名&密码
     *
     * @param gitRepoVo
     * @return
     */
    private CredentialsProvider buildCredentialsProvider(GitRepoVo gitRepoVo) {
        CredentialsProvider credentialsProvider = null;
        if (StringUtils.isNotEmpty(gitRepoVo.getUserName()) && StringUtils.isNotEmpty(gitRepoVo.getPassword())) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(gitRepoVo.getUserName(), gitRepoVo.getPassword());
        }

        return credentialsProvider;
    }

    /**
     * 是否为git仓库文件
     *
     * @param repoDir
     * @return
     */
    private boolean isGitDirectory(File repoDir) {
        if (!repoDir.isDirectory()) {
            return false;
        }
        File[] files = repoDir.listFiles();
        if (files.length == 0) {
            return false;
        }
        for (File subFile : files) {
            if (Objects.equals(subFile.getName(), ".git")) {
                return true;
            }
        }
        return false;
    }
}
