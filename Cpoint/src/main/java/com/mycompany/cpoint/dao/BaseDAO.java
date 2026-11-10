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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseDAO {

    private static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());

    protected void closeEntityManager(EntityManager em) {
        if (em != null) {
            EntityTransaction transaction = em.getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            em.close();
        }
    }

    protected DatabaseException databaseError(String message, PersistenceException ex) {
        LOGGER.log(Level.SEVERE, message, ex);
        return new DatabaseException(message, ex);
    }
}
