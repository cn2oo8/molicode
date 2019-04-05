package com.shareyi.molicode.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础通用Domain， 一般被继承使用
 *
 * @author david
 * @date 2018/1/28
 */
public class BasicDomain implements Serializable {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改人
     */
    private String operator;
    /**
     * 数据有效性；1
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 最后修改时间
     */
    private Date modified;
    /**
     * 并发版本号，从1开始
     */
    private Integer concurrentVersion;
    /**
     * 数据版本号
     */
    private Integer dataVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getConcurrentVersion() {
        return concurrentVersion;
    }

    public void setConcurrentVersion(Integer concurrentVersion) {
        this.concurrentVersion = concurrentVersion;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    @Override
    public String toString() {
        return "BasicDomain{" +
                "id=" + id +
                ", creator='" + creator + '\'' +
                ", operator='" + operator + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", modified=" + modified +
                ", concurrentVersion=" + concurrentVersion +
                ", dataVersion=" + dataVersion +
                '}';
    }
}
