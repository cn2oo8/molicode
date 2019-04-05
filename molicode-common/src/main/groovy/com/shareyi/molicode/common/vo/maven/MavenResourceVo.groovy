package com.shareyi.molicode.common.vo.maven

/**
 * maven资源
 * @author david
 * @since 2018/9/1
 */
class MavenResourceVo extends MavenSettingVo{

    /**
     * groupId
     */
    private String groupId
    /**
     * 工程ID
     */
    private String artifactId;
    /**
     * 版本号
     */
    private String version;

    /**
     * maven 执行临时文件夹
     */
    private String mavenTempDir;

    /**
     * maven name
     */
    private String name;
    /**
     * 描述信息
     */
    private String description;
    /**
     * url地址
     */
    private String url;
    /**
     * 创建年份
     */
    String inceptionYear
    /**
     * 开发人员JSON
     */
    String developersJson

    String getGroupId() {
        return groupId
    }

    void setGroupId(String groupId) {
        this.groupId = groupId
    }

    String getArtifactId() {
        return artifactId
    }

    void setArtifactId(String artifactId) {
        this.artifactId = artifactId
    }

    String getVersion() {
        return version
    }

    void setVersion(String version) {
        this.version = version
    }

    String getMavenTempDir() {
        return mavenTempDir
    }

    void setMavenTempDir(String mavenTempDir) {
        this.mavenTempDir = mavenTempDir
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    @Override
    public String toString() {
        return "MavenResourceVo{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", mavenTempDir='" + mavenTempDir + '\'' +
                "} " + super.toString();
    }
}
