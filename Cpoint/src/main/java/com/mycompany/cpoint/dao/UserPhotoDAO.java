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
import com.mycompany.cpoint.model.UserPhoto;
import com.mycompany.cpoint.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

public class UserPhotoDAO extends BaseDAO {

    public byte[] findImageByUserId(int userId) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            UserPhoto photo = em.find(UserPhoto.class, userId);

            if (photo == null) {
                return null;
            }
            return photo.getImage();
        } catch (PersistenceException ex) {
            throw databaseError("Could not load the profile photo.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void save(int userId, byte[] image) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            UserPhoto photo = em.find(UserPhoto.class, userId);
            if (photo == null) {
                User user = em.find(User.class, userId);
                if (user == null) {
                    throw new DatabaseException("No user found with id " + userId + ".");
                }
                em.persist(new UserPhoto(user, image));
            } else {
                photo.setImage(image);
            }

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not save the profile photo.", ex);
        } finally {
            closeEntityManager(em);
        }
    }

    public void deleteByUserId(int userId) throws DatabaseException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            UserPhoto photo = em.find(UserPhoto.class, userId);
            if (photo != null) {
                em.remove(photo);
            }

            transaction.commit();
        } catch (PersistenceException ex) {
            throw databaseError("Could not remove the profile photo.", ex);
        } finally {
            closeEntityManager(em);
        }
    }
}
