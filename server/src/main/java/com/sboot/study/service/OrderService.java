package com.sboot.study.service;

import com.sboot.study.entity.OrderRecord;
import com.sboot.study.mybatisMapper.OrderRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: faraway
 * @date: 2018/11/8 9:58
 * @description:
 */
@Service
public class OrderService {

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    public List<OrderRecord> getOrderList() throws Exception{
        List<OrderRecord> orderRecordList = orderRecordMapper.selectOrderList();
        return orderRecordList;
    }
}
