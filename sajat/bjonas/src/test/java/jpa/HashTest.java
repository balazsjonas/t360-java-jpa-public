package jpa;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class HashTest {

    private static MysqlDataSource dataSource;
    private static PersonDao personDao;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void init(){
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
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        personDao = new PersonDao(entityManagerFactory);
    }

    @BeforeEach
    void setup() {
        personDao.deleteAll();
        IntStream.range(101, 111).boxed()
                .forEach(ssn -> personDao.save("person "+ ssn, ssn));
        IntStream.range(101, 111).boxed()
                .forEach(ssn -> personDao.save("person "+ ssn, ssn));
    }

    @AfterAll
    static void cleanup() {
        entityManagerFactory.close();
    }
    @Test
    void test() {
        System.out.println("HELLO");
        List<Person> all = personDao.findAll();
        System.err.println(all.size());
        System.out.println(all);

        System.err.println("------");
        Set<Person> set = new HashSet<>(all);
        System.err.println(set.size());
        System.out.println(set);

        System.err.println("------");
    }

    @Test
    void update() {
        EntityManager em = entityManagerFactory.createEntityManager();
        Person person = em.find(Person.class, 1L);
        System.out.println(".......");
        System.err.println(person);
        System.out.println(".......");
        person.setName(person.getName()+"!!!!!");
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();

        List<Person> all = personDao.findAll();
        System.out.println(all.size());
        all.forEach(System.err::println);
        System.out.println(".......");
    }
}
