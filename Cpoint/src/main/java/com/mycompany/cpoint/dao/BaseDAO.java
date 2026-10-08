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
import com.mycompany.cpoint.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseDAO {

    private static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());

    protected void inTransaction(String errorMessage, Consumer<EntityManager> work)
            throws DatabaseException {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                work.accept(em);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw ex;
            }
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, errorMessage, ex);
            throw new DatabaseException(errorMessage, ex);
        }
    }

    protected <T> T query(String errorMessage, Function<EntityManager, T> work)
            throws DatabaseException {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return work.apply(em);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, errorMessage, ex);
            throw new DatabaseException(errorMessage, ex);
        }
    }
}
