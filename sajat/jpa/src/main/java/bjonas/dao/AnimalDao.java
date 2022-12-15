package bjonas.dao;

import bjonas.model.Animal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AnimalDao {
    private EntityManagerFactory factory;

    public AnimalDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void save(String name) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Animal(name));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Animal findById(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        Animal animal = entityManager.find(Animal.class, id);
        entityManager.close();
        return animal;
    }

    public List<Animal> listAll() {
        EntityManager entityManager = factory.createEntityManager();
        List<Animal> resultList = entityManager.createQuery("select a from Animal a order by a.name", Animal.class).getResultList();
        entityManager.close();
        return resultList;
    }

    public void delete(Animal animal) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.remove(animal);
        entityManager.close();

    }

    public void deleteAll() {
        listAll().forEach(this::delete);

    }
}
