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
import com.mycompany.cpoint.validation.ProjectValidator;
import java.sql.SQLException;
import java.util.List;

public class ProjectController {

    private final ProjectDAO projectDAO = new ProjectDAO();
    private final ProjectValidator projectValidator = new ProjectValidator();

    public void saveProject(Project project) throws ValidationException, SQLException {
        projectValidator.validate(project);

        if (project.getId() == 0) {
            projectDAO.insert(project);
        } else {
            projectDAO.update(project);
        }
    }

    public void deleteProject(int id) throws ValidationException, SQLException {
        projectValidator.validateId(id);
        projectDAO.delete(id);
    }

    public List<Project> listProjects() throws SQLException {
        return projectDAO.findAll();
    }
}
