package com.sboot.study.service;

import com.sboot.study.entity.Employee;
import com.sboot.study.jdbcTemplateMapper.EmployeeRowMapper;
import com.sboot.study.mybatisMapper.TbEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author : faraway
 * @Date : create in 2018/11/13 10:21
 * @Description :
 */

@Service
public class EmployeeBatchService {

    @Resource(name = "employeeJdbcTemplate")
    private JdbcTemplate employeeJdbcTemplate;

    @Resource(name = "productJdbcTemplate")
    private JdbcTemplate productJdbcTemplate;

    @Autowired
    private TbEmployeeMapper employeeMapper;

    public Integer insertEmoloyeeByJdbcTemplate() throws Exception {
        //获取第三方人事数据  employeeJdbcTemplate是第三方数据源
        final String querySql = "select * from tb_employee limit 0,5000";
        final List<Employee> employeeList = employeeJdbcTemplate.query(querySql, new EmployeeRowMapper());

        final String updSql = "insert into tb_employee (id_bo, mobile, wbs_pwd, financial_pwd, employee_id, real_name, " +
                "certificates_type,idcard_no, auth_idcard_no, certificates_valid, auth_state, level, auth_time,studioName," +
                "recommender, email, sex, area, position_id, org_id, type, state, other_org_id, create_time, last_modified_time," +
                " last_login_time, yn, nickname, on_job) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        /**
         * 批量插入语法：batchUpdate(sql,new BatchPreparedStatementSetter(){setValues();getBatchSize();});
         */
        //获取连接
        //Connection con = productJdbcTemplate.getDataSource().getConnection();
        /**把自动提交置为false。不能合并成：productJdbcTemplate.getDataSource().getConnection().setAutoCommit(false);这样设置无效，因为使用的是连接池*/
        /**经过测试，设置成自动提交和非自动提交效率差不多，反正都比mybatis的慢*/
        //con.setAutoCommit(false);
        int[] total = productJdbcTemplate.batchUpdate(updSql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, employeeList.get(i).getIdBo());
                preparedStatement.setString(2, employeeList.get(i).getMobile());
                preparedStatement.setString(3, employeeList.get(i).getWbsPwd());
                preparedStatement.setString(4, employeeList.get(i).getFinancialPwd());
                preparedStatement.setString(5, employeeList.get(i).getEmployeeId());
                preparedStatement.setString(6, employeeList.get(i).getRealName());
                preparedStatement.setInt(7, employeeList.get(i).getCertificatesType());
                preparedStatement.setString(8, employeeList.get(i).getIdcardNo());
                preparedStatement.setString(9, employeeList.get(i).getAuthIdcardNo());
                preparedStatement.setString(10, employeeList.get(i).getCertificatesValid());
                preparedStatement.setInt(11, employeeList.get(i).getAuthState());
                preparedStatement.setByte(12, employeeList.get(i).getLevel());
                preparedStatement.setLong(13, employeeList.get(i).getAuthTime());
                preparedStatement.setString(14, employeeList.get(i).getStudioname());
                preparedStatement.setString(15, employeeList.get(i).getRecommender());
                preparedStatement.setString(16, employeeList.get(i).getEmail());
                preparedStatement.setByte(17, employeeList.get(i).getSex());
                preparedStatement.setString(18, employeeList.get(i).getArea());
                preparedStatement.setInt(19, employeeList.get(i).getPositionId());
                preparedStatement.setInt(20, employeeList.get(i).getOrgId());
                preparedStatement.setByte(21, employeeList.get(i).getType());
                preparedStatement.setByte(22, employeeList.get(i).getState());
                preparedStatement.setString(23, employeeList.get(i).getOtherOrgId());
                preparedStatement.setLong(24, employeeList.get(i).getCreateTime());
                preparedStatement.setLong(25, employeeList.get(i).getLastModifiedTime());
                preparedStatement.setLong(26, employeeList.get(i).getLastLoginTime());
                preparedStatement.setByte(27, employeeList.get(i).getYn());
                preparedStatement.setString(28, employeeList.get(i).getNickname());
                preparedStatement.setByte(29, employeeList.get(i).getOnJob());
            }

            public int getBatchSize() {
                return employeeList.size();
            }
        });
        //手动提交
        //con.commit();
        //提交完成将自动提交置为true
        //con.setAutoCommit(true);
        return total.length;
    }

    public Integer insertEmployeeByMybatis() throws Exception {
        //获取第三方人事数据  employeeJdbcTemplate是第三方数据源
        final String querySql = "select * from tb_employee limit 0,5000";
        //使用jdbcTmplate获取数据
        List<Employee> employeeList = employeeJdbcTemplate.query(querySql, new EmployeeRowMapper());
        //使用mybatis插入数据
        int total = employeeMapper.insertByBatch(employeeList);
        return total;
    }
}
