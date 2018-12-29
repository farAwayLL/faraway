package com.sboot.study.controller;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.enums.StatusCode;
import com.sboot.study.service.EmployeeBatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author : faraway
 * @Date : create in 2018/11/12 11:10
 * @Description : 批量操作测试类  mybatis的批量操作效率大于jdbcTemplate
 */
@Api(description = "从第三方数据库将人事数据批量导入到自己的数据库")
@RestController
public class EmployeeBatchController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeBatchController.class);

    private static final String PREFIX = "employee";

    @Autowired
    private EmployeeBatchService employeeBatchService;

    @ApiOperation("JdbcTemplate批量插入测试")
    @PostMapping(PREFIX + "/insertEmployeeByJdbcTmplate")
    public BaseResponse insertEmployeeByJdbcTmplate() {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            long timeStart = System.currentTimeMillis();
            int total = employeeBatchService.insertEmoloyeeByJdbcTemplate();
            long timeEnd = System.currentTimeMillis();
            long time = timeEnd - timeStart;
            log.debug("JdbcTemplate批量插入耗时：{}", time);
            Map<String, Object> returnMap = MapUtil.newHashMap();
            returnMap.put("total", total);
            response.setData(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL.getCode(), "JdbcTemplate批量插入失败！");
        }
        return response;
    }


    @ApiOperation("mybatis批量插入测试")
    @PostMapping(PREFIX + "/insertEmployeeByMybatis")
    public BaseResponse insertEmployeeByMybatis() {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            long startTime = System.currentTimeMillis();
            int total = employeeBatchService.insertEmployeeByMybatis();
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            log.debug("mybatis批量插入耗时：{}", time);
            Map<String,Object> returnMap = Maps.newHashMap();
            returnMap.put("total",total);
            response.setData(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL.getCode(), "mybatis批量插入失败！");
        }
        return response;
    }
}
