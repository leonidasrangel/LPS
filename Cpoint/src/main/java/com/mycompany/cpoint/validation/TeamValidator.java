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
import com.mycompany.cpoint.model.Team;
import java.util.ArrayList;
import java.util.List;

public class TeamValidator {

    public void validate(Team team) throws ValidationException {
        if (team == null) {
            throw new ValidationException(List.of("No team was provided."));
        }

        List<String> errors = new ArrayList<>();

        if (ValidationUtils.isBlank(team.getName())) {
            errors.add("Enter the team name.");
        }
        if (team.getMembers().isEmpty()) {
            errors.add("Add at least one member to the team.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validateId(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException(List.of("Select a team in the table first."));
        }
    }
}
