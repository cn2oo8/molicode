package com.shareyi.molicode.common.vo.git;

/**
 * git仓库相关配置
 *
 * @author zhangshibin
 * @date 2019/6/14
 */
class GitRepoVo {
    /**
     * 远程地址
     */
    String gitUrl;

    /**
     * 分支名称
     */
    String branchName;
    /**
     * 用户名
     */
    String userName;
    /**
     * 密码
     */
    String password;

    /**
     * 加密密码
     */
    String encryptPassword;

    /**
     * 模板相对路径
     */
    String templateRelativePath;

    @Override
    String toString() {
        return "GitRepoVo{" +
                "gitUrl='" + gitUrl + '\'' +
                ", branchName='" + branchName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password ==null? "null":"notNull" + '\'' +
                ", encryptPassword='" + encryptPassword + '\'' +
                '}';
    }
}
