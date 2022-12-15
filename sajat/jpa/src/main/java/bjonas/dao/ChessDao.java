package bjonas.dao;

import bjonas.model.Animal;
import bjonas.model.ChessTable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ChessDao {
    private EntityManagerFactory factory;

    public ChessDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void save(String col, int row, String piece) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new ChessTable(col, row, piece));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public ChessTable findById(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        ChessTable table = entityManager.find(ChessTable.class, id);
        entityManager.close();
        return table;
    }

    public List<ChessTable> listAll() {
        EntityManager entityManager = factory.createEntityManager();
        List<ChessTable> resultList = entityManager.createQuery("select a from ChessTable a", ChessTable.class).getResultList();
        entityManager.close();
        return resultList;
    }

    public void delete(ChessTable table) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.remove(table);
        entityManager.close();

    }

    public void deleteAll() {
        listAll().forEach(this::delete);

    }
}
