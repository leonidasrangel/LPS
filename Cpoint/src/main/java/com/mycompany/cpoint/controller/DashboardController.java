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
import com.mycompany.cpoint.dao.TaskDAO;
import com.mycompany.cpoint.dao.TeamDAO;
import com.mycompany.cpoint.dao.UserDAO;
import com.mycompany.cpoint.exception.DatabaseException;

public class DashboardController {

    private final UserDAO userDAO = new UserDAO();
    private final TeamDAO teamDAO = new TeamDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final TaskDAO taskDAO = new TaskDAO();

    public long countUsers() throws DatabaseException {
        return userDAO.count();
    }

    public long countTeams() throws DatabaseException {
        return teamDAO.count();
    }

    public long countProjects() throws DatabaseException {
        return projectDAO.count();
    }

    public long countTasks() throws DatabaseException {
        return taskDAO.count();
    }
}
