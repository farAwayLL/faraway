package com.sboot.study.mapper;

import com.sboot.study.entity.Employee;

import java.util.List;

public interface TbEmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    int insertByBatch(List<Employee> employeeList);
}