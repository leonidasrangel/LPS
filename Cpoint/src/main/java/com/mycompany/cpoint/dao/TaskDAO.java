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
import com.mycompany.cpoint.model.Task;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public class TaskDAO extends BaseDAO {

    public void insert(Task task) throws DatabaseException {
        inTransaction("Could not save the task.", em -> em.persist(task));
    }

    public List<Task> findAll() throws DatabaseException {
        return query("Could not load the tasks.", em
                -> em.createQuery("SELECT t FROM Task t ORDER BY t.id", Task.class).getResultList());
    }

    public void update(Task task) throws DatabaseException {
        inTransaction("Could not update the task.", em -> {
            if (em.find(Task.class, task.getId()) == null) {
                throw new EntityNotFoundException("No task found with id " + task.getId() + ".");
            }
            em.merge(task);
        });
    }

    public void delete(int id) throws DatabaseException {
        inTransaction("Could not delete the task.", em -> {
            Task task = em.find(Task.class, id);
            if (task == null) {
                throw new EntityNotFoundException("No task found with id " + id + ".");
            }
            em.remove(task);
        });
    }
}
