/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.validation;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskValidator {

    public void validate(Task task) throws ValidationException {
        if (task == null) {
            throw new ValidationException(List.of("No task was provided."));
        }

        List<String> errors = new ArrayList<>();

        if (ValidationUtils.isBlank(task.getName())) {
            errors.add("Enter the task name.");
        }
        if (ValidationUtils.isBlank(task.getDescription())) {
            errors.add("Enter the task description.");
        }
        if (ValidationUtils.isBlank(task.getState())) {
            errors.add("Select the task state.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validateId(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException(List.of("Select a task in the table first."));
        }
    }
}
