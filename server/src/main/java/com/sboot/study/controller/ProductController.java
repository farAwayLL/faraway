package com.sboot.study.controller;

import cn.hutool.core.map.MapUtil;
import com.sboot.study.entity.Product;
import com.sboot.study.jdbcTemplateMapper.ProductMapper;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.response.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 多数据源之jdbctemplate使用教程  使用的范畴：调用第三方
 */
@Api(description = "产品相关")
@RestController
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private static final String PREFIX = "product";

    //获取product非数据源 并注入到JdbcTemplate中
    @Resource(name = "productJdbcTemplate")
    private JdbcTemplate productJdbcTemplate;

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
            //使用Jdbctemplate，查询多数据源数据，可以不用创建ProductMapper.java和ProductMapper.xml，因为不走mybatis
            final String sql = "select * from product";
            List<Product> productList = productJdbcTemplate.query(sql, new ProductMapper());
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

    @ApiOperation(value = "新增产品")
    @PostMapping(value = PREFIX + "/insertProduct")
    public BaseResponse insertProduct(@RequestBody final ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            log.debug("接受前端数据为：{}", valueMap);
            final String sql = "insert into product(name,product_no) values(?,?)";
            productJdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, valueMap.get("name").toString());
                    preparedStatement.setString(2, valueMap.get("productNo").toString());
                }
            });
        } catch (Exception e) {
            log.error("插入商品失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "插入商品失败！");
        }
        return response;
    }

    @ApiOperation(value = "修改商品信息")
    @PostMapping(PREFIX + "/updateProduct")
    public BaseResponse updateProduct(@RequestBody final ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            log.debug("接受到的数据：{}", valueMap);
            final String sql = "update product set name=?,product_no=? where id=?";
            int total = productJdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, valueMap.get("name").toString());
                    preparedStatement.setString(2, valueMap.get("productNo").toString());
                    preparedStatement.setInt(3, Integer.valueOf(valueMap.get("id").toString()));
                }
            });
            if (total == 0) {
                response = new BaseResponse(StatusCode.FAIL.getCode(), "该商品不存在！");
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
    public BaseResponse deleteProduct(@RequestBody final ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            log.debug("接收到的数据：{}", valueMap);
            final String sql = "delete from product where id=?";
            int total = productJdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setInt(1, Integer.valueOf(valueMap.get("id").toString()));
                }
            });
            if (total == 0) {
                response = new BaseResponse(StatusCode.FAIL.getCode(), "该商品不存在！");
            }
        } catch (Exception e) {
            log.error("删除商品失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "删除商品失败！");
        }
        return response;
    }

}
