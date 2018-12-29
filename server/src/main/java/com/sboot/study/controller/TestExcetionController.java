package com.sboot.study.controller;

import com.sboot.study.entity.OrderRecord;
import com.sboot.study.exception.NotFoundException;
import com.sboot.study.exception.SystemException;
import com.sboot.study.mybatisMapper.OrderRecordMapper;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.enums.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/9/23.
 */
@RestController
public class TestExcetionController {

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    private static final Logger log= LoggerFactory.getLogger(TestExcetionController.class);

    private static final String prefix="test";

    @RequestMapping(value = prefix+"/exception/advice/{id}",method = RequestMethod.GET)
    public BaseResponse exceptionAdvice(@PathVariable Integer id) throws SystemException {
        OrderRecord record=orderRecordMapper.selectByPrimaryKey(id);
        if (id==null || id<=0 || record==null){
            throw new SystemException("请求实体信息不存在");
        }

        BaseResponse response=new BaseResponse(StatusCode.SUCCESS);
        response.setData(record);
        return response;
    }

    @RequestMapping(value = prefix+"/exception/advice/not/found/{id}",method = RequestMethod.GET)
    public BaseResponse exceptionNotFound(@PathVariable Integer id) throws NotFoundException {
        OrderRecord record=orderRecordMapper.selectByPrimaryKey(id);
        if (id==null || id<=0 || record==null){
            throw new NotFoundException("请求实体信息不存在V2");
        }

        BaseResponse response=new BaseResponse(StatusCode.SUCCESS);
        response.setData(record);
        return response;
    }
}






































