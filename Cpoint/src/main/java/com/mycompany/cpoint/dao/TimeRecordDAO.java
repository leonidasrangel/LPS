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
import com.mycompany.cpoint.model.TimeRecord;
import com.mycompany.cpoint.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;

import java.util.List;

public class TimeRecordDAO extends BaseDAO {

    private static final String FIND_BY_USER_JPQL
            = "SELECT r FROM TimeRecord r WHERE r.user.id = :userId "
            + "ORDER BY r.recordedAt DESC, r.id DESC";
    private static final String FIND_BY_USER_IN_PERIOD_JPQL
            = "SELECT r FROM TimeRecord r "
            + "WHERE r.user.id = :userId AND r.recordedAt >= :start AND r.recordedAt < :end "
            + "ORDER BY r.recordedAt ASC, r.id ASC";
    private static final String FIND_IN_PERIOD_JPQL
            = "SELECT r FROM TimeRecord r JOIN FETCH r.user "
            + "WHERE r.recordedAt >= :start AND r.recordedAt < :end "
            + "ORDER BY r.recordedAt DESC, r.id DESC";
    private static final String FIND_IN_PERIOD_OLDEST_FIRST_JPQL
            = "SELECT r FROM TimeRecord r JOIN FETCH r.user "
            + "WHERE r.recordedAt >= :start AND r.recordedAt < :end "
            + "ORDER BY r.recordedAt ASC, r.id ASC";

    public void insert(TimeRecord record) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(record);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not save the clock record.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public TimeRecord findLastByUser(int userId) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<TimeRecord> query = em.createQuery(FIND_BY_USER_JPQL, TimeRecord.class);
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            List<TimeRecord> found = query.getResultList();

            if (found.isEmpty()) {
                return null;
            }
            return found.get(0);
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the last clock record.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<TimeRecord> findByUser(int userId) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<TimeRecord> query = em.createQuery(FIND_BY_USER_JPQL, TimeRecord.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the clock records.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<TimeRecord> findByUserInPeriod(int userId, LocalDateTime start, LocalDateTime end)
            throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<TimeRecord> query = em.createQuery(FIND_BY_USER_IN_PERIOD_JPQL, TimeRecord.class);
            query.setParameter("userId", userId);
            query.setParameter("start", start);
            query.setParameter("end", end);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the clock records of the day.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<TimeRecord> findInPeriod(LocalDateTime start, LocalDateTime end) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<TimeRecord> query = em.createQuery(FIND_IN_PERIOD_JPQL, TimeRecord.class);
            query.setParameter("start", start);
            query.setParameter("end", end);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the clock records of the period.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<TimeRecord> findInPeriodOldestFirst(LocalDateTime start, LocalDateTime end)
            throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<TimeRecord> query = em.createQuery(FIND_IN_PERIOD_OLDEST_FIRST_JPQL, TimeRecord.class);
            query.setParameter("start", start);
            query.setParameter("end", end);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the clock records of the period.", ex);
        } finally {
            closeEntityManager(em);
        }
    }
}