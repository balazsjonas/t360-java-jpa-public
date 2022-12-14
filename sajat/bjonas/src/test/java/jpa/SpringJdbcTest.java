package jpa;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SpringJdbcTest {

    private MysqlDataSource dataSource;

    @BeforeEach
    void init(){
        dataSource = new MysqlDataSource();
        String url = "jdbc:mysql://localhost:3306/employees?useUnicode=true";
        dataSource.setURL(url);
        String user = "root";
        dataSource.setUser(user);
        String password = "mysql";
        dataSource.setPassword(password);

        Flyway flyway = Flyway.configure()
                .locations("db/migrations/mysql")
                .dataSource(dataSource)
                .load();
        flyway.clean();
        flyway.migrate();

    }

    @Test
    void template() {
        EmployeesDao employeesDao = new EmployeesDao(dataSource);
        List.of("John Doe", "Jane Doe", "James Doe").forEach(n -> employeesDao.createEmployee(n));

        System.out.println(employeesDao.listEmployees());
    }

    @Test
    void template2() {
        EmployeesDao employeesDao = new EmployeesDao(dataSource);
        List.of("John Doe", "Jane Doe", "James Doe").stream().map(n -> employeesDao.createEmployee2(n))
                .forEach(System.out::println);

        System.out.println(employeesDao.listEmployees());

        System.out.println(employeesDao.findById(3));
//        System.out.println(employeesDao.findById(4));
    }

}
