package bjonas.dao;

import bjonas.model.Animal;
import bjonas.model.Kutyus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class KutyusDao {
    private EntityManagerFactory factory;

    public KutyusDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void save(String name) {
        save(new Kutyus(name));
    }
    public void save(Kutyus kutyus) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(kutyus);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Kutyus findById(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        Kutyus kutyus = entityManager.find(Kutyus.class, id);
        entityManager.close();
        return kutyus;
    }

    public List<Kutyus> listAll() {
        EntityManager entityManager = factory.createEntityManager();
        List<Kutyus> resultList = entityManager.createQuery("select a from Kutyus a order by a.name", Kutyus.class).getResultList();
        entityManager.close();
        return resultList;
    }

    public void delete(Kutyus animal) {
        EntityManager entityManager = factory.createEntityManager();
        Kutyus managed = entityManager.find(Kutyus.class, animal.getKutyusId());
        entityManager.remove(managed);
        entityManager.close();

    }

    public void deleteAll() {
//        listAll().forEach(this::delete);
        List<Kutyus> animals = listAll();
        for(var a:animals) {
            delete(a);
        }

    }
}
