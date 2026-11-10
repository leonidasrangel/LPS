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
import com.mycompany.cpoint.model.Team;
import com.mycompany.cpoint.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TeamDAO extends BaseDAO {

    private static final String FIND_ALL_JPQL
            = "SELECT t FROM Team t LEFT JOIN FETCH t.members ORDER BY t.id";

    public void insert(Team team) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(team);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not save the team.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<Team> findAll() throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Team> query = em.createQuery(FIND_ALL_JPQL, Team.class);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the teams.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void update(Team team) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Team existing = em.find(Team.class, team.getId());
            if (existing == null) {
                throw new DatabaseException("No team found with id " + team.getId() + ".");
            }
            em.merge(team);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not update the team.", ex);
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

            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new DatabaseException("No team found with id " + id + ".");
            }
            em.remove(team);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not delete the team.", ex);
        } finally {
            closeEntityManager(em);
        }
    }
}
