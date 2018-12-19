package com.sboot.study.service;

import com.sboot.study.entity.ProductStock;
import com.sboot.study.mybatisMapper.ProductStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by faraway on 2018/12/14
 * description:着重看sql怎么写
 */

@Service
public class GroupSelectService {

    @Autowired
    private ProductStockMapper productStockMapper;

    public List<ProductStock> getNewestDataByGorup() {
        return productStockMapper.getNewestDataByGorup();
    }
}
