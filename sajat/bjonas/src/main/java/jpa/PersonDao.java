package jpa;

import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class PersonDao {


    private EntityManagerFactory entityManagerFactory;

    public PersonDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public Person save(Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        entityManager.close();
        return person;
    }

    public Person save(String name, int ssn) {
        Person person = new Person(name, ssn);
        return save(person);
    }

    public Optional<Person> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Person person = entityManager.find(Person.class, id);
        entityManager.close();
        return Optional.ofNullable(person);
    }

    public List<Person> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Person> query = entityManager.createQuery("select p from Person p", Person.class);
        List<Person> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public void delete(Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public void deleteAll() {
        this.findAll().forEach(this::delete);
    }
}
