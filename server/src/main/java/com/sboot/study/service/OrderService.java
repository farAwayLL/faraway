package com.sboot.study.service;

import cn.hutool.core.map.MapUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sboot.study.entity.OrderRecord;
import com.sboot.study.mybatisMapper.OrderRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: faraway
 * @date: 2018/11/8 9:58
 * @description:
 */
@Service
public class OrderService {

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    public Map<String, Object> getOrderList(Map<String, Object> requestMap) throws Exception {
        Integer startPage = Integer.valueOf(requestMap.get("startPage").toString());
        Integer pageSize = Integer.valueOf(requestMap.get("pageSize").toString());
        //分页插件
        PageHelper.startPage(startPage, pageSize);
        List<OrderRecord> orderRecordList = orderRecordMapper.selectOrderList();
        Page page = (Page) orderRecordList;
        //获取总数
        long total = page.getTotal();
        Map<String, Object> returnMap = MapUtil.newHashMap();
        returnMap.put("orderRecordList", orderRecordList);
        returnMap.put("total", total);
        return returnMap;
    }
}
