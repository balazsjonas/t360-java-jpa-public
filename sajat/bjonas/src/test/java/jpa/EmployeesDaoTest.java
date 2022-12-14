package jpa;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class EmployeesDaoTest {
    @BeforeEach
    void init(){
        MysqlDataSource dataSource = new MysqlDataSource();
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
    void test() {
        MysqlDataSource dataSource = new MysqlDataSource();
        String url = "jdbc:mysql://localhost:3306/employees?useUnicode=true";
        dataSource.setURL(url);
        String user = "root";
        dataSource.setUser(user);
        String password = "mysql";
        dataSource.setPassword(password);

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("insert into employees(emp_name) values('John Doe')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("insert into employees.employees(emp_name) values (?)")) {
            for (int i = 0; i < 10; i++) {
                preparedStatement.setString(1, "prepared " + i);
                preparedStatement.execute();
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
