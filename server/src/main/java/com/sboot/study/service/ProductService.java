package com.sboot.study.service;

import com.google.common.collect.Maps;
import com.sboot.study.entity.Product;
import com.sboot.study.entity.TProduct;
import com.sboot.study.jdbcTemplateMapper.ProductRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author : faraway
 * @Date : create in 2018/11/13 10:53
 * @Description :
 */

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(Product.class);

    //获取product非数据源 并注入到JdbcTemplate中
    @Resource(name = "productJdbcTemplate")
    private JdbcTemplate productJdbcTemplate;

    public List<Product> getProductList() throws Exception {
        //使用Jdbctemplate，查询多数据源数据，可以不用创建ProductMapper.java和ProductMapper.xml，因为不走mybatis
        final String sql = "select * from product";
        List<Product> productList = productJdbcTemplate.query(sql, new ProductRowMapper());
        //这里手动打印日志，因为这里使用的是jdbctemplate，不走model模块，所以日志扫描不到
        log.debug("Preparing：{}", sql);
        log.debug("Total：{}", productList.size());
        return productList;
    }

    public Product getProductByPrimaryId(Integer primaryId) throws Exception {
        final String sql = "select * from product where id=?";
        Product product = productJdbcTemplate.queryForObject(sql, new Object[]{primaryId}, new ProductRowMapper());
        log.debug("Preparing：{}", sql);
        log.debug("Total：{}", product.toString());
        return product;
    }

    public Integer insertProduct(final ModelMap valueMap) throws Exception {
        final String sql = "insert into product(name,product_no) values(?,?)";
        int total = productJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, valueMap.get("name").toString());
                preparedStatement.setString(2, valueMap.get("productNo").toString());
            }
        });
        return total;
    }

    public Integer updateProduct(final ModelMap valueMap) {
        final String sql = "update product set name=?,product_no=? where id=?";
        int total = productJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, valueMap.get("name").toString());
                preparedStatement.setString(2, valueMap.get("productNo").toString());
                preparedStatement.setInt(3, Integer.valueOf(valueMap.get("id").toString()));
            }
        });
        return total;
    }

    public Integer deleteProduct(final ModelMap valueMap) {
        final String sql = "delete from product where id=?";
        int total = productJdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, Integer.valueOf(valueMap.get("id").toString()));
            }
        });
        return total;
    }

    public List<Map<Integer, Object>> manageProductList(List<TProduct> productList) {
        List<Map<Integer, Object>> listMap = new LinkedList<Map<Integer, Object>>();

        Map<Integer, Object> rowMap;
        for (TProduct p : productList) {
            rowMap = Maps.newHashMap();

            rowMap.put(0, p.getName());
            rowMap.put(1, p.getUnit());
            rowMap.put(2, p.getPrice());
            rowMap.put(3, p.getStock());
            rowMap.put(4, p.getRemark());
            rowMap.put(5, p.getPurchaseDate());

            listMap.add(rowMap);
        }
        return listMap;
    }
}
