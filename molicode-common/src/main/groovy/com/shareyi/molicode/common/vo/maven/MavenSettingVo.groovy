package com.shareyi.molicode.common.vo.maven

/**
 * maven配置信息
 * @author david
 * @since 2018/9/1
 */
class MavenSettingVo {

    /**
     * java home
     */
    private String javaHome;
    /**
     * 本地仓库
     */
    private String localRepository;
    /**
     * maven 安装目录
     */
    private String mavenHome;
    /**
     * maven的自定义setting
     */
    private String mavenSetting;

    String getJavaHome() {
        return javaHome
    }

    void setJavaHome(String javaHome) {
        this.javaHome = javaHome
    }

    String getLocalRepository() {
        return localRepository
    }

    void setLocalRepository(String localRepository) {
        this.localRepository = localRepository
    }

    String getMavenHome() {
        return mavenHome
    }

    void setMavenHome(String mavenHome) {
        this.mavenHome = mavenHome
    }

    String getMavenSetting() {
        return mavenSetting
    }

    void setMavenSetting(String mavenSetting) {
        this.mavenSetting = mavenSetting
    }
}
