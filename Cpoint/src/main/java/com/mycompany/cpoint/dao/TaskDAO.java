/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.dao;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.model.Task;
import com.mycompany.cpoint.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TaskDAO extends BaseDAO {

    private static final String FIND_ALL_JPQL
            = "SELECT t FROM Task t LEFT JOIN FETCH t.members ORDER BY t.id";
    private static final String COUNT_JPQL
            = "SELECT COUNT(t) FROM Task t";

    private static final String FIND_BY_MEMBER_JPQL
            = "SELECT t FROM Task t LEFT JOIN FETCH t.members "
            + "WHERE t.id IN (SELECT t2.id FROM Task t2 JOIN t2.members m WHERE m.id = :userId) "
            + "ORDER BY t.id";

    public void insert(Task task) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(task);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not save the task.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<Task> findAll() throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Task> query = em.createQuery(FIND_ALL_JPQL, Task.class);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the tasks.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<Task> findByMember(int userId) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Task> query = em.createQuery(FIND_BY_MEMBER_JPQL, Task.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the user's tasks.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public long count() throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(COUNT_JPQL, Long.class);
            return query.getSingleResult();
        } catch (PersistenceException ex) {
            throw databaseError("Could not count the tasks.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void update(Task task) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Task existing = em.find(Task.class, task.getId());
            if (existing == null) {
                throw new DatabaseException("No task found with id " + task.getId() + ".");
            }
            em.merge(task);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not update the task.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void delete(int id) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Task task = em.find(Task.class, id);
            if (task == null) {
                throw new DatabaseException("No task found with id " + id + ".");
            }
            em.remove(task);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not delete the task.", ex);
        } finally {
            closeEntityManager(em);
        }
    }
}
