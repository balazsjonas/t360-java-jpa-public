package jpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class EmployeesDao {
    private JdbcTemplate jdbcTemplate;
    public EmployeesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public EmployeesDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createEmployee(String name) {
        jdbcTemplate.update("insert into employees.employees(emp_name) values(?)", name);
    }

    public String findById(int id) {
        return jdbcTemplate.queryForObject("select  emp_name from employees where id=?",
                (rs, rowNum) -> rs.getString("emp_name"),
                id);
    }

    public int createEmployee2(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement("insert into employees.employees(emp_name) values(?)", Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, name);
                    return preparedStatement;
                } ,keyHolder
                );
        return keyHolder.getKey().intValue();
    }
    public List<String> listEmployees() {
        return jdbcTemplate.query("select emp_name from employees",
                (rs, rowNum) -> rs.getString("emp_name"));
    }
}
