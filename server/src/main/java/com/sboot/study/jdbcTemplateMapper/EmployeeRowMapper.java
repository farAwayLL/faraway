package com.sboot.study.jdbcTemplateMapper;

import com.sboot.study.entity.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author : faraway
 * @Date : create in 2018/11/12 11:32
 * @Description :
 */
public class EmployeeRowMapper implements RowMapper<Employee> {
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setIdBo(resultSet.getString("id_bo"));
        employee.setMobile(resultSet.getString("mobile"));
        employee.setWbsPwd(resultSet.getString("wbs_pwd"));
        employee.setFinancialPwd(resultSet.getString("financial_pwd"));
        employee.setEmployeeId(resultSet.getString("employee_id"));
        employee.setRealName(resultSet.getString("real_name"));
        employee.setCertificatesType(resultSet.getInt("certificates_type"));
        employee.setIdcardNo(resultSet.getString("idcard_no"));
        employee.setAuthIdcardNo(resultSet.getString("auth_idcard_no"));
        employee.setCertificatesValid(resultSet.getString("certificates_valid"));
        employee.setAuthState(resultSet.getInt("auth_state"));
        employee.setLevel(resultSet.getByte("level"));
        employee.setAuthTime(resultSet.getLong("auth_time"));
        employee.setStudioname(resultSet.getString("studioName"));
        employee.setRecommender(resultSet.getString("recommender"));
        employee.setEmail(resultSet.getString("email"));
        employee.setSex(resultSet.getByte("sex"));
        employee.setArea(resultSet.getString("area"));
        employee.setPositionId(resultSet.getInt("position_id"));
        employee.setOrgId(resultSet.getInt("org_id"));
        employee.setType(resultSet.getByte("type"));
        employee.setState(resultSet.getByte("state"));
        employee.setOtherOrgId(resultSet.getString("other_org_id"));
        employee.setCreateTime(resultSet.getLong("create_time"));
        employee.setLastModifiedTime(resultSet.getLong("last_modified_time"));
        employee.setLastLoginTime(resultSet.getLong("last_login_time"));
        employee.setYn(resultSet.getByte("yn"));
        employee.setNickname(resultSet.getString("nickname"));
        employee.setOnJob(resultSet.getByte("on_job"));
        return employee;
    }
}
