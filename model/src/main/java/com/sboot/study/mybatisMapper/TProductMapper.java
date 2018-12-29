package com.sboot.study.mybatisMapper;

import com.sboot.study.entity.TProduct;

import java.util.List;
import java.util.Map;

public interface TProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);

    //查询产品列表，有分页(PageHelper)  导出产品列表excel，不分页
    List<TProduct> selectProductList(Map<String, Object> map);

    int insertBatch(List<TProduct> products);
}