package com.sboot.study.jdbcTemplateMapper;

import com.sboot.study.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper <T>表示返回实体信息
 */
public class ProductRowMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setProductNo(resultSet.getString("product_no"));
        product.setCreateTime(resultSet.getDate("create_time"));
        product.setUpdateTime(resultSet.getDate("update_time"));
        return product;
    }
}
