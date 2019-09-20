package com.shareyi.molicode.common.constants;

import com.shareyi.molicode.common.vo.git.GitRepoVo;
import org.apache.commons.lang3.StringUtils;

/**
 * 缓存key常量
 *
 * @author david
 * @date 2019/7/5
 */
public class CacheKeyConstant {

    /**
     * 锁定时间
     */
    public static final long LOCK_TIME_MSEC = 10 * 60 * 1000;

    /**
     * 获取缓存的用户信息
     *
     * @param userName
     * @return
     */
    public static String getAcUserCacheKey(String userName) {
        return "ac_user_" + StringUtils.lowerCase(userName.trim());
    }

    /**
     * 失败次数key
     *
     * @param userName
     * @return
     */
    public static String getLoginFailureKey(String userName) {
        return "login_fail_" + StringUtils.lowerCase(userName.trim());
    }

    /**
     * 获取git 锁定key
     *
     * @param gitRepoVo
     * @return
     */
    public static String buildGitRepoLock(GitRepoVo gitRepoVo) {
        return "lock_git_" + gitRepoVo.getGitUrl() + "_" + gitRepoVo.getBranchName();
    }
}
