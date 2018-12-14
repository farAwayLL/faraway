package com.sboot.study.mybatisMapper;

import com.sboot.study.entity.ProductStock;

import java.util.List;

public interface ProductStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductStock record);

    int insertSelective(ProductStock record);

    ProductStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductStock record);

    int updateByPrimaryKey(ProductStock record);

    List<ProductStock> getNewestDataByGorup();
}