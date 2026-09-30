/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.controller;

/**
 *
 * @author Rangel
 */

import com.mycompany.cpoint.dao.ProjectDAO;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.Project;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ProjectDAO projectDAO = new ProjectDAO();

    public void registerProject(String name, String description, String startDateText,
                                 String endDateText) throws ValidationException, SQLException {

        List<String> errors = new ArrayList<>();
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (isBlank(name)) {
            errors.add("Informe o nome do projeto.");
        }
        if (isBlank(description)) {
            errors.add("Informe a descrição.");
        }

        if (isBlank(startDateText)) {
            errors.add("Informe a data de início.");
        } else {
            try {
                startDate = LocalDate.parse(startDateText, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                errors.add("Data de início inválida. Use o formato dd/MM/yyyy.");
            }
        }

        if (!isBlank(endDateText)) {
            try {
                endDate = LocalDate.parse(endDateText, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                errors.add("Data de término inválida. Use o formato dd/MM/yyyy.");
            }
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            errors.add("A data de término não pode ser anterior à data de início.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Project project = new Project(name, description, startDate, endDate);
        projectDAO.insert(project);
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
