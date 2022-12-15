package jpa;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDaoTest {

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
    void test() {


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

    @Test
    void meta() throws SQLException {
        try(Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            List<String> tableNames = getTableNamesByMetadata(metaData);
            System.out.println(tableNames);
//            try(ResultSet rs = metaData.getTables(null, null, null, null)) {
//                while(rs.next()) {
//                    System.out.println(rs.getString(3));
//                }
//            }

        }

    }

    private List<String> getTableNamesByMetadata(DatabaseMetaData meta) throws SQLException {
        try (
                ResultSet rs = meta.getTables(null, null, null, null)
        ) {
            List<String> names = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString(3);
                names.add(name);
            }
            return names;
        }
    }
    @Test
    void cursor() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("insert into employees.employees(emp_name) values (?)")) {
            for (int i = 0; i < 10; i++) {
                preparedStatement.setString(1, "prepared " + i);
                preparedStatement.execute();
            }
        }

        try (Connection conn = dataSource.getConnection();
             Statement query = conn.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE, // görgethető
                     ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = query.executeQuery("select emp_name from employees.employees")) {

            while (resultSet.relative(2)) {
                System.out.println(resultSet.getString("emp_name"));
            }
        }
    }
    @Test
    void cursorUpdate() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("insert into employees.employees(emp_name) values (?)")) {
            for (int i = 0; i < 10; i++) {
                preparedStatement.setString(1, "prepared " + i);
                preparedStatement.execute();
            }
        }

        try (Connection conn = dataSource.getConnection();
             Statement query = conn.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE, // görgethető
                     ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = query.executeQuery("select id, emp_name from employees.employees")) {

            while (resultSet.relative(2)) {
                resultSet.updateString("emp_name", "___"+ resultSet.getString("emp_name"));
                resultSet.updateRow();
            }
        }
    }

    @Test
    void update() {
        EmployeesDao employeesDao = new EmployeesDao(this.dataSource);

    }
}
