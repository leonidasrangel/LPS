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
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public class UserDAO extends BaseDAO {

    private static final String EMAIL_COUNT_JPQL =
        "SELECT COUNT(u) FROM User u WHERE u.email = :value AND u.id <> :ignoredId";
    private static final String USERNAME_COUNT_JPQL =
        "SELECT COUNT(u) FROM User u WHERE u.username = :value AND u.id <> :ignoredId";
    private static final String TEAM_MEMBER_COUNT_JPQL =
        "SELECT COUNT(t) FROM Team t JOIN t.members m WHERE m.id = :userId";

    public void insert(User user) throws DatabaseException {
        inTransaction("Could not save the user.", em -> em.persist(user));
    }

    public List<User> findAll() throws DatabaseException {
        return query("Could not load the users.", em ->
            em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class).getResultList());
    }

    public void update(User user) throws DatabaseException {
        inTransaction("Could not update the user.", em -> {
            if (em.find(User.class, user.getId()) == null) {
                throw new EntityNotFoundException("No user found with id " + user.getId() + ".");
            }
            em.merge(user);
        });
    }

    public void delete(int id) throws DatabaseException {
        inTransaction("Could not delete the user.", em -> {
            User user = em.find(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("No user found with id " + id + ".");
            }
            em.remove(user);
        });
    }

    public boolean existsByEmail(String email, int ignoredId) throws DatabaseException {
        return countMatches(EMAIL_COUNT_JPQL, email, ignoredId, "Could not check the email.") > 0;
    }

    public boolean existsByUsername(String username, int ignoredId) throws DatabaseException {
        return countMatches(USERNAME_COUNT_JPQL, username, ignoredId, "Could not check the username.") > 0;
    }

    public boolean isTeamMember(int userId) throws DatabaseException {
        return query("Could not check the user's teams.", em ->
            em.createQuery(TEAM_MEMBER_COUNT_JPQL, Long.class)
                .setParameter("userId", userId)
                .getSingleResult()) > 0;
    }

    private long countMatches(String jpql, String value, int ignoredId, String errorMessage)
            throws DatabaseException {
        return query(errorMessage, em ->
            em.createQuery(jpql, Long.class)
                .setParameter("value", value)
                .setParameter("ignoredId", ignoredId)
                .getSingleResult());
    }
}
