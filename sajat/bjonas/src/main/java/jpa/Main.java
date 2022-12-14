package jpa;

//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
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
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("insert into employees.employees(emp_name) values (?)")) {
            for (int i = 0; i < 10; i++) {
                preparedStatement.setString(1, "prepared " + i);
                preparedStatement.execute();
            }
        }
        System.out.println("Hello world!");

        try (Connection conn = dataSource.getConnection();
             Statement query1 = conn.createStatement();
             ResultSet resultSet = query1.executeQuery("select emp_name from employees.employees")) {

            while (resultSet.next()) {
                System.out.println(resultSet.getString("emp_name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }



    }
}