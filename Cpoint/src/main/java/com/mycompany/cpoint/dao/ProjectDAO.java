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
import com.mycompany.cpoint.model.Project;
import com.mycompany.cpoint.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProjectDAO extends BaseDAO {

    private static final String FIND_ALL_JPQL
            = "SELECT p FROM Project p LEFT JOIN FETCH p.members ORDER BY p.id";

    public void insert(Project project) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(project);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not save the project.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public List<Project> findAll() throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            TypedQuery<Project> query = em.createQuery(FIND_ALL_JPQL, Project.class);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the projects.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void update(Project project) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Project existing = em.find(Project.class, project.getId());
            if (existing == null) {
                throw new DatabaseException("No project found with id " + project.getId() + ".");
            }
            em.merge(project);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not update the project.", ex);
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

            Project project = em.find(Project.class, id);
            if (project == null) {
                throw new DatabaseException("No project found with id " + id + ".");
            }
            em.remove(project);

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not delete the project.", ex);
        } finally {
            closeEntityManager(em);
        }
    }
}
