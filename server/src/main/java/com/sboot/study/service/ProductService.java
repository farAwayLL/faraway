package com.sboot.study.service;

import com.sboot.study.entity.Product;
import com.sboot.study.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<Product> getProductList() {
        List<Product> productList = productMapper.selectProductList();
        return productList;
    }
}
