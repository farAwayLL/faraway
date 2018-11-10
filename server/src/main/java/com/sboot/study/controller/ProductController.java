package com.sboot.study.controller;

import cn.hutool.core.map.MapUtil;
import com.sboot.study.dto.ProductMapperDto;
import com.sboot.study.entity.Product;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.response.StatusCode;
import com.sboot.study.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private static final String PREFIX = "product";

    //多数据源之product数据源
    @Resource(name = "productJdbcTemplate")
    private JdbcTemplate productJdbcTemplate;

    @Autowired
    private ProductService productService;

    @PostMapping(PREFIX + "/getProductList")
    public BaseResponse getProductList() {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            final String sql = "select * from product";
            List<Product> productList = productJdbcTemplate.query(sql, new ProductMapperDto());
            Map<String, Object> returnMap = MapUtil.newHashMap();
            returnMap.put("productList", productList);
            //手动打印日志，因为这里使用的事jdbctemplate，不走model层，所以日志扫描不到
            log.debug("Preparing：{}", sql);
            log.debug("Total：{}", productList.size());
            response.setData(returnMap);
        } catch (Exception e) {
            log.error("获取商品列表失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "获取商品列表失败！");
        }
        return response;
    }

}
