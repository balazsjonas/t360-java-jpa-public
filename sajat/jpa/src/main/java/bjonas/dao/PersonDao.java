package bjonas.dao;

import bjonas.model.Animal;
import bjonas.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PersonDao {
    private final EntityManagerFactory factory;

    public PersonDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void save(String name) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Person(name));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Person findById(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        Person person = entityManager.find(Person.class, id);
        entityManager.close();
        return person;
    }

    public List<Person> listAll() {
        EntityManager entityManager = factory.createEntityManager();
        List<Person> resultList = entityManager.createQuery("select p from Person p order by p.name", Person.class).getResultList();
        entityManager.close();
        return resultList;
    }

    public void delete(Person animal) { // nem csak copy-paste :-)
        EntityManager entityManager = factory.createEntityManager();
        Person managed = entityManager.getReference(Person.class, animal.getId());
        entityManager.remove(managed);
        entityManager.close();

    }

    public void deleteAll() {
        listAll().forEach(this::delete);

    }
}
