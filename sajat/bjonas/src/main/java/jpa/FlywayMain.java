package jpa;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;

public class FlywayMain {
    public static void main(String[] args) {
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
        flyway.migrate();
    }
}
