package com.sboot.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * product_stock
 * @author 
 */
public class ProductStock implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 产品名
     */
    private String name;

    /**
     * 产品价格
     */
    private Long price;

    /**
     * 产品类型
     */
    private Byte type;

    /**
     * 创建时间
     */
    private Date createTime;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}