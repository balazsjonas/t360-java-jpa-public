package bjonas.dao;

import bjonas.model.Animal;
import bjonas.model.ChessTable;
import bjonas.model.Key;

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

    public ChessTable findById(String colChar, int rowNum) {
        EntityManager entityManager = factory.createEntityManager();
        ChessTable table = entityManager.find(ChessTable.class, new Key(colChar, rowNum));
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
        ChessTable managed = entityManager.getReference(ChessTable.class, new Key(table.getColChar(), table.getRowNum()));
        System.err.println("(bjonas) deleting: "+ managed);
        entityManager.remove(managed);
        entityManager.close();

    }

    public void deleteAll() {
        listAll().forEach(this::delete);

    }
}
