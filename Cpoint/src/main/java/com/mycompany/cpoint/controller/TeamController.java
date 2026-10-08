/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.controller;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.dao.TeamDAO;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.Team;
import com.mycompany.cpoint.validation.TeamValidator;
import com.mycompany.cpoint.exception.DatabaseException;
import java.util.List;

public class TeamController {

    private final TeamDAO teamDAO = new TeamDAO();
    private final TeamValidator teamValidator = new TeamValidator();

    public void saveTeam(Team team) throws ValidationException, DatabaseException {
        teamValidator.validate(team);

        if (team.getId() == 0) {
            teamDAO.insert(team);
        } else {
            teamDAO.update(team);
        }
    }

    public void deleteTeam(int id) throws ValidationException, DatabaseException {
        teamValidator.validateId(id);
        teamDAO.delete(id);
    }

    public List<Team> listTeams() throws DatabaseException {
        return teamDAO.findAll();
    }
}
