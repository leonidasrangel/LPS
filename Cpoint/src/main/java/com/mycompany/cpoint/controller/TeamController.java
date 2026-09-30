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
import com.mycompany.cpoint.model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamController {

    private final TeamDAO teamDAO = new TeamDAO();

    public void registerTeam(String name, List<User> members) throws ValidationException, SQLException {

        List<String> errors = new ArrayList<>();

        if (isBlank(name)) {
            errors.add("Informe o nome do time.");
        }
        if (members.isEmpty()) {
            errors.add("Adicione pelo menos um membro ao time.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Team team = new Team(name);
        for (User member : members) {
            team.addMember(member);
        }

        teamDAO.insert(team);
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
