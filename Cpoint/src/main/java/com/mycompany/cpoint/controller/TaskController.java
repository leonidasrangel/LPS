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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskController {

    private final TaskDAO taskDAO = new TaskDAO();

    public void registerTask(String name, String description, String state)
            throws ValidationException, SQLException {

        List<String> errors = new ArrayList<>();

        if (isBlank(name)) {
            errors.add("Informe o nome da task.");
        }
        if (isBlank(description)) {
            errors.add("Informe a descrição.");
        }
        if (isBlank(state)) {
            errors.add("Selecione o estado da task.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Task task = new Task(name, description, state);
        taskDAO.insert(task);
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
