/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.controller;

/**
 *
 * @author Rangel
 */

import com.mycompany.cpoint.dao.TaskDAO;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.Task;
import com.mycompany.cpoint.validation.TaskValidator;
import java.sql.SQLException;
import java.util.List;

public class TaskController {

    private final TaskDAO taskDAO = new TaskDAO();
    private final TaskValidator taskValidator = new TaskValidator();

    public void saveTask(Task task) throws ValidationException, SQLException {
        taskValidator.validate(task);

        if (task.getId() == 0) {
            taskDAO.insert(task);
        } else {
            taskDAO.update(task);
        }
    }

    public void deleteTask(int id) throws ValidationException, SQLException {
        taskValidator.validateId(id);
        taskDAO.delete(id);
    }

    public List<Task> listTasks() throws SQLException {
        return taskDAO.findAll();
    }
}