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
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public class TeamDAO extends BaseDAO {

    public void insert(Team team) throws DatabaseException {
        inTransaction("Could not save the team.", em -> em.persist(team));
    }

    public List<Team> findAll() throws DatabaseException {
        return query("Could not load the teams.", em
                -> em.createQuery("SELECT t FROM Team t LEFT JOIN FETCH t.members ORDER BY t.id", Team.class)
                        .getResultList());
    }

    public void update(Team team) throws DatabaseException {
        inTransaction("Could not update the team.", em -> {
            if (em.find(Team.class, team.getId()) == null) {
                throw new EntityNotFoundException("No team found with id " + team.getId() + ".");
            }
            em.merge(team);
        });
    }

    public void delete(int id) throws DatabaseException {
        inTransaction("Could not delete the team.", em -> {
            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new EntityNotFoundException("No team found with id " + id + ".");
            }
            em.remove(team);
        });
    }
}
