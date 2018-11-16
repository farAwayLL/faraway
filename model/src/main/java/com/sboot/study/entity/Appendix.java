package com.sboot.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * appendix
 * @author 
 */
public class Appendix implements Serializable {
    private Integer id;

    /**
     * 附件名
     */
    private String name;

    /**
     * 大小
     */
    private Long size;

    /**
     * 所属模块
     */
    private String moduleType;

    /**
     * 所属模块的记录id
     */
    private Integer recordId;

    /**
     * 是否删除(1=是；0=否)
     */
    private Integer isDelete;

    /**
     * 附件位置
     */
    private String location;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 排序
     */
    private Integer sortBy;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSortBy() {
        return sortBy;
    }

    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }
}