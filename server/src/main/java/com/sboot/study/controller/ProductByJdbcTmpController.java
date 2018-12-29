package com.sboot.study.controller;

import cn.hutool.core.map.MapUtil;
import com.sboot.study.entity.Product;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.enums.StatusCode;
import com.sboot.study.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 多数据源之jdbctemplate使用教程  使用的范畴：调用第三方
 */
@Api(description = "产品相关")
@RestController
public class ProductByJdbcTmpController {

    private static final Logger log = LoggerFactory.getLogger(ProductByJdbcTmpController.class);

    private static final String PREFIX = "product";

    @Autowired
    private ProductService productService;

    /**
     * 查询商品列表
     *
     * @return
     */
    @ApiOperation(value = "获取产品列表")
    @PostMapping(PREFIX + "/getProductList")
    public BaseResponse getProductList() {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            List<Product> productList = productService.getProductList();
            Map<String, Object> returnMap = MapUtil.newHashMap();
            returnMap.put("productList", productList);
            response.setData(returnMap);
        } catch (Exception e) {
            log.error("获取商品列表失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "获取商品列表失败！");
        }
        return response;
    }

    @ApiOperation("根据ID获取商品信息")
    @GetMapping(PREFIX + "/getProductByPrimaryId/{primaryId}")
    @ResponseBody
    public BaseResponse getProductByPrimaryId(@PathVariable Integer primaryId) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            Product product = productService.getProductByPrimaryId(primaryId);
            Map<String, Object> returnMap = MapUtil.newHashMap();
            returnMap.put("product", product);
            response.setData(returnMap);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.ENTITY_IS_NULL.getCode(), "该商品不存在！");
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL.getCode(), "获取商品信息失败！");
        }
        return response;
    }

    @ApiOperation(value = "新增产品")
    @PostMapping(value = PREFIX + "/insertProduct")
    public BaseResponse insertProduct(@RequestBody ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            int total = productService.insertProduct(valueMap);
            Map<String, Object> returnMap = MapUtil.newHashMap();
            returnMap.put("total", total);
            response.setData(returnMap);
        } catch (Exception e) {
            log.error("插入商品失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "插入商品失败！");
        }
        return response;
    }

    @ApiOperation(value = "修改商品信息")
    @PostMapping(PREFIX + "/updateProduct")
    public BaseResponse updateProduct(@RequestBody ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            int total = productService.updateProduct(valueMap);
            Map<String, Object> returnMap = MapUtil.newHashMap();
            returnMap.put("total", total);
            response.setData(returnMap);
            if (total == 0) {
                response = new BaseResponse(StatusCode.ENTITY_IS_NULL.getCode(), "该商品不存在！");
            }
        } catch (Exception e) {
            log.error("修改商品信息失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "修改商品信息失败！");
        }
        return response;
    }

    /**
     * 删除商品信息
     */
    @ApiOperation(value = "删除商品信息")
    @PostMapping(PREFIX + "/deleteProduct")
    public BaseResponse deleteProduct(@RequestBody ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            int total = productService.deleteProduct(valueMap);
            Map<String, Object> returnMap = MapUtil.newHashMap();
            returnMap.put("total", total);
            response.setData(returnMap);
            if (total == 0) {
                response = new BaseResponse(StatusCode.ENTITY_IS_NULL.getCode(), "该商品不存在！");
            }
        } catch (Exception e) {
            log.error("删除商品失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "删除商品失败！");
        }
        return response;
    }
}
