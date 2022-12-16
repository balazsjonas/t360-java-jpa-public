package bjonas.model;

import bjonas.dao.AnimalDao;
import bjonas.dao.ChessDao;
import bjonas.dao.KutyusDao;
import bjonas.dao.PersonDao;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private static EntityManagerFactory factory;

    AnimalDao animalDao;
    PersonDao personDao;

    ChessDao chessDao;

    KutyusDao kutyusDao;

    @BeforeAll
    static void init() {
        factory = Persistence.createEntityManagerFactory("pu");
    }

    @BeforeEach
    void setup() {
        animalDao = new AnimalDao(factory);
        animalDao.deleteAll();
        List.of("Cili", "Cirmi", "Cicus Maximus").forEach(n -> animalDao.save(n));

        personDao = new PersonDao(factory);
        personDao.deleteAll();
        List.of("Anna", "Bela", "Csilla").forEach(n -> personDao.save(n));

        chessDao = new ChessDao(factory);

        chessDao.deleteAll();
        chessDao.save("A", 1, "Fehér Bástya");
        chessDao.save("B", 1, "Fehér Paci");
        chessDao.save("C", 1, "Fehér Futó");
        System.err.println("(bjonas)"+ chessDao.findById("B", 1));
    }

    @AfterAll
    static void stop() {
        if(factory != null) {
            factory.close();
        }
    }

    @Test
    void test() {
        Animal cili = animalDao.findById(1L);
        System.err.println(cili);

        System.err.println(animalDao.listAll());
    }

    @Test
    void person() {
        System.err.println(personDao.listAll());
    }

    @Test
    void people() {
        IntStream.range(1, 30).boxed()
                .map(i -> "name#"+i)
                .forEach(personDao::save);
        System.err.println(personDao.listAll());
    }

    @Test
    void chess() {
        System.err.println(chessDao.listAll());
    }

    @Test
    void lifeCycle() {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        ChessTable queen = new ChessTable("D", 1, "Királynő");

        ChessTable king = new ChessTable("E", 1, "Király");
        em.persist(queen);
        em.persist(king);
        em.getTransaction().commit();
        king.setPiece("Fehér Király");
        queen.setPiece("Fehér Királynő");
        em.getTransaction().begin();
        em.persist(king);
//        em.detach(queen);
        em.persist(queen);
        em.getTransaction().commit();

        em.close();
        chessDao.listAll().forEach(System.err::println);
    }

    @Test
    void inherit() {
        Cicus cirmos = new Cicus("Cirmos", "cirmos");
        animalDao.save(cirmos);
    }

    @Test
    void lazy() {
        System.err.println("LAZY");

    }

    @Test
    void empty() {
        System.err.println("Cak fusson a setup");
    }

}