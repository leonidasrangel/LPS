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
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public class ProjectDAO extends BaseDAO {

    public void insert(Project project) throws DatabaseException {
        inTransaction("Could not save the project.", em -> em.persist(project));
    }

    public List<Project> findAll() throws DatabaseException {
        return query("Could not load the projects.", em ->
            em.createQuery("SELECT p FROM Project p ORDER BY p.id", Project.class).getResultList());
    }

    public void update(Project project) throws DatabaseException {
        inTransaction("Could not update the project.", em -> {
            if (em.find(Project.class, project.getId()) == null) {
                throw new EntityNotFoundException("No project found with id " + project.getId() + ".");
            }
            em.merge(project);
        });
    }

    public void delete(int id) throws DatabaseException {
        inTransaction("Could not delete the project.", em -> {
            Project project = em.find(Project.class, id);
            if (project == null) {
                throw new EntityNotFoundException("No project found with id " + id + ".");
            }
            em.remove(project);
        });
    }
}
