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
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends BaseDAO {

    private static final String FIND_ALL_JPQL
            = "SELECT u FROM User u ORDER BY u.id";
    private static final String FIND_BY_EMAIL_JPQL
            = "SELECT u FROM User u WHERE u.email = :value";
    private static final String FIND_BY_USERNAME_JPQL
            = "SELECT u FROM User u WHERE u.username = :value";
    private static final String COUNT_JPQL
            = "SELECT COUNT(u) FROM User u";
    private static final String EMAIL_COUNT_JPQL
            = "SELECT COUNT(u) FROM User u WHERE u.email = :value AND u.id <> :ignoredId";
    private static final String USERNAME_COUNT_JPQL
            = "SELECT COUNT(u) FROM User u WHERE u.username = :value AND u.id <> :ignoredId";
    private static final String TEAM_MEMBER_COUNT_JPQL
            = "SELECT COUNT(t) FROM Team t JOIN t.members m WHERE m.id = :userId";
    private static final String TASK_MEMBER_COUNT_JPQL
            = "SELECT COUNT(t) FROM Task t JOIN t.members m WHERE m.id = :userId";
    private static final String PROJECT_MEMBER_COUNT_JPQL
            = "SELECT COUNT(p) FROM Project p JOIN p.members m WHERE m.id = :userId";
    private static final String TIME_RECORD_COUNT_JPQL
            = "SELECT COUNT(r) FROM TimeRecord r WHERE r.user.id = :userId";

    public void insert(User user) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(user);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not save the user.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<User> findAll() throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<User> query = em.createQuery(FIND_ALL_JPQL, User.class);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the users.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public User findById(int id) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            return em.find(User.class, id);
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the user.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public User findByEmail(String email) throws DatabaseException {
        return findOne(FIND_BY_EMAIL_JPQL, email, "Could not search the user by email.");
    }

    public User findByUsername(String username) throws DatabaseException {
        return findOne(FIND_BY_USERNAME_JPQL, username, "Could not search the user by username.");
    }

    public long count() throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(COUNT_JPQL, Long.class);
            return query.getSingleResult();
        } catch (PersistenceException ex) {
            throw databaseError("Could not count the users.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void update(User user) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            User existing = em.find(User.class, user.getId());
            if (existing == null) {
                throw new DatabaseException("No user found with id " + user.getId() + ".");
            }
            em.merge(user);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not update the user.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void updatePassword(int id, String newPassword) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            User user = em.find(User.class, id);
            if (user == null) {
                throw new DatabaseException("No user found with id " + id + ".");
            }
            user.setPassword(newPassword);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not change the password.", ex);
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

            User user = em.find(User.class, id);
            if (user == null) {
                throw new DatabaseException("No user found with id " + id + ".");
            }
            em.remove(user);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not delete the user.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public boolean existsByEmail(String email, int ignoredId) throws DatabaseException {
        long total = countMatches(EMAIL_COUNT_JPQL, email, ignoredId, "Could not check the email.");
        return total > 0;
    }

    public boolean existsByUsername(String username, int ignoredId) throws DatabaseException {
        long total = countMatches(USERNAME_COUNT_JPQL, username, ignoredId, "Could not check the username.");
        return total > 0;
    }

    public boolean isTeamMember(int userId) throws DatabaseException {
        long total = countByUser(TEAM_MEMBER_COUNT_JPQL, userId, "Could not check the user's teams.");
        return total > 0;
    }

    public boolean isTaskMember(int userId) throws DatabaseException {
        long total = countByUser(TASK_MEMBER_COUNT_JPQL, userId, "Could not check the user's tasks.");
        return total > 0;
    }

    public boolean isProjectMember(int userId) throws DatabaseException {
        long total = countByUser(PROJECT_MEMBER_COUNT_JPQL, userId, "Could not check the user's projects.");
        return total > 0;
    }

    public boolean hasTimeRecords(int userId) throws DatabaseException {
        long total = countByUser(TIME_RECORD_COUNT_JPQL, userId, "Could not check the user's clock records.");
        return total > 0;
    }

    private User findOne(String jpql, String value, String errorMessage) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("value", value);
            List<User> found = query.getResultList();

            if (found.isEmpty()) {
                return null;
            }
            return found.get(0);
        } catch (PersistenceException ex) {
            throw databaseError(errorMessage, ex);
        } finally {
            closeEntityManager(em);
        }
    }

    private long countMatches(String jpql, String value, int ignoredId, String errorMessage)
            throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("value", value);
            query.setParameter("ignoredId", ignoredId);
            return query.getSingleResult();
        } catch (PersistenceException ex) {
            throw databaseError(errorMessage, ex);
        } finally {
            closeEntityManager(em);
        }
    }

    private long countByUser(String jpql, int userId, String errorMessage) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("userId", userId);
            return query.getSingleResult();
        } catch (PersistenceException ex) {
            throw databaseError(errorMessage, ex);
        } finally {
            closeEntityManager(em);
        }
    }
}