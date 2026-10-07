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
import com.mycompany.cpoint.model.Project;
import java.util.ArrayList;
import java.util.List;

public class ProjectValidator {

    public void validate(Project project) throws ValidationException {
        if (project == null) {
            throw new ValidationException(List.of("No project was provided."));
        }

        List<String> errors = new ArrayList<>();

        if (ValidationUtils.isBlank(project.getName())) {
            errors.add("Enter the project name.");
        }
        if (ValidationUtils.isBlank(project.getDescription())) {
            errors.add("Enter the project description.");
        }
        if (project.getStartDate() == null) {
            errors.add("Enter the start date.");
        }
        if (project.getStartDate() != null && project.getEndDate() != null
                && project.getEndDate().isBefore(project.getStartDate())) {
            errors.add("The end date cannot be before the start date.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validateId(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException(List.of("Select a project in the table first."));
        }
    }
}
